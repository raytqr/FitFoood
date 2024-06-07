package com.example.fitfoood.view.setting

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityAccountDetailBinding

class AccountDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAccountDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(com.example.fitfoood.R.id.title_toolbar)
        tbTitle.text = "Akun"

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.btnEdit.setOnClickListener { startAccountSetting() }
    }

    private fun startAccountSetting() {
        val intent = Intent(this, AccountSettingActivity::class.java)
        startActivity(intent)
    }
}