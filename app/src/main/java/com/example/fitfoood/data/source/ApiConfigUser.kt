package com.example.fitfoood.data.source

import com.example.fitfood.data.source.ApiServiceUser
import com.example.fitfoood.source.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigUser {
    private const val BASE_URL = "https://cc-fitfood-xrre4szdka-et.a.run.app"
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun getApiServiceUser(): ApiServiceUser {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceUser::class.java)
    }
}