package com.example.fitfoood.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository, private val authRepository: AuthRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
    fun login(email: String, password: String) =
        authRepository.userLogin(email, password)
}