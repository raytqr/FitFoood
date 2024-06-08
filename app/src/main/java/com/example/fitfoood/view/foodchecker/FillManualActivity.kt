package com.example.fitfoood.view.foodchecker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityFillManualBinding
import com.example.fitfoood.databinding.ActivitySearchFoodBinding

class FillManualActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFillManualBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(com.example.fitfoood.R.id.title_toolbar)
        tbTitle.text = "Tambah Makanan"

        binding.toolbar.setOnClickListener {
            finish()
        }
    }
}