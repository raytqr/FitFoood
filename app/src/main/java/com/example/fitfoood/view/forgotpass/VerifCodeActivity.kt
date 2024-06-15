package com.example.fitfoood.view.forgotpass

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityVerificationCodeBinding
import com.example.fitfoood.view.foodchecker.ResultRecActivity
import java.util.Locale
import java.util.concurrent.TimeUnit

class VerifCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationCodeBinding

    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textView = findViewById(R.id.countdown)

        val duration = TimeUnit.MINUTES.toMillis(1)

        object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                val sDuration = String.format(Locale.ENGLISH, "%02d : %02d", minutes, seconds)
                textView.text = sDuration
            }

            override fun onFinish() {
                textView.visibility = View.GONE
                Toast.makeText(
                    applicationContext, "Countdown timer has ended", Toast.LENGTH_LONG
                ).show()
            }
        }.start()

        binding.confirmCodeButton.setOnClickListener { startNewPass() }
    }

    private fun startNewPass() {
        val intent = Intent(this, NewPassActivity::class.java)
        startActivity(intent)
    }
}
