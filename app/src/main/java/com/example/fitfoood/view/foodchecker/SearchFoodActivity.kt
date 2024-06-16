package com.example.fitfoood.view.foodchecker
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.data.response.FoodResponse
import com.example.fitfoood.data.retrofit.ApiConfig
import com.example.fitfoood.databinding.ActivitySearchFoodBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class SearchFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodBinding
    private lateinit var searchAdapter: FoodSearchAdapter
    private val foodList = mutableListOf<FoodResponse>()

    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    private var searchTask: ScheduledFuture<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tbTitle = findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Cari Makanan"

        binding.toolbar.setOnClickListener {
            finish()
        }

        binding.btnFillManual.setOnClickListener {
            startActivityForResult(Intent(this, FillManualActivity::class.java), MANUAL_ENTRY_REQUEST_CODE)
        }

        setupRecyclerView()

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchFood(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Batalkan task sebelumnya jika ada
                searchTask?.cancel(false)

                // Jadwalkan task baru
                searchTask = scheduler.schedule({
                    runOnUiThread {
                        newText?.let { searchFood(it) }
                    }
                }, 300, TimeUnit.MILLISECONDS) // Debounce time 300ms

                return true
            }
        })
    }

    private fun setupRecyclerView() {
        searchAdapter = FoodSearchAdapter(foodList) { food ->
            val intent = Intent()
            intent.putExtra("foodName", food.name)
            intent.putExtra("calories", food.calories)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchFoodActivity)
            adapter = searchAdapter
        }
    }

    private fun searchFood(query: String) {
        val client = ApiConfig.getFoodApiService().searchFood(query)
        client.enqueue(object : Callback<List<FoodResponse>> {
            override fun onResponse(call: Call<List<FoodResponse>>, response: Response<List<FoodResponse>>) {
                if (response.isSuccessful) {
                    foodList.clear()
                    response.body()?.let {
                        foodList.addAll(it)
                    }
                    searchAdapter.notifyDataSetChanged()
                    if (foodList.isEmpty()) {
                        binding.emptyFoodContainer.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.emptyFoodContainer.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    }

                    // Show or hide empty state based on the search results
                    if (foodList.isEmpty()) {
                        binding.emptyFood.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.emptyFood.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                    }
                } else {
                    Log.e(TAG, "Search food failed: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FoodResponse>>, t: Throwable) {
                Log.e(TAG, "Search food failed: ${t.message}")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MANUAL_ENTRY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    companion object {
        private const val MANUAL_ENTRY_REQUEST_CODE = 1
        private const val TAG = "SearchFoodActivity"
    }
}
