package com.example.fitfoood.data.retrofit

import com.example.fitfoood.data.request.LoginRequest
import com.example.fitfoood.data.request.RegisterRequest
import com.example.fitfoood.data.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/register")
    fun register(@Body registerRequest: RegisterRequest): Call<Void>

    @POST("api/logout")
    fun logout(): Call<Void>
}
