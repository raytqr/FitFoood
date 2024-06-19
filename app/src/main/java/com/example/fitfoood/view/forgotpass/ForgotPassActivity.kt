package com.example.fitfoood.view.forgotpass

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityForgotPasswordBinding
import com.example.fitfoood.view.ViewModelFactory

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModel: ForgotViewModel
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelFactory.getInstance(this).create(ForgotViewModel::class.java)

        viewModel.getSession().observe(this) { user ->
            email = user.email
        }

        binding.confirmEmail.setOnClickListener {
            val emailInput = binding.emailEditText.text.toString().trim()
            if (emailInput == email) {
                startActivity(Intent(this, NewPassActivity::class.java))
            } else {
                Toast.makeText(this, "$email Masukkan email terakhir Anda login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
