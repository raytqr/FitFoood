package com.example.fitfoood.view.foodchecker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityListFoodBinding
import com.example.fitfoood.view.main.ListFood
import com.example.fitfoood.view.main.ListFoodAdapter

class ListFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Daftar Makanan"

        val foodList = listOf(
            ListFood(R.drawable.dummy_img_food, "Ayam Bakar", "200 kcal", "100 gr"),
            ListFood(R.drawable.dummy_img_food, "Selada", "300 kcal", "150 gr"),
            ListFood(R.drawable.dummy_img_food, "Nasi Putih", "400 kcal", "150 gr")
        )

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ListFoodAdapter(foodList)
        recyclerView.adapter = adapter

        binding.toolbar.setOnClickListener {
            finish()
        }
    }
}