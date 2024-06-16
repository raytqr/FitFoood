package com.example.fitfoood.data.retrofit

import com.example.fitfoood.data.response.ArticleResponseItem

import retrofit2.Call
import retrofit2.http.GET

interface ArticlesApiService {
    @GET("getArticle")
    fun getArticles(): Call<ArticleResponseItem>
}