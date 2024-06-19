package com.example.fitfoood.view.forgotpass

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.pref.BMIModel
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.data.repository.AuthRepository
import com.example.fitfoood.data.repository.BMIRepository
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.data.response.BMI
import com.example.fitfoood.data.response.GetBMIResponse
import com.example.fitfoood.data.response.PostBMIResponse
import com.example.fitfoood.data.response.UpdatUserResponse
import com.example.fitfoood.data.response.UserUpdate
import kotlinx.coroutines.launch

class ForgotViewModel (private val repository: AuthRepository , private val userRepository: UserRepository) : ViewModel() {


    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun updateUser( userId:String, userUpdate: UserUpdate): LiveData<ApiResponse<UpdatUserResponse>> {
        return repository.updateUser(userId, userUpdate)
    }

}