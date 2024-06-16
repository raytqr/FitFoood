package com.example.fitfoood.data.retrofit

import com.example.fitfoood.data.response.ArticleResponseItem
import com.example.fitfoood.data.response.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("getArticle")
    fun getArticles(): Call<ArticleResponseItem>
}
interface FoodApiService {
    @GET("/nutrition/")
    fun searchFood(@Query("search") query: String): Call<List<FoodResponse>>
}
