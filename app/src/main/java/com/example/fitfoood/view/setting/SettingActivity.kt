package com.example.fitfoood.view.setting

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityNotificationBinding
import com.example.fitfoood.databinding.ActivitySettingBinding
import com.example.fitfoood.databinding.DboxLogoutBinding
import com.example.fitfoood.view.notification.NotificationFragment
import com.example.fitfoood.view.reminder.ReminderActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var  binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(SettingFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.commit()
    }

}