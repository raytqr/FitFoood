package com.example.fitfoood.view.foodchecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivitySearchFoodBinding

class SearchFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Cari Makanan"

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.btnFillManual.setOnClickListener {
            startActivityForResult(Intent(this, FillManualActivity::class.java), MANUAL_ENTRY_REQUEST_CODE)
        }

        // Implement search functionality and item selection here
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MANUAL_ENTRY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    companion object {
        private const val MANUAL_ENTRY_REQUEST_CODE = 1
    }
}
