package com.example.fitfoood.view.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.databinding.ActivityLoginBinding
import com.example.fitfoood.databinding.ActivitySignUpBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.login.LoginActivity
import com.example.fitfoood.view.login.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private var dateBirth: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelFactory.getInstance(this).create(SignUpViewModel::class.java)
        setContentView(binding.root)

        binding.LoginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.dateEditText.setOnClickListener {
            showDatePickerDialog()
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
            val dateOfBirth =  dateBirth




            viewModel.register(username, email, password, dateOfBirth).observe(this) { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                            setPositiveButton("Lanjut") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(this, "Terjadi kesalahan : ${result.message}", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponse.Loading -> {

                    }
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            dateBirth = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(calendar.time)
            binding.dateEditText.setText(dateBirth) // Update EditText with selected date
        }

        DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}
