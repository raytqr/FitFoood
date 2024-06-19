package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class WoBMIResponse(

	@field:SerializedName("WoBMIResponse")
	val woBMIResponse: List<WoBMIResponseItem?>? = null
)

data class WoBMIResponseItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
