package com.example.fitfoood.view.foodchecker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.R
import com.example.fitfoood.data.response.FoodResponse

class FoodSearchAdapter(
    private val foodList: List<FoodResponse>,
    private val itemClickListener: (FoodResponse) -> Unit
) : RecyclerView.Adapter<FoodSearchAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_row, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)
        holder.itemView.setOnClickListener { itemClickListener(food) }
    }

    override fun getItemCount(): Int = foodList.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodName: TextView = itemView.findViewById(R.id.tv_name)
        private val foodCalories: TextView = itemView.findViewById(R.id.food_kcal)

        fun bind(food: FoodResponse) {
            foodName.text = food.name
            foodCalories.text = "${food.calories} kcal"
        }
    }
}
