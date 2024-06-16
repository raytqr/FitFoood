package com.example.fitfoood

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fitfoood.databinding.ActivityMainBinding
import com.example.fitfoood.view.foodchecker.CameraActivity
import com.example.fitfoood.view.main.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> {
                    selectedFragment = HomeFragment()
                    updateIcons(R.id.navigation_home)
                }
                R.id.navigation_analysis -> {
                    if (allPermissionsGranted()) {
                        val intent = Intent(this, CameraActivity::class.java)
                        startActivity(intent)
                        updateIcons(R.id.navigation_analysis)
                    } else {
                        requestPermissionLauncher.launch(REQUIRED_PERMISSION)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    selectedFragment = ProfileFragment()
                    updateIcons(R.id.navigation_profile)
                }
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

        // Set default selection
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }

    private fun updateIcons(selectedItemId: Int) {
        val homeIcon = if (selectedItemId == R.id.navigation_home) R.drawable.ic_home_click else R.drawable.ic_home
        val cameraIcon = if (selectedItemId == R.id.navigation_analysis) R.drawable.ic_camera_click else R.drawable.ic_camera
        val profileIcon = if (selectedItemId == R.id.navigation_profile) R.drawable.ic_profile_click else R.drawable.ic_profile

        binding.bottomNavigation.menu.findItem(R.id.navigation_home).setIcon(homeIcon)
        binding.bottomNavigation.menu.findItem(R.id.navigation_analysis).setIcon(cameraIcon)
        binding.bottomNavigation.menu.findItem(R.id.navigation_profile).setIcon(profileIcon)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
                updateIcons(R.id.navigation_analysis)
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
