package org.sopt.myapplication.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.Throws

object ServiceCreator {
    private const val BASE_URL = "https://asia-northeast3-we-sopt-29.cloudfunctions.net/api/"
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor(),))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideOkHttpClient(
        interceptor: AppInterceptor,
    ): OkHttpClient{
        val logger = HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()
    }



    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .build()

            return chain.proceed(newRequest)
        }
    }

    val signUpService : SignUpService = retrofit.create(SignUpService::class.java)
    val signInService: SignInService = retrofit.create(SignInService::class.java)
}