
package com.example.fitfoood

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.FragmentHomeBinding
import com.example.fitfoood.view.artikel.ArtikelActivity
import com.example.fitfoood.view.artikel.DetailArtikelActivity
import com.example.fitfoood.view.foodrecomendation.FoodActivity
import com.example.fitfoood.view.workoutrecomendation.WorkOutActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artikelList = listOf(
            Artikel("Artikel 1", R.drawable.dummy_img_artikel),
            Artikel("Artikel 2", R.drawable.dummy_img_artikel),
            // Tambahkan artikel lainnya
        )

        binding.apply {
            btnFoodRecomendation.setOnClickListener {
                startActivity(Intent(requireContext(), FoodActivity::class.java))
            }
            btnWoRecomendation.setOnClickListener {
                startActivity(Intent(requireContext(), WorkOutActivity::class.java))
            }
            homeSeeAll.setOnClickListener {
                startActivity(Intent(requireContext(), ArtikelActivity::class.java))
            }
            recyclerView.setOnClickListener {
                startActivity(Intent(requireContext(), DetailArtikelActivity::class.java))
            }

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ArtikelAdapter(artikelList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
