package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class GetBMIResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UpdatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class DataItem(

	@field:SerializedName("updated_at")
	val updatedAt: UpdatedAt? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("bmiUser")
	val bmiUser: Any? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
