package com.example.fitfoood.view.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.R
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.databinding.ActivitySignUpBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.login.LoginActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private var dateBirth: String = ""
    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(SignUpViewModel::class.java)
        setContentView(binding.root)

        binding.LoginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.dateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        binding.genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            gender = if (checkedId == R.id.radioMan) {
                "Male"
            } else {
                "Female"
            }
        }

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.SignUpButton.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.passwordRepeatEditText.text.toString()
            val dateOfBirth = dateBirth

            if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() || dateOfBirth.isBlank() || gender.isBlank()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(username, email, password, dateOfBirth, gender).observe(this) { result ->
                when (result) {
                    is ApiResponse.Success<*> -> {
                        showSuccessDialog(email)
                    }
                    is ApiResponse.Error<*> -> {
                        Log.d("SignUpActivity", "Error message: ${result.message}")
                        runOnUiThread {
                            if (result.message?.contains("Email already exists") == true) {
                                Toast.makeText(this@SignUpActivity, "Email is already registered", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@SignUpActivity, "An error occurred: ${result.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    ApiResponse.Loading -> {
                        // Handle loading state if needed
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateBirth = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(calendar.time)
            binding.dateEditText.setText(dateBirth) // Update EditText with selected date
        }

        DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showSuccessDialog(email: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_signup_success, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = builder.show()

        val btnOK = dialogView.findViewById<Button>(R.id.btnOK)
        btnOK.setOnClickListener {
            alertDialog.dismiss()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}
