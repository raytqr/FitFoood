package com.example.fitfoood.data

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val dateOfBirth: String
)