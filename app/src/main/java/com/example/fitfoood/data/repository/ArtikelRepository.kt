package com.example.fitfoood.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.data.response.BMI
import com.example.fitfoood.data.response.BMIRecomendationResponse
import com.example.fitfoood.data.response.GetBMIResponse
import com.example.fitfoood.data.response.PostBMIResponse
import com.example.fitfoood.source.ApiConfig
import com.example.fitfoood.source.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtikelRepository {
    fun getAllArticle(token: String): LiveData<ApiResponse<List<ArtikelResponseItem>>> {
        val result = MediatorLiveData<ApiResponse<List<ArtikelResponseItem>>>()
        result.value = ApiResponse.Loading
        val ArticlesApiService =ApiConfig.getApiService()
        val client = ArticlesApiService.getAllArticle("Bearer $token")
        client.enqueue(object : Callback<List<ArtikelResponseItem>> {
            override fun onResponse(
                call: Call<List<ArtikelResponseItem>>,
                response: Response<List<ArtikelResponseItem>>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.Success(response.body()!!)
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<List<ArtikelResponseItem>>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
            }
        })
        return result
    }

    fun getBMI(token: String, idhealth: String): LiveData<ApiResponse<GetBMIResponse>> {
        val result = MediatorLiveData<ApiResponse<GetBMIResponse>>()
        result.value = ApiResponse.Loading
        val apiService = ApiConfig.getApiService()
        val client = apiService.getBMI("Bearer $token", idhealth)
        client.enqueue(object : Callback<GetBMIResponse> {
            override fun onResponse(
                call: Call<GetBMIResponse>,
                response: Response<GetBMIResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.Success(response.body()!!)
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<GetBMIResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
            }
        })

        return result
    }

    fun postBMI(token: String, idhealth: String, bmi: BMI): LiveData<ApiResponse<PostBMIResponse>> {
        val result = MediatorLiveData<ApiResponse<PostBMIResponse>>()
        result.value = ApiResponse.Loading
        val apiService = ApiConfig.getApiService()
        val client = apiService.postBMI("Bearer $token", idhealth, bmi)
        client.enqueue(object : Callback<PostBMIResponse> {
            override fun onResponse(
                call: Call<PostBMIResponse>,
                response: Response<PostBMIResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.Success(response.body()!!)
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<PostBMIResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
            }
        })

        return result
    }

    fun getBMIRecomendation(user_id: String): LiveData<ApiResponse<BMIRecomendationResponse>> {
        val result = MediatorLiveData<ApiResponse<BMIRecomendationResponse>>()
        result.value = ApiResponse.Loading
        val apiService = ApiConfig.getApiService()
        val client = apiService.getBMIRecomendation(user_id)
        client.enqueue(object : Callback<BMIRecomendationResponse> {
            override fun onResponse(
                call: Call<BMIRecomendationResponse>,
                response: Response<BMIRecomendationResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = ApiResponse.Success(response.body()!!)
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<BMIRecomendationResponse>, t: Throwable) {
                result.value = ApiResponse.Error(t.message.toString())
            }
        })

        return result
    }
}
