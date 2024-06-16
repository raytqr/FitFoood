package com.example.fitfoood.view.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem

class HomeViewModel(private val repository: ArtikelRepository) : ViewModel() {
    fun getAllArticles(token: String): LiveData<ApiResponse<List<ArtikelResponseItem>>> {
        return repository.getAllArticle(token)
    }

//    fun getArticleDetails(token: String, articleId: Int): LiveData<ApiResponse<ArtikelResponseItem>> {
//        return repository.getArticleDetails(token, articleId)
//    }
}
