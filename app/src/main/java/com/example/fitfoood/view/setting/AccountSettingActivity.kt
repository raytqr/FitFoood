package com.example.fitfoood.view.setting

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityAccountDetailBinding
import com.example.fitfoood.databinding.ActivityAccountSettingBinding

class AccountSettingActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAccountSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(com.example.fitfoood.R.id.title_toolbar)
        tbTitle.text = "Pengaturan Akun"

        binding.toolbar.setOnClickListener {
            finish()
        }


    }
}