package com.example.fitfoood.di

import android.content.Context
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.data.pref.UserPreference
import com.example.fitfoood.data.pref.dataStore
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.data.repository.AuthRepository
import com.example.fitfoood.data.source.ApiConfigUser
import com.example.fitfoood.source.ApiConfig

object Injection {
    fun provideArtikelRepository(context: Context): ArtikelRepository {
        val apiService = ApiConfig.getApiService()
        return ArtikelRepository()
    }
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfigUser.getApiServiceUser()
        val authPreferencesDataSource = (context)
        return AuthRepository(apiService)
    }
}