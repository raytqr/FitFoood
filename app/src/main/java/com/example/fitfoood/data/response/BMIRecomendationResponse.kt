package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class BMIRecomendationResponse(

	@field:SerializedName("result")
	val result: Result? = null
)

data class Result(

	@field:SerializedName("food1")
	val food1: String? = null,

	@field:SerializedName("food3_img")
	val food3Img: String? = null,

	@field:SerializedName("food3")
	val food3: String? = null,

	@field:SerializedName("food2")
	val food2: String? = null,

	@field:SerializedName("food5")
	val food5: String? = null,

	@field:SerializedName("food4")
	val food4: String? = null,

	@field:SerializedName("food6")
	val food6: String? = null,

	@field:SerializedName("food4_cal")
	val food4Cal: String? = null,

	@field:SerializedName("food5_cal")
	val food5Cal: String? = null,

	@field:SerializedName("exer6")
	val exer6: String? = null,

	@field:SerializedName("exer5")
	val exer5: String? = null,

	@field:SerializedName("exer4")
	val exer4: String? = null,

	@field:SerializedName("exer3")
	val exer3: String? = null,

	@field:SerializedName("food2_img")
	val food2Img: String? = null,

	@field:SerializedName("exer2")
	val exer2: String? = null,

	@field:SerializedName("exer1")
	val exer1: String? = null,

	@field:SerializedName("food6_img")
	val food6Img: String? = null,

	@field:SerializedName("food_desc")
	val foodDesc: String? = null,

	@field:SerializedName("food1_cal")
	val food1Cal: String? = null,

	@field:SerializedName("img_exer3")
	val imgExer3: String? = null,

	@field:SerializedName("img_exer2")
	val imgExer2: String? = null,

	@field:SerializedName("img_exer1")
	val imgExer1: String? = null,

	@field:SerializedName("food6_cal")
	val food6Cal: String? = null,

	@field:SerializedName("food1_img")
	val food1Img: String? = null,

	@field:SerializedName("img_exer6")
	val imgExer6: String? = null,

	@field:SerializedName("food5_img")
	val food5Img: String? = null,

	@field:SerializedName("img_exer5")
	val imgExer5: String? = null,

	@field:SerializedName("food2_cal")
	val food2Cal: String? = null,

	@field:SerializedName("img_exer4")
	val imgExer4: String? = null,

	@field:SerializedName("exercise_desc")
	val exerciseDesc: String? = null,

	@field:SerializedName("food4_img")
	val food4Img: String? = null,

	@field:SerializedName("label_id")
	val labelId: String? = null,

	@field:SerializedName("food3_cal")
	val food3Cal: String? = null
)
