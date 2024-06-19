package com.example.fitfoood.view.workoutrecomendation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitfoood.ArtikelAdapter
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.data.response.WoBMIResponseItem
import com.example.fitfoood.databinding.ArtikelRowBinding
import com.example.fitfoood.databinding.FoodRowBinding
import com.example.fitfoood.databinding.WoRowBinding
import com.example.fitfoood.view.artikel.DetailArtikelActivity
import com.example.fitfoood.view.foodrecomendation.FoodAdapter
import com.example.fitfoood.view.foodrecomendation.FoodItem

class WorkOutAdapter (private val woList: List<WoBMIResponseItem>) : RecyclerView.Adapter<WorkOutAdapter.ViewHolder>() {

    class ViewHolder(private val binding: WoRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workOut: WoBMIResponseItem) {
            binding.dummywoname.text = workOut.title

            Glide.with(binding.root)
                .load(workOut.imageUrl)
                .into(binding.imgwodummy)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workOut = woList[position]
        holder.bind(workOut)
    }

    override fun getItemCount(): Int {
        return woList.size
    }
}