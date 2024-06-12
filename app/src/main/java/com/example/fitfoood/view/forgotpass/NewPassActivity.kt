package com.example.fitfoood.view.forgotpass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityNewPasswordBinding

class NewPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}