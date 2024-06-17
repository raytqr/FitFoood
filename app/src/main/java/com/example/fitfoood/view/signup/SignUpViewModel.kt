package com.example.fitfoood.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository, private val authRepository: AuthRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
    fun register(name: String, email: String, password: String, dateOfBirth: String) =
        authRepository.userRegister(name, email, password, dateOfBirth)
}