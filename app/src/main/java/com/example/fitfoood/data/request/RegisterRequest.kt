package com.example.fitfoood.data.request


data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val dateOfBirth: String
)

