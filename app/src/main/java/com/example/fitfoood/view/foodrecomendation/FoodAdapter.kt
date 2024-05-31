package com.example.fitfoood.view.foodrecomendation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FoodRowBinding

class FoodAdapter(private val foodList: List<FoodItem>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(val binding: FoodRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.binding.tvName.text = foodItem.name
        holder.binding.textView6.text = foodItem.calories
        holder.binding.imgPhoto.setImageResource(foodItem.imageResId)
    }

    override fun getItemCount(): Int = foodList.size
}
