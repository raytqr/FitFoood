package com.example.fitfoood.view.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.fitfoood.view.main.MainActivity
import com.example.fitfoood.databinding.ActivityLoginBinding
import com.example.fitfoood.view.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
//    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
//        viewModel = ViewModelFactory.getInstance(this).create(LoginViewModel::class.java)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if(email == "admin@gmail.com" && password == "admin123"){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else if(email.isEmpty() || password.isEmpty()){
                binding.emailEditText.error = "Field ini tidak boleh kosong"
                binding.passwordEditText.error = "Field ini tidak boleh kosong"
            }else{
                Toast.makeText(this, "Email atau password yang Anda masukkan salah.", Toast.LENGTH_SHORT).show()
            }

        }

        binding.SignUpTextView.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        setupView()
//        setupAction()
//        playAnimation()
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

//    private fun setupAction() {
//        binding.loginButton.setOnClickListener {
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//            viewModel.login(email, password).observe(this) {result ->
//                when(result) {
//                    is ApiResponse.Error -> {
//                        Toast.makeText(this, "Terjadi kesalahan :"+result.message, Toast.LENGTH_SHORT).show()
//                    }
//                    ApiResponse.Loading -> {}
//                    is ApiResponse.Success -> {
//                        if (result.data == null) {
//                            AlertDialog.Builder(this).apply {
//                                setTitle("Oops!")
//                                setMessage("Email atau password yang Anda masukkan salah.")
//                                setPositiveButton("OK") { _, _ -> }
//                                create()
//                                show()
//                            }
//                        }
//                        viewModel.saveSession(UserModel(result.data?.loginResult?.name!!,email, result.data?.loginResult?.token!!))
//                        startActivity(Intent(this, MainActivity::class.java))
//                        return@observe
//                    }
//                }
//            }
//        }


}