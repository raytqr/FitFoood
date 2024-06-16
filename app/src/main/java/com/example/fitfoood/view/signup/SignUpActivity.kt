package com.example.fitfoood.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivitySignUpBinding
import com.example.fitfoood.view.form.FormActivity
import com.example.fitfoood.view.login.LoginActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.SignUpButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                binding.nameEditText.error = "Field ini tidak boleh kosong"
                binding.emailEditText.error = "Field ini tidak boleh kosong"
                binding.passwordEditText.error = "Field ini tidak boleh kosong"
            }else{
                startActivity(Intent(this, FormActivity::class.java))
                finish()
            }
        }

        binding.LoginTextView.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
        genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioMan -> {
                    // Handle 'Man' selection
                }
                R.id.radioWoman -> {
                    // Handle 'Woman' selection
                }
            }
        }

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