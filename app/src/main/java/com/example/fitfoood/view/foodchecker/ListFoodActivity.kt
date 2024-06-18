package com.example.fitfoood.view.foodchecker

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

    private val foodList = mutableListOf<ListFood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.food_list)

        if (intent.getBooleanExtra("noPrediction", false)) {
            showNoPredictionDialog()
        } else {
            val initialLabels = intent.getStringArrayListExtra("resultLabels") ?: emptyList()
            foodList.addAll(initialLabels.mapNotNull { label ->
                calorieMap[label]?.let { ListFood(R.drawable.ic_food, label, "$it kcal", "100 gr") }
            })
        }

        setupRecyclerView()

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.addFood.setOnClickListener { startAddFood() }
        binding.cekRecButton.setOnClickListener { startCekRecom() }
    }

    private fun showNoPredictionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_no_prediction, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<Button>(R.id.btnOK).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListFoodAdapter(foodList, calculateTotalCalories()) { food ->
            adapter.removeItem(food)
            updateTotalCalories(food.title)
        }
        recyclerView.adapter = adapter
    }

    private fun startCekRecom() {
        if (foodList.isEmpty()) {
            showEmptyFoodListDialog()
        } else {
            updateTotalCalories()
            val intent = Intent(this, ResultRecActivity::class.java)
            intent.putExtra("totalCalories", calculateTotalCalories())
            startActivity(intent)
        }
    }

    private fun showEmptyFoodListDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dbox_empty_food, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<Button>(R.id.btnOK).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun startAddFood() {
        val intent = Intent(this, SearchFoodActivity::class.java)
        startActivityForResult(intent, ADD_FOOD_REQUEST_CODE)
    }

    private fun calculateTotalCalories(): Int {
        return foodList.sumBy { food ->
            val label = food.title
            if (calorieMap.containsKey(label)) {
                calorieMap[label] ?: 0
            } else {
                food.kcal.replace(" kcal", "").toIntOrNull() ?: 0
            }
        }
    }

    private fun updateTotalCalories(deletedFoodLabel: String? = null) {
        if (deletedFoodLabel != null) {
            foodList.removeAll { it.title == deletedFoodLabel }
        }
        adapter.setTotalCalories(calculateTotalCalories())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_FOOD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val foodName = data?.getStringExtra("foodName")
            val calories = data?.getIntExtra("calories", 0)

            if (!foodName.isNullOrEmpty() && calories != null && calories > 0) {
                foodList.add(ListFood(R.drawable.ic_food, foodName, "$calories kcal", "100 gr"))
                adapter.notifyDataSetChanged()
                updateTotalCalories()
            }
        }
    }

    companion object {
        private const val ADD_FOOD_REQUEST_CODE = 1
    }
}
