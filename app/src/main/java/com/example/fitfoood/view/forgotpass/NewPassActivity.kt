package com.example.fitfoood.view.forgotpass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.data.response.UserUpdate
import com.example.fitfoood.databinding.ActivityNewPasswordBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.login.LoginActivity

class NewPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    private lateinit var viewModel: ForgotViewModel
    private lateinit var userId: String
    private lateinit var email: String
    private lateinit var username: String
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelFactory.getInstance(this).create(ForgotViewModel::class.java)
        viewModel.getSession().observe(this) { user ->
            userId = user.userId
            email = user.email
            username = user.username

        }

        binding.loginButton.setOnClickListener {
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPassEditText.text.toString().trim()
            if (password == confirmPassword) {
                viewModel.updateUser( userId, dataUpdate()).observe(this) { response ->
                    if (response != null) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }


    }

    private fun dataUpdate(): UserUpdate {
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPassEditText.text.toString().trim()
        return UserUpdate(username = username, email = email, password = password)

    }
}