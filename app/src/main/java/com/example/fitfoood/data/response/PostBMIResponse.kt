package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class PostBMIResponse(

	@field:SerializedName("data")
	val data: BMI? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class BMI(
	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("weight")
	val weight: Int
)