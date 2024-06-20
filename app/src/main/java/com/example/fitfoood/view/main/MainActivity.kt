package com.example.fitfoood

import ProfileFragment
import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fitfoood.databinding.ActivityMainBinding
import com.example.fitfoood.view.foodchecker.CameraActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize fragments
        homeFragment = HomeFragment()
        profileFragment = ProfileFragment()

        // Add homeFragment as the default fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, homeFragment, "HomeFragment")
            .add(R.id.fragment_container, profileFragment, "ProfileFragment")
            .hide(profileFragment)  // Hide profileFragment initially
            .commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showFragment(homeFragment)
                    updateIcons(R.id.navigation_home)
                    true // Return true to indicate the item is selected
                }
                R.id.navigation_analysis -> {
                    if (allPermissionsGranted()) {
                        startCameraActivity()
                    } else {
                        requestCameraPermission()
                    }
                    false // Return false to indicate the item is not selected
                }
                R.id.navigation_profile -> {
                    showFragment(profileFragment)
                    updateIcons(R.id.navigation_profile)
                    true // Return true to indicate the item is selected
                }
                else -> false // Handle unknown item
            }
        }

        // Set default selection
        binding.bottomNavigation.selectedItemId = R.id.navigation_home

        // Request the exact alarm permission
        requestExactAlarmPermission()
    }

    private fun showFragment(fragment: Fragment) {
        // Replace the current fragment with the selected one
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun updateIcons(selectedItemId: Int) {
        binding.bottomNavigation.menu.findItem(R.id.navigation_home)?.setIcon(
            if (selectedItemId == R.id.navigation_home) R.drawable.ic_home_click else R.drawable.ic_home
        )
        binding.bottomNavigation.menu.findItem(R.id.navigation_analysis)?.setIcon(
            if (selectedItemId == R.id.navigation_analysis) R.drawable.ic_camera_click else R.drawable.ic_camera
        )
        binding.bottomNavigation.menu.findItem(R.id.navigation_profile)?.setIcon(
            if (selectedItemId == R.id.navigation_profile) R.drawable.ic_profile_click else R.drawable.ic_profile
        )
    }

    private fun startCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
        updateIcons(R.id.navigation_analysis)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show()
                startCameraActivity()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
    }
}
