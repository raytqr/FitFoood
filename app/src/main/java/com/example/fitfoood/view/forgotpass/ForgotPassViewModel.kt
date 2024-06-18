package com.example.fitfoood.view.forgotpass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.AuthRepository
import com.example.fitfoood.data.repository.UserRepository
import kotlinx.coroutines.launch

class ForgotPassViewModel ( private val authRepository: AuthRepository) : ViewModel() {

    fun forgotUser(token:String,email: String, password: String) =
        authRepository.updateUser(token, email, password)
}