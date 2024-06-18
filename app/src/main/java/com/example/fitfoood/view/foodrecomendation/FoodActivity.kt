package com.example.fitfoood.view.foodrecomendation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityFoodBinding

class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodList = listOf(
            FoodItem("Apple", "52 kcal", R.drawable.img_food_dummy),
            FoodItem("Banana", "96 kcal", R.drawable.img_food_dummy),
            FoodItem("Orange", "47 kcal", R.drawable.img_food_dummy)
        )

        val adapter = FoodAdapter(foodList)
        binding.recyclerViewFood.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFood.adapter = adapter

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.food)

        binding.toolbar.setOnClickListener {
            finish()
        }

    }
}
