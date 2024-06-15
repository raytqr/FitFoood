package com.example.fitfoood.view.foodchecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.databinding.ActivityFillManualBinding

class FillManualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFillManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(com.example.fitfoood.R.id.title_toolbar)
        tbTitle.text = "Tambah Makanan"

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.addBtn.setOnClickListener {
            val foodName = binding.foodEditText.text.toString().trim()
            val calories = binding.caloriesEditText.text.toString().toIntOrNull()

            if (foodName.isNotEmpty() && calories != null && calories > 0) {
                val intent = Intent()
                intent.putExtra("foodName", foodName)
                intent.putExtra("calories", calories)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                // Handle input validation or error message display
            }
        }
    }
}
