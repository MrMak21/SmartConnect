package gr.makris.smartconnect.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import gr.makris.smartconnect.application.SmartConnectApplication
import gr.makris.smartconnect.model.Definitions
import gr.makris.smartconnect.model.common.DataResultThrowable
import gr.makris.smartconnect.response.authToken.RefreshTokenResponse
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


object NetworkClientFactory {

    private val sharedPrefsProvider = SmartConnectApplication.get().sharedPreferencesProvider

    @JvmStatic
    fun getSmartConnectAuthenticatedApi(): SmartConnectApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val tokenInterceptor = Interceptor { chain ->
            val token = sharedPrefsProvider.getString(Definitions.ACCESS_TOKEN_PREFERENCES, "")
            val request = chain.request().newBuilder()
            if (!token.isNullOrBlank()) {
                request.addHeader("Authorization", "Bearer $token")
            }
            request.addHeader("Content-Type", "application/json")
            request.addHeader("Accept", " application/json")
            chain.proceed(request.build())
        }

        val authenticator = object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                return runBlocking {
                    val networkProvider = SmartConnectApplication.get().networkProvider
                    val refreshToken = sharedPrefsProvider.getString(Definitions.REFRESH_TOKEN_PREFERENCES, "") ?: ""
                    val refreshTokenResponse: DataResultThrowable<RefreshTokenResponse, Throwable> = try {
                        val accessTokenResponse = networkProvider.refreshAccessToken(refreshToken)
                        DataResultThrowable(accessTokenResponse)
                    } catch (t: Throwable) {
                        Timber.d(t)
                        DataResultThrowable(error = t)
                    }

                    refreshTokenResponse.data?.let {
                        sharedPrefsProvider.putString(Definitions.ACCESS_TOKEN_PREFERENCES, it.accessToken)
                        sharedPrefsProvider.putString(Definitions.REFRESH_TOKEN_PREFERENCES, it.refreshToken)
                        val token = sharedPrefsProvider.getString(Definitions.ACCESS_TOKEN_PREFERENCES, "") ?: ""

                        response.request.newBuilder().header("Authorization", "Bearer $token").build()
                    } ?: refreshTokenResponse.error.let {
                        Timber.d(it?.message)

                        null
                    }
                }
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(Definitions.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(SmartConnectApi::class.java)
    }

    @JvmStatic
    fun getSmartConnectApi(): SmartConnectApi {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val headersInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader("Content-Type", "application/json")
            request.addHeader("Accept", " application/json")
            chain.proceed(request.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return  Retrofit.Builder()
            .baseUrl(Definitions.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(SmartConnectApi::class.java)
    }
}