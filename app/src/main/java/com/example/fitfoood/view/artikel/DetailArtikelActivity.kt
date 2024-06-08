package com.example.fitfoood.view.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityDetailArtikelBinding

class DetailArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("ARTICLE_TITLE")
        val imageResId = intent.getIntExtra("ARTICLE_IMAGE", 0)
        val category = intent.getIntExtra("ARTICLE_CATEGORY", 0)

        binding.articleTitle.text = title
        binding.articleImage.setImageResource(imageResId)

        // Set the toolbar title based on category
        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = when (category) {
            1 -> "Hidup Sehat"
            2 -> "Olahraga"
            else -> "Artikel"
        }

        binding.toolbar.setOnClickListener {
            finish()
        }
    }
}
