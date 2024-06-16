package com.example.fitfoood.data

sealed class ApiResponse<out T>(val message: String?=null, val data: T?=null){
    class Success<T>(data: T): ApiResponse<T>(data = data)
    class Error<T>(message: String): ApiResponse<T>(message = message)
    object Loading: ApiResponse<Nothing>()
}
