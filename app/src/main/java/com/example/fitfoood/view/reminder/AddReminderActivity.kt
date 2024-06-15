package com.example.fitfoood.view.reminder

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityAddReminderBinding

class AddReminderActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityAddReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Tambah Pengingat"

        binding.toolbar.setOnClickListener {
            finish()
        }
    }
}