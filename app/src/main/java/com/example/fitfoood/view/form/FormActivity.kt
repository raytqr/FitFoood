package com.example.fitfoood.view.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityFormBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateEditText = findViewById<TextInputEditText>(R.id.dateEditText)
        dateEditText.setOnClickListener { showDatePicker() }

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = Date(selection)
            val dateEditText = findViewById<TextInputEditText>(R.id.dateEditText)
            dateEditText.setText(dateFormat.format(date))
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }
}