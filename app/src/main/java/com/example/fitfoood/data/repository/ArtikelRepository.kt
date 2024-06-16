package com.example.fitfoood.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.source.ApiConfig
import com.example.fitfoood.source.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtikelRepository {
    fun getAllArticle(token: String): LiveData<ApiResponse<List<ArtikelResponseItem>>> {
        val result = MediatorLiveData<ApiResponse<List<ArtikelResponseItem>>>()
        result.value = ApiResponse.Loading
        val apiService =ApiConfig.getApiService()
        val client = apiService.getAllArticle("Bearer $token")
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


}
