package com.example.fitfoood.data.repository

import com.example.fitfoood.data.pref.BMIModel
import com.example.fitfoood.data.pref.BMIPreference
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class BMIRepository private constructor(
    private val bmiPreference: BMIPreference
) {

    suspend fun saveSessionBMI(bmiModel: BMIModel) {
        bmiPreference.saveSessionBMI(bmiModel)
    }

    fun getSessionBMI(): Flow<BMIModel> {
        return bmiPreference.getSessionBMI()
    }




    companion object {
        @Volatile
        private var instance: BMIRepository? = null
        fun getInstance(bmiPreference: BMIPreference): BMIRepository =
            instance ?: synchronized(this) {
                instance ?: BMIRepository(bmiPreference)
            }.also { instance = it }
    }
}
