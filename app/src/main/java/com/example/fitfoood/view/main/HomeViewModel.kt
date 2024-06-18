package com.example.fitfoood.view.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.pref.BMIModel
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.data.repository.BMIRepository
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.data.response.BMI
import com.example.fitfoood.data.response.GetBMIResponse
import com.example.fitfoood.data.response.PostBMIResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ArtikelRepository,private val userRepository: UserRepository,private val bmiRepository: BMIRepository) : ViewModel() {
    fun getAllArticles(token: String): LiveData<ApiResponse<List<ArtikelResponseItem>>> {
        return repository.getAllArticle(token)
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getBMI(token: String, idhealth: String): LiveData<ApiResponse<GetBMIResponse>> {
        return repository.getBMI(token, idhealth)
    }

    fun postBMI(token: String, idhealth: String, bmi: BMI): LiveData<ApiResponse<PostBMIResponse>> {
        return repository.postBMI(token, idhealth, bmi)
    }

    fun saveSessionBMI(bmiModel: BMIModel) {
        viewModelScope.launch {
            bmiRepository.saveSessionBMI(bmiModel)
        }
    }
    fun getSessionBMI(): LiveData<BMIModel> {
        return bmiRepository.getSessionBMI().asLiveData()
    }
}
