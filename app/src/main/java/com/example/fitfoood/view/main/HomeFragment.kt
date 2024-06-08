
package com.example.fitfoood

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.databinding.FragmentHomeBinding
import com.example.fitfoood.view.artikel.ArtikelActivity
import com.example.fitfoood.view.foodrecomendation.FoodActivity
import com.example.fitfoood.view.notification.NotificationActivity
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
            Artikel("Artikel 1", R.drawable.dummy_img_artikel, 1),
            Artikel("Artikel 2", R.drawable.dummy_img_artikel, 2),
            // Add more articles with appropriate categories
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
            btnNotification.setOnClickListener {
                startActivity(Intent(requireContext(), NotificationActivity::class.java))
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
