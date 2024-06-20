package com.example.fitfoood.view.foodrecomendation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.databinding.ActivityFoodBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.HomeViewModel

class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    private lateinit var foodViewModel: HomeViewModel
    private lateinit var token: String
    private lateinit var bmiLabel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodViewModel = ViewModelFactory.getInstance(this).create(HomeViewModel::class.java)

        foodViewModel.getSession().observe(this) { user ->
            token = user.token
            foodViewModel.getSessionBMI().observe(this) { result ->
                bmiLabel = result.label
                if(bmiLabel == "") {
                    bmiLabel = "ideal"
                }
                showRecycleList()
                binding.bmiHead.text = bmiLabel
            }
        }

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.food)

        binding.toolbar.setOnClickListener {
            finish()
        }
    }

    private fun showRecycleList() {
        foodViewModel.getFoodRec(token).observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val list = response.data?.filter { it.label == bmiLabel }
                    val adapter = FoodAdapter(list!!)
                    binding.recyclerViewFood.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerViewFood.adapter = adapter
                }
                is ApiResponse.Error -> {

                }
                is ApiResponse.Loading -> {

                }
            }
        }
    }
}
