package com.example.fitfoood.data.pref

import com.example.fitfoood.data.response.DateOfBirth

data class UserModel(
    val username: String,
    val email: String,
    val token: String,
    val dateOfBirth: String,
    val userId: String,
    val isLogin: Boolean = false
)