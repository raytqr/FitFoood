package com.example.fitfoood.view.signup

import com.example.fitfoood.view.login.LoginActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.data.request.RegisterRequest
import com.example.fitfoood.data.retrofit.ApiService
import com.example.fitfoood.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://cc-fitfood-xrre4szdka-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.SignUpButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val dateOfBirth = binding.dateEditText.text.toString()

            // Validate input
            if (name.isBlank() || email.isBlank() || password.isBlank() || dateOfBirth.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Perform registration request
            val registerCall = apiService.register(RegisterRequest(name, email, password, dateOfBirth))
            registerCall.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Handle successful registration, navigate to login
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        val errorMessage = response.errorBody()?.string()
                        Log.e("SignUpActivity", "Registration failed: $errorMessage")
                        Toast.makeText(this@SignUpActivity, "Registration failed: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("SignUpActivity", "Error: ${t.message}")
                    Toast.makeText(this@SignUpActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.LoginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
        genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioMan -> {
                    // Handle 'Man' selection
                }
                R.id.radioWoman -> {
                    // Handle 'Woman' selection
                }
            }
        }

        binding.dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        setupView()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateEditText(calendar)
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateEditText(calendar: Calendar) {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        binding.dateEditText.setText(sdf.format(calendar.time))
    }

    private fun setupView() {
        // Hide status bar and action bar
        supportActionBar?.hide()
    }
}
