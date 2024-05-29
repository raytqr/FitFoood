package com.example.fitfoood.view.foodchecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivitySearchFoodBinding

class SearchFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}