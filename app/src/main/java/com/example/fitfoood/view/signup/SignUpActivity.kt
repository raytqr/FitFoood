package com.example.fitfoood.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitfoood.databinding.ActivitySignUpBinding
import com.example.fitfoood.view.form.FormActivity
import com.example.fitfoood.view.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.SignUpButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                binding.nameEditText.error = "Field ini tidak boleh kosong"
                binding.emailEditText.error = "Field ini tidak boleh kosong"
                binding.passwordEditText.error = "Field ini tidak boleh kosong"
            }else{
                startActivity(Intent(this, FormActivity::class.java))
                finish()
            }
        }

        binding.LoginTextView.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}