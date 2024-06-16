package com.example.fitfoood

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.databinding.FragmentHomeBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.artikel.ArtikelActivity
import com.example.fitfoood.view.foodrecomendation.FoodActivity
import com.example.fitfoood.view.main.HomeViewModel
import com.example.fitfoood.view.notification.NotificationActivity
import com.example.fitfoood.view.workoutrecomendation.WorkOutActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = "your_token_here" // Retrieve or set your token here

        homeViewModel = ViewModelFactory.getInstance(requireContext()).create(HomeViewModel::class.java)

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
        showRecyclerList()
    }

    private fun showRecyclerList() {
        homeViewModel.getAllArticles(token).observe(viewLifecycleOwner) { artikel ->
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
