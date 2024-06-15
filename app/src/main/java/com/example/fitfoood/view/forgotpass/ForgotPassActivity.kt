package com.example.fitfoood.view.forgotpass

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityForgotPasswordBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmEmail.setOnClickListener { startConfirmEmail() }
    }

    private fun startConfirmEmail() {
        val intent = Intent(this, VerifCodeActivity::class.java)
        startActivity(intent)
    }

}