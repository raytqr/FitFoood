package com.example.fitfoood.view.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fitfoood.ArtikelAdapter
import com.example.fitfoood.R
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.ArtikelResponseItem
import com.example.fitfoood.databinding.ActivityArtikelBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtikelBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var token: String
    private var articles: List<ArtikelResponseItem> = listOf()
    var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

         // Gantikan dengan token Anda
        homeViewModel = ViewModelFactory.getInstance(this).create(HomeViewModel::class.java)
        homeViewModel.getSession().observe(this) { user ->
            token = user.token
        }
//        setupViewPager()
        showRecyclerList()
        fetchArticles()
    }

    private fun setupViewPager() {

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true){tab, position ->
            tab.setText(tabTitles[position])
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, listOf())
        binding.viewPager.adapter = sectionsPagerAdapter
        tabLayoutMediator!!.attach()
    }

    private fun fetchArticles() {
        homeViewModel.getAllArticles(token).observe(this, Observer { artikel ->
            when (artikel) {
                is ApiResponse.Success -> {
                    articles = artikel.data ?: listOf()
                    if(tabLayoutMediator == null){
                        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true){tab, position ->
                            tab.setText(tabTitles[position])
                        }
                        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, articles)
                        binding.viewPager.adapter = sectionsPagerAdapter
                        tabLayoutMediator!!.attach()
                    }else{

                        (binding.viewPager.adapter as SectionsPagerAdapter).updateArticles(articles)
                    }
                }
                is ApiResponse.Error -> {
                    // Handle error
                }
                is ApiResponse.Loading -> {
                    // Show loading
                }
            }
        })
    }

    private fun showRecyclerList() {
        homeViewModel.getAllArticles(token).observe(this, Observer { artikel ->
            when (artikel) {
                is ApiResponse.Success -> {
                    val list = artikel.data ?: listOf()
                    val adapter = ArtikelAdapter(list)
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
        })
    }

    private val tabTitles = arrayOf("All", "Hidup Sehat", "Olahraga")

    inner class SectionsPagerAdapter(fm: FragmentManager, private var articles: List<ArtikelResponseItem>) : FragmentStateAdapter(fm, lifecycle) {

        override fun createFragment(position: Int): Fragment {
            val filteredArticles = when (position) {
                0 -> articles // Show all articles for the "All" tab
                1 -> articles.filter { it.category.equals("hidup-sehat", ignoreCase = true) }
                2 -> articles.filter { it.category.equals("olahraga", ignoreCase = true) }
                else -> articles
            }
            return ContentFragment.newInstance(filteredArticles)
        }

        override fun getItemCount(): Int {
            return tabTitles.size
        }

        fun updateArticles(newArticles: List<ArtikelResponseItem>) {
            articles = newArticles
            notifyDataSetChanged()
        }
    }
}
