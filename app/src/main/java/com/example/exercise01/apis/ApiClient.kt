package com.example.exercise01.apis

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL: String = "https://android101-7f15b-default-rtdb.asia-southeast1.firebasedatabase.app/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }
    private val httpClient : OkHttpClient by lazy {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()
    }
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    val apiService : ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}