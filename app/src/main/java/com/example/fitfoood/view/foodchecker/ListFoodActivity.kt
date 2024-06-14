package com.example.fitfoood.view.foodchecker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityListFoodBinding
import com.example.fitfoood.view.main.ListFood
import com.example.fitfoood.view.main.ListFoodAdapter

class ListFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListFoodBinding
    private lateinit var adapter: ListFoodAdapter

    // Daftar kalori per label
    private val calorieMap = mapOf(
        "Ayam_Crispy" to 297,
        "Ayam_Kecap" to 223,
        "Ayam_Serundeng" to 260,
        "Bakso" to 76,
        "Brownies" to 379,
        "Bubur_Ayam" to 155,
        "Capcay" to 203,
        "Cumi_Bakar" to 185,
        "Cumi_Hitam" to 174,
        "Cumi_Rica" to 200,
        "Dimsum_Ikan" to 112,
        "Garang_Asem" to 150,
        "Ikan_Bakar" to 126,
        "Ikan_Goreng" to 84,
        "Kentang_Balado" to 102,
        "Kue_Bolu" to 297,
        "Nasi_Bakar" to 281,
        "Nasi_Goreng" to 276,
        "Nasi_Kuning" to 95,
        "Nasi_Merah" to 110,
        "Nasi_Putih" to 130,
        "Nasi_Rames" to 155,
        "Opor_Ayam" to 163,
        "Pancake" to 227,
        "Pecel" to 270,
        "Pepes_Ikan" to 105,
        "Perkedel_Kentang" to 143,
        "Pukis" to 259,
        "Rawon" to 120,
        "Rendang" to 193,
        "Salad_Sayur" to 17,
        "Sate_Ayam" to 225,
        "Sate_Kambing" to 216,
        "Sayur_Asem" to 29,
        "Sayur_Sop" to 27,
        "Soto_Ayam" to 130,
        "Telur_Balado" to 202,
        "Telur_Dadar" to 153,
        "Tumis_Kacang_Panjang_Tahu" to 140,
        "Tumis_Kangkung" to 98,
        "Tumis_Terong" to 65,
        "Udang_Asam_Manis" to 269,
        "Udang_Goreng_Tepung" to 287
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Daftar Makanan"

        val resultLabels = intent.getStringArrayListExtra("resultLabels") ?: listOf<String>()

        // Buat daftar makanan berdasarkan hasil klasifikasi dan data kalori
        val foodList = resultLabels.map { label ->
            ListFood(
                R.drawable.dummy_img_food, // Ganti dengan gambar yang sesuai jika ada
                label,
                "${calorieMap[label] ?: 0} kcal",
                "100 gr" // Ganti dengan berat yang sesuai jika ada
            )
        }.toMutableList()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListFoodAdapter(foodList, calculateTotalCalories()) { food ->
            adapter.removeItem(food)
            updateTotalCalories()
        }
        recyclerView.adapter = adapter

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.addFood.setOnClickListener { startAddFood() }
        binding.cekRecButton.setOnClickListener { startCekRecom() }
    }

    private fun startCekRecom() {
        updateTotalCalories() // Panggil updateTotalCalories() sebelum berpindah ke ResultRecActivity
        val intent = Intent(this, ResultRecActivity::class.java)
        intent.putExtra("totalCalories", calculateTotalCalories())
        startActivity(intent)
    }

    private fun startAddFood() {
        val intent = Intent(this, SearchFoodActivity::class.java)
        startActivity(intent)
    }

    private fun calculateTotalCalories(): Int {
        val resultLabels = intent.getStringArrayListExtra("resultLabels") ?: listOf<String>()
        return resultLabels.sumOf { calorieMap[it] ?: 0 }
    }

    private fun updateTotalCalories() {
        val totalCalories = calculateTotalCalories()
        adapter.setTotalCalories(totalCalories) // Perbarui total kalori pada adapter
    }
}
