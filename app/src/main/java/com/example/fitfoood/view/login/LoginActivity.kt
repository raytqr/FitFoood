package com.example.fitfoood.view.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.MainActivity
import com.example.fitfoood.data.request.LoginRequest
import com.example.fitfoood.data.response.LoginResponse
import com.example.fitfoood.data.retrofit.ApiService
import com.example.fitfoood.databinding.ActivityLoginBinding
import com.example.fitfoood.view.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService // Retrofit service interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cc-fitfood-xrre4szdka-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Perform login request
            val loginCall = apiService.login(LoginRequest(email, password))
            loginCall.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        // Handle successful login, save token or navigate to main activity
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.SignUpTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.forgotPassword.setOnClickListener {
            // Handle forgot password action
        }

        setupView()
    }

    private fun setupView() {
        // Hide status bar and action bar
        supportActionBar?.hide()
    }
}
