package com.example.fitfoood.view.artikel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.Artikel
import com.example.fitfoood.Artikel2Adapter
import com.example.fitfoood.ArtikelAdapter
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityArtikelBinding

class ArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter()
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        val artikelList = listOf(
            Artikel("Artikel 1", R.drawable.dummy_img_artikel, 1),
            Artikel("Artikel 2", R.drawable.dummy_img_artikel, 2),
            // Tambahkan artikel lainnya
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ArtikelAdapter(artikelList)
    }

    inner class SectionsPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {
        private val tabTitles = arrayOf("All", "Articles", "Interviews", "News")

        override fun getItem(position: Int): Fragment {
            return ContentFragment.newInstance(tabTitles[position])
        }

        override fun getCount(): Int {
            return tabTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }
}
