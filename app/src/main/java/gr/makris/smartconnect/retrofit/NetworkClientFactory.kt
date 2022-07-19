package gr.makris.smartconnect.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import gr.makris.smartconnect.application.SmartConnectApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkClientFactory {

    private lateinit var retrofit: Retrofit
    private const val BASE_URL = "http://10.0.2.2:7777/"
    private val sharedPrefsProvider = SmartConnectApplication.get().sharedPreferencesProvider
    private const val ACCESS_TOKEN = "AccessToken"

    @JvmStatic
    fun getRetrofitInstance(): ApiInterface {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val token = sharedPrefsProvider.getString(ACCESS_TOKEN, "")
//        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5vc21hazM3QGdtYWlsLmNvbSIsImlzcyI6InNtYXJ0Q29ubmVjdFNlcnZlciIsImV4cCI6MTY1ODA5NDk3MX0.2zudii_mVk6KiY-HCgi7WqjRi9pxqXXWAYrNj26YZYw"

        val tokenInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
            if (!token.isNullOrBlank()) {
                request.addHeader("Authorization", "Bearer $token")
            }

            request.addHeader("Content-Type:", "application/json;charset=UTF-8")


            chain.proceed(request.build())
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}