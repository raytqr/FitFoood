package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("userId")
	val userId: String? = null
)
