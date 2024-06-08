package com.example.fitfoood.view.setting

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivitySettingBinding
import com.example.fitfoood.databinding.DboxLogoutBinding
import com.example.fitfoood.view.reminder.ReminderActivity

class SettingActivity : AppCompatActivity() {
    private lateinit var  binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Pengaturan"

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.arrowAccount.setOnClickListener { startAccountDetail() }
        binding.arrowLogout.setOnClickListener { showLogoutDialog() }
        binding.arrowLanguage.setOnClickListener { startLanguageSetting() }
        binding.arrowReminder.setOnClickListener { startReminderSetting() }
    }

    private fun startReminderSetting() {
        val intent = Intent(this, ReminderActivity::class.java)
        startActivity(intent)
    }

    private fun startLanguageSetting() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun startAccountDetail() {
        val intent = Intent(this, AccountDetailActivity::class.java)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dbox_logout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnYes : Button = dialog.findViewById(R.id.btn_yes)
        val btnNo : Button = dialog.findViewById(R.id.btn_no)

        btnYes.setOnClickListener {
            dialog.dismiss()
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}