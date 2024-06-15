package com.example.fitfoood.view.notification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityMainBinding
import com.example.fitfoood.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val notificationFragment = NotificationFragment()
        val fragment = fragmentManager.findFragmentByTag(NotificationFragment::class.java.simpleName)
        if (fragment !is NotificationFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + NotificationFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, notificationFragment, NotificationFragment::class.java.simpleName)
                .commit()
        }
//        binding.toolbar.setOnClickListener {
//            finish()
//        }
    }
}