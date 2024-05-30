package com.example.fitfoood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.ArtikelRowBinding

class ArtikelAdapter(private val artikelList: List<Artikel>) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val binding = ArtikelRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtikelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        holder.bind(artikelList[position])
    }

    override fun getItemCount(): Int {
        return artikelList.size
    }

    inner class ArtikelViewHolder(private val binding: ArtikelRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artikel: Artikel) {
            binding.tvName.text = artikel.title
            binding.imgPhoto.setImageResource(artikel.imageResId) // Assuming you have image resource IDs
        }
    }
}

data class Artikel(val title: String, val imageResId: Int)
