package com.example.fitfoood.view.workoutrecomendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.FoodRowBinding
import com.example.fitfoood.databinding.WoRowBinding
import com.example.fitfoood.view.foodrecomendation.FoodAdapter
import com.example.fitfoood.view.foodrecomendation.FoodItem

class WorkOutAdapter (private val woList: List<WorkOutItem>) : RecyclerView.Adapter<WorkOutAdapter.WorkOutViewHolder>() {

    inner class WorkOutViewHolder(val binding: WoRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkOutViewHolder {
        val binding = WoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkOutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkOutViewHolder, position: Int) {
        val foodItem = woList[position]
        holder.binding.dummywoname.text = foodItem.name
        holder.binding.imgwodummy.setImageResource(foodItem.imageResId)
    }

    override fun getItemCount(): Int = woList.size
}