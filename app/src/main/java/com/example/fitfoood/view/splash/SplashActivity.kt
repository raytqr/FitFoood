package com.example.fitfoood.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.fitfoood.MainActivity
import com.example.fitfoood.databinding.ActivitySplashBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.welcome.WelcomeScreenActivity

class SplashActivity : AppCompatActivity() {
//    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelFactory.getInstance(this).create(SplashViewModel::class.java)

        Handler(mainLooper).postDelayed({
            viewModel.getSession().observe(this) { user ->
                if (user.isLogin) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, WelcomeScreenActivity::class.java))
                }
                finish()
            }
        }, SPLASH_DELAY)


    }

    companion object {
        private const val SPLASH_DELAY: Long = 2000
    }
}