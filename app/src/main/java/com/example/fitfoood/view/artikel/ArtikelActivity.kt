package com.example.fitfoood.view.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityMainBinding
import com.example.fitfoood.view.foodchecker.CameraActivity

class ArtikelActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)
    }

}