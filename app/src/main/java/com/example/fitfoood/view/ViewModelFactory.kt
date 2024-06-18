package com.example.fitfoood.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.data.repository.ArtikelRepository
import com.example.fitfoood.data.repository.AuthRepository
import com.example.fitfoood.data.repository.BMIRepository
import com.example.fitfoood.data.repository.UserRepository
import com.example.fitfoood.di.Injection
import com.example.fitfoood.view.forgotpass.ForgotPassViewModel
import com.example.fitfoood.view.login.LoginViewModel
import com.example.fitfoood.view.main.AccountViewModel
import com.example.fitfoood.view.main.HomeViewModel
import com.example.fitfoood.view.setting.SettingViewModel
import com.example.fitfoood.view.signup.SignUpViewModel
import com.example.fitfoood.view.splash.SplashViewModel

class ViewModelFactory(
    private val repository: ArtikelRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val bmiRepository: BMIRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, userRepository, bmiRepository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ForgotPassViewModel::class.java) -> {
                AccountViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideArtikelRepository(context),
                        Injection.provideUserRepository(context),
                        Injection.provideAuthRepository(context),
                        Injection.provideBMIRRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
