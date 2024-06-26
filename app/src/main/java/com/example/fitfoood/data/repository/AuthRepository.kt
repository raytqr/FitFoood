package com.example.fitfoood.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.fitfood.data.source.ApiServiceUser
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.RegisterRequest
import com.example.fitfoood.data.response.LoginResponse
import com.example.fitfoood.data.response.SignUpResponse
import com.example.fitfoood.data.response.UpdatUserResponse
import com.example.fitfoood.data.response.UserUpdate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error

class AuthRepository(
    private val apiService: ApiServiceUser
) {
    fun userLogin(email: String, password: String): LiveData<ApiResponse<LoginResponse>> {
        val result = MutableLiveData<ApiResponse<LoginResponse>>()
        result.value = ApiResponse.Loading

        val client = apiService.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.Success(response.body()!!)
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
            }
        })
        return result
    }

    fun userRegister(username: String, email: String, password: String, dateOfBirth: String,gender:String) = liveData {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.register(RegisterRequest(username, email, password, dateOfBirth,gender))
            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body()))
            } else {
                emit(Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Error(e.message))
        }
    }

    fun updateUser( idUser:String,userUpdate:UserUpdate): LiveData<ApiResponse<UpdatUserResponse>> {
        val result = MutableLiveData<ApiResponse<UpdatUserResponse>>()
        result.value = ApiResponse.Loading

        val client = apiService.updateUser( idUser, userUpdate)
        client.enqueue(object : Callback<UpdatUserResponse> {
            override fun onResponse(
                call: Call<UpdatUserResponse>,
                response: Response<UpdatUserResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.Success(response.body()!!)
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<UpdatUserResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
            }
        })
        return result
    }
}
