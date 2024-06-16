package com.example.fitfoood

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.Artikel2RowBinding
import com.example.fitfoood.view.artikel.DetailArtikelActivity

class Artikel2Adapter(private val artikelList: List<Artikel>) : RecyclerView.Adapter<Artikel2Adapter.ArtikelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val binding = Artikel2RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtikelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        holder.bind(artikelList[position])
    }

    override fun getItemCount(): Int {
        return artikelList.size
    }

    inner class ArtikelViewHolder(private val binding: Artikel2RowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artikel: Artikel) {
            binding.tvName.text = artikel.title
            binding.imgPhoto.setImageResource(artikel.imageResId)

            // Set the icon based on the category
            when (artikel.category) {
                1 -> binding.idCategoryArtikel2.setImageResource(R.drawable.icon_hidupsehat)
                2 -> binding.idCategoryArtikel2.setImageResource(R.drawable.icon_olahraga)
                else -> binding.idCategoryArtikel2.setImageResource(R.drawable.icon_hidupsehat) // Optional default icon
            }

            // Set click listener to navigate to DetailArtikelActivity
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailArtikelActivity::class.java).apply {
                    putExtra("ARTICLE_TITLE", artikel.title)
                    putExtra("ARTICLE_IMAGE", artikel.imageResId)
                    putExtra("ARTICLE_CATEGORY", artikel.category)
                }
                context.startActivity(intent)
            }
        }
    }
}
data class Artikel(val title: String, val imageResId: Int, val category: Int)

