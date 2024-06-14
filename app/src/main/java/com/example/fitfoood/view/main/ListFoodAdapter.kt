package com.example.fitfoood.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.FoodRowBinding

class ListFoodAdapter(
    private val foodList: MutableList<ListFood>,
    private var totalCalories: Int,
    private val onDeleteClick: (ListFood) -> Unit
) : RecyclerView.Adapter<ListFoodAdapter.ListFoodViewHolder>() {

    fun setTotalCalories(totalCalories: Int) {
        this.totalCalories = totalCalories
        notifyDataSetChanged() // Perbarui tampilan setelah total kalori berubah
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFoodViewHolder {
        val binding = FoodRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class ListFoodViewHolder(private val binding: FoodRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listFood: ListFood) {
            binding.tvName.text = listFood.title
            binding.imgPhoto.setImageResource(listFood.image)
            binding.foodKcal.text = listFood.kcal
            binding.foodGr.text = listFood.gr
            binding.deleteButton.setOnClickListener {
                onDeleteClick(listFood)
            }
        }
    }

    fun removeItem(food: ListFood) {
        val position = foodList.indexOf(food)
        if (position >= 0) {
            foodList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

data class ListFood(val image: Int, val title: String, val kcal: String, val gr: String)
