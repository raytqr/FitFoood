package com.example.fitfoood.view.foodchecker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityResultRecBinding

class ResultRecActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultRecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultRecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Hasil Rekomendasi"

        val totalCalories = intent.getIntExtra("totalCalories", 0)
        binding.totalKcal.text = "$totalCalories kcal"

        binding.toolbar.setOnClickListener {
            finish()
        }
    }
}
