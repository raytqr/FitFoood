package com.example.fitfoood.view.workoutrecomendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.databinding.ActivityWorkOutBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.HomeViewModel

class WorkOutActivity : AppCompatActivity() {
     private lateinit var binding: ActivityWorkOutBinding
     private lateinit var homeViewModel: HomeViewModel
     private lateinit var token: String
     private lateinit var bmiLabel:String

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityWorkOutBinding.inflate(layoutInflater)
         setContentView(binding.root)

         homeViewModel = ViewModelFactory.getInstance(this).create(HomeViewModel::class.java)

         homeViewModel.getSession().observe(this) { user ->
             token = user.token
             homeViewModel.getSessionBMI().observe(this){result->
                 bmiLabel = result.label
                     if (bmiLabel == "") {
                         bmiLabel = "ideal"
                     }
                     showRecycleList()
                 binding.bmiHead.text = bmiLabel

             }
         }



         val tbTitle = findViewById<TextView>(R.id.title_toolbar)
         tbTitle.text = getString(R.string.workout)

         binding.toolbar.setOnClickListener {
             finish()
         }



     }

    private fun showRecycleList() {
        homeViewModel.getWoRec(token).observe(this ){ response ->
            when (response) {
                is ApiResponse.Success -> {
                    val list = response.data?.filter { it.label == bmiLabel }
                    val adapter = WorkOutAdapter(list!!)
                    binding.recyclerViewWorkOut.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerViewWorkOut.adapter = adapter
                }

                is ApiResponse.Error -> {
                    // Show error message
                }

                is ApiResponse.Loading -> {
                    // Show loading
                }
            }
        }
    }
}