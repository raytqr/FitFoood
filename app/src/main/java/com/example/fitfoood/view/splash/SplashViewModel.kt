package com.example.fitfoood.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.pref.UserModel

class SplashViewModel(private val userRepository: UserRepository): ViewModel(){

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}