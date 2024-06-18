package com.example.fitfoood.view.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem

class HomeViewModel(private val repository: ArtikelRepository,private val userRepository: UserRepository) : ViewModel() {
    fun getAllArticles(token: String): LiveData<ApiResponse<List<ArtikelResponseItem>>> {
        return repository.getAllArticle(token)
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

//    fun getArticleDetails(token: String, articleId: Int): LiveData<ApiResponse<ArtikelResponseItem>> {
//        return repository.getArticleDetails(token, articleId)
//    }
}
