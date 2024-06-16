package com.example.fitfoood.di

import android.content.Context
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.source.ApiConfig

object Injection {
    fun provideArtikelRepository(context: Context): ArtikelRepository {
        val apiService = ApiConfig.getApiService()
        return ArtikelRepository()
    }
    fun provideUserRepository(context: Context): ArtikelRepository {
        val apiService = ApiConfig.getApiService()
        return ArtikelRepository()
    }
}