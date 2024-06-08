package com.example.fitfoood.view.foodchecker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivitySearchFoodBinding

class SearchFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(com.example.fitfoood.R.id.title_toolbar)
        tbTitle.text = "Cari Makanan"

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.btnFillManual.setOnClickListener { startFillManual() }
    }

    private fun startFillManual() {
        val intent = Intent(this, FillManualActivity::class.java)
        startActivity(intent)
    }
}