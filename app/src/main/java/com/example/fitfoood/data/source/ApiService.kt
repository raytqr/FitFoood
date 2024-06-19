package com.example.fitfoood.source

import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.data.response.BMI
import com.example.fitfoood.data.response.BMIRecomendationResponse
import com.example.fitfoood.data.response.FoodBMIResponseItem
import com.example.fitfoood.data.response.GetBMIResponse
import com.example.fitfoood.data.response.PostBMIResponse
import com.example.fitfoood.data.response.WoBMIResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/api/getArticle")
    fun getAllArticle(
        @Header("Authorization") token: String
    ): Call<List<ArtikelResponseItem>>

    @POST("/api/users/{idhealth}/health")
    fun postBMI(
        @Header("Authorization") token: String,
        @Path("idhealth") idhealth: String,
        @Body bmi: BMI
    ): Call<PostBMIResponse>

    @GET("/api/users/{idhealth}/health")
    fun getBMI(
        @Header("Authorization") token: String,
        @Path("idhealth") idhealth: String
    ): Call<GetBMIResponse>

    @GET("/api/getLabelbyLabel/{user_id}")
    fun getBMIRecomendation(
        @Path("user_id") user_id: String
    ):Call<BMIRecomendationResponse>

    @GET("api/getExercise")
    fun getExerciseRec(
        @Header("Authorization") token: String
    ): Call<List<WoBMIResponseItem>>

    @GET("api/getFood")
    fun getFoodRec(
        @Header("Authorization") token: String
    ): Call<List<FoodBMIResponseItem>>
}
