package com.example.fitfoood.view.login


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.MainActivity
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.pref.UserModel
import com.example.fitfoood.databinding.ActivityLoginBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.forgotpass.ForgotPassActivity
import com.example.fitfoood.view.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelFactory.getInstance(this).create(LoginViewModel::class.java)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassActivity::class.java))
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
        binding.SignUpTextView.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password).observe(this) {result ->
                when(result) {
                    is ApiResponse.Error -> {
                        Toast.makeText(this, "Terjadi kesalahan :"+result.message, Toast.LENGTH_SHORT).show()
                    }
                    ApiResponse.Loading -> {}
                    is ApiResponse.Success -> {
                        if (result.data == null) {
                            AlertDialog.Builder(this).apply {
                                setTitle("Oops!")
                                setMessage("Email atau password yang Anda masukkan salah.")
                                setPositiveButton("OK") { _, _ -> }
                                create()
                                show()
                            }
                        }else{
                            val userData = result.data
                            viewModel.saveSession(
                                UserModel(
                                    userData?.user?.username.toString(),
                                    userData?.user?.email.toString(),
                                    userData?.token.toString(),
                                    userData?.user?.dateOfBirth.toString(),
                                    userData?.user?.userId.toString(),
                                )
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            return@observe
                        }

                    }
                }
            }
        }
    }
}
