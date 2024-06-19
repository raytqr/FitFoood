package com.example.fitfoood.view.foodchecker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityResultRecBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.HomeViewModel

class ResultRecActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultRecBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bmiUser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultRecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.recommendation_result)

        val totalCalories = intent.getIntExtra("totalCalories", 0)
        binding.totalKcal.text = "$totalCalories kcal"

        binding.toolbar.setOnClickListener {
            finish()
        }

        homeViewModel = ViewModelFactory.getInstance(this).create(HomeViewModel::class.java)

    }
}
