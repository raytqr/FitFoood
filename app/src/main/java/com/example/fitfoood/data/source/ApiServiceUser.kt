package com.example.fitfood.data.source

import com.example.fitfoood.data.response.UserResponseItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceUser {

    @GET("api/users")
    fun getAllUsers(
        @Header("Authorization") token: String
    ): Call<List<UserResponseItem>>

    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponseItem>

    @FormUrlEncoded
    @POST("api/register")
    fun signUp(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("date_of_birth") dateOfBirth: String
    ): Call<UserResponseItem>

    @FormUrlEncoded
    @PUT("api/users/{id}")
    fun updateUser(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<UserResponseItem>

    @DELETE("api/users/{id}")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Call<ResponseBody>
}
