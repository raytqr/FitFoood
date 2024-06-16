package com.example.fitfoood.view.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityDetailArtikelBinding
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.HomeViewModel

class DetailArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtikelBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = "your_token_here" // Ganti dengan cara yang sesuai untuk mendapatkan token Anda

        viewModel = ViewModelFactory.getInstance(this).create(HomeViewModel::class.java)

        // Mengambil data artikel dari intent
        val artikel: ArtikelResponseItem? = intent.getParcelableExtra("Artikel")

        artikel?.let { showArticleDetails(it) }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Artikel Detail"



        binding.toolbar.setOnClickListener {
            finish()
        }
    }

    private fun showArticleDetails(article: ArtikelResponseItem) {
        binding.apply {
            articleTitle.text = article.title
            // Menggunakan placeholder jika gambar tidak tersedia
            Glide.with(this@DetailArtikelActivity)
                .load(article.imageUrl)
                .placeholder(R.drawable.img_artikel_dummy)
                .into(articleImage)

            // Tampilkan konten artikel
            textView10.text = article.text
        }
    }
}
