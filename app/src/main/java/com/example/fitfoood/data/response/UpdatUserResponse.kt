package com.example.fitfoood.data.response

import com.google.gson.annotations.SerializedName

data class UpdatUserResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserUpdate? = null
)

data class UserUpdate(

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("password")
	val password: String? = null
)
