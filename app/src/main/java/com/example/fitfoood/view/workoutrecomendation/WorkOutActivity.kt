package com.example.fitfoood.view.workoutrecomendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityWorkOutBinding

class WorkOutActivity : AppCompatActivity() {
     private lateinit var binding: ActivityWorkOutBinding

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityWorkOutBinding.inflate(layoutInflater)
         setContentView(binding.root)

         val workOutList = listOf(
             WorkOutItem("Push Up", R.drawable.img_wo_dummy),
             WorkOutItem("Sit Up", R.drawable.img_wo_dummy),
             WorkOutItem("Squat", R.drawable.img_wo_dummy)
         )

         val adapter = WorkOutAdapter(workOutList)
         binding.recyclerViewWorkOut.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
         binding.recyclerViewWorkOut.adapter = adapter
     }
}