package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class FoodBMIResponse(

	@field:SerializedName("FoodBMIResponse")
	val foodBMIResponse: List<FoodBMIResponseItem?>? = null
)

data class FoodBMIResponseItem(

	@field:SerializedName("calory")
	val calory: Int? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

)
