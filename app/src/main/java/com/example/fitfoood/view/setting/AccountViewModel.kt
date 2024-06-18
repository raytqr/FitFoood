package com.example.fitfoood.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.UserRepository

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    suspend fun logout() {
        userRepository.logout()
    }
}
