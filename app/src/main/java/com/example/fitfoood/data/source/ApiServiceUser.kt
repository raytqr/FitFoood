package com.example.fitfood.data.source

import com.example.fitfoood.data.RegisterRequest
import com.example.fitfoood.data.response.LoginResponse
import com.example.fitfoood.data.response.SignUpResponse
import com.example.fitfoood.data.response.UserResponse
import com.example.fitfoood.data.response.UserResponseItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceUser {

    @FormUrlEncoded
    @POST("api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("api/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<SignUpResponse>

    @FormUrlEncoded
    @POST("api/register")
    fun signUp(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("date_of_birth") dateOfBirth: String
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @PUT("api/users/{id}")
    fun updateUser(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<UserResponse>

    @DELETE("api/users/{id}")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Call<UserResponse>
}
