package com.example.fitfoood.source

import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("/api/getArticle")
    fun getAllArticle(
        @Header("Authorization") token: String
    ): Call<List<ArtikelResponseItem>>



}
