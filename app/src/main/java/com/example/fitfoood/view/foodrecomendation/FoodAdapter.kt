package com.example.fitfoood.view.foodrecomendation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitfoood.R
import com.example.fitfoood.data.response.FoodBMIResponseItem
import com.example.fitfoood.data.response.WoBMIResponseItem
import com.example.fitfoood.databinding.FoodArtikelRowBinding
import com.example.fitfoood.databinding.FoodRowBinding
import com.example.fitfoood.databinding.WoRowBinding
import com.example.fitfoood.view.workoutrecomendation.WorkOutAdapter

class FoodAdapter(private val foodList: List<FoodBMIResponseItem>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FoodArtikelRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodBMIResponseItem) {
            binding.dummyfoodname.text = food.title
            binding.foodKcal.text = food.calory.toString()
            Glide.with(binding.root)
                .load(food.imageUrl)
                .into(binding.imgfooddummy)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodArtikelRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}
