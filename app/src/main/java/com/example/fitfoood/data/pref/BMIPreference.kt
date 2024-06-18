package com.example.fitfoood.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore2: DataStore<Preferences> by preferencesDataStore(name = "session")

class BMIPreference private constructor(private val dataStore2: DataStore<Preferences>) {


    suspend fun saveSessionBMI(bmiModel: BMIModel) {
        dataStore2.edit { preferences ->
            preferences[WEIGHT_KEY] = bmiModel.weight.toString()
            preferences[HEIGHT_KEY] = bmiModel.height.toString()
            preferences[BMI_KEY] = bmiModel.bmiUser
            preferences[BMI_LABEL] = bmiModel.label
        }
    }

    fun getSessionBMI(): Flow<BMIModel> {
        return dataStore2.data.map { preferences ->
            BMIModel(
                preferences[WEIGHT_KEY] ?: "",
                preferences[HEIGHT_KEY] ?: "",
                preferences[BMI_KEY] ?: "",
                preferences[BMI_LABEL] ?: ""
            )
        }
    }





    companion object {
        @Volatile
        private var INSTANCE: BMIPreference? = null

        private val WEIGHT_KEY = stringPreferencesKey("weight")
        private val HEIGHT_KEY = stringPreferencesKey("height")
        private val BMI_KEY = stringPreferencesKey("bmi")
        private val BMI_LABEL = stringPreferencesKey("label")


        fun getInstance(dataStore: DataStore<Preferences>): BMIPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = BMIPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}