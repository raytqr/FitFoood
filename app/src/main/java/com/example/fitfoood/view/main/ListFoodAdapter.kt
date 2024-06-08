package com.example.fitfoood.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.FoodRowBinding

class ListFoodAdapter(private val foodList: List<ListFood>) : RecyclerView.Adapter<ListFoodAdapter.ListFoodViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodAdapter.ListFoodViewHolder {
        val binding = FoodRowBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFoodAdapter.ListFoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int {
        return  foodList.size
    }

    inner class ListFoodViewHolder (private val binding: FoodRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listFood: ListFood) {
            binding.tvName.text = listFood.title
            binding.imgPhoto.setImageResource(listFood.image)
            binding.foodKcal.text = listFood.kcal
            binding.foodGr.text = listFood.gr
        }
    }
}

data class ListFood (val image: Int, val title: String, val kcal: String, val gr: String)