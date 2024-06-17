package com.example.fitfoood.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfoood.data.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel(){
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}