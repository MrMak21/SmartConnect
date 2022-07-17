package gr.makris.smartconnect.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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

    @JvmStatic
    fun getRetrofitInstance(): ApiInterface {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val token = "testeyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5vc21hazM3QGdtYWlsLmNvbSIsImlzcyI6InNtYXJ0Q29ubmVjdFNlcnZlciIsImV4cCI6MTY1ODA3NzEwNH0.4H9W3Y7ZGDykX54XrCFVJWIviphoXvpwNDSwa2tg2IE"

        val tokenInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "$token")
                .addHeader("Content-Type:",  "application/json;charset=UTF-8")
                .build()

            chain.proceed(request)
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