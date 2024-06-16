package com.example.fitfoood.view.artikel

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitfoood.R
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.databinding.Artikel2RowBinding
import com.example.fitfoood.databinding.ArtikelRowBinding
import com.example.fitfoood.view.artikel.DetailArtikelActivity

class Artikel2Adapter(private var listItem: List<ArtikelResponseItem>) : RecyclerView.Adapter<Artikel2Adapter.ViewHolder>() {

    class ViewHolder(private val binding: Artikel2RowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artikel: ArtikelResponseItem) {
            binding.tvName.text = artikel.title

            Glide.with(binding.root)
                .load(artikel.imageUrl)
                .into(binding.imgPhoto)

            val categoryIcon = when (artikel.category) {
                "hidup sehat" -> R.drawable.icon_hidupsehat
                "olahraga" -> R.drawable.icon_olahraga
                else -> R.drawable.icon_hidupsehat // Optional default icon
            }
            binding.idCategoryArtikel.setImageResource(categoryIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = Artikel2RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikel = listItem[position]
        holder.bind(artikel)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailArtikelActivity::class.java)
            intent.putExtra("Artikel", artikel)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateData(newList: List<ArtikelResponseItem>) {
        listItem = newList
        notifyDataSetChanged()
    }
}
