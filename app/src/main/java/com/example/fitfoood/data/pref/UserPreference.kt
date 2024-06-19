package com.example.fitfoood.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessionbmi")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[Name_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[DATE_BIRTH] ?: "",
                preferences[USER_ID] ?: "",
            )
        }
    }
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[Name_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[DATE_BIRTH] = user.dateOfBirth
            preferences[USER_ID] = user.userId
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[Name_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[DATE_BIRTH] ?: "",
                preferences[USER_ID] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            val email = preferences[EMAIL_KEY] ?: ""
            val userid = preferences[USER_ID] ?: ""
            val userame = preferences[Name_KEY] ?: ""
            preferences.clear()
            preferences[EMAIL_KEY] = email
            preferences[USER_ID] = userid
            preferences[Name_KEY] = userame
        }
    }




    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val Name_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val DATE_BIRTH = stringPreferencesKey("dateOfBirth")
        private val USER_ID = stringPreferencesKey("userId")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}