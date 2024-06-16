package com.example.fitfoood.view.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.Artikel
import com.example.fitfoood.ArtikelAdapter
import com.example.fitfoood.R
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.ArtikelResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.databinding.ActivityArtikelBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.HomeViewModel

class ArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtikelBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var token: String
    private var articles: List<ArtikelResponseItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = "your_token_here" // Gantikan dengan token Anda
        homeViewModel = ViewModelFactory.getInstance(this).create(HomeViewModel::class.java)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, articles)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        showRecyclerList()
        fetchArticles()
    }

    private fun fetchArticles() {
        homeViewModel.getAllArticles(token).observe(this) { artikel ->
            when (artikel) {
                is ApiResponse.Success -> {
                    articles = artikel.data ?: listOf()
                    (binding.viewPager.adapter as SectionsPagerAdapter).notifyDataSetChanged()
                }
                is ApiResponse.Error -> {
                    // Handle error
                }
                is ApiResponse.Loading -> {
                    // Show loading
                }
            }
        }
    }

    private fun showRecyclerList() {
        homeViewModel.getAllArticles(token).observe(this) { artikel ->
            when (artikel) {
                is ApiResponse.Success -> {
                    val list = artikel.data
                    val adapter = ArtikelAdapter(list!!)
                    with(binding.recyclerView) {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        setHasFixedSize(true)
                        this.adapter = adapter
                    }
                }
                is ApiResponse.Error -> {
                    // Handle error
                }
                is ApiResponse.Loading -> {
                    // Show loading
                }
            }
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, private val articles: List<ArtikelResponseItem>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val tabTitles = arrayOf("All", "Hidup Sehat", "Olahraga")

        override fun getItem(position: Int): Fragment {
            val filteredArticles = when (position) {
                0 -> articles
                1 -> articles.filter { it.category.equals("hidup sehat", ignoreCase = true) }
                2 -> articles.filter { it.category.equals("olahraga", ignoreCase = true) }
                else -> articles
            }
            return ContentFragment.newInstance(filteredArticles)
        }

        override fun getCount(): Int {
            return tabTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }

}
