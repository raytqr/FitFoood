package com.example.fitfoood

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.databinding.FragmentHomeBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.artikel.ArtikelActivity
import com.example.fitfoood.view.foodrecomendation.FoodActivity
import com.example.fitfoood.view.main.HomeViewModel
import com.example.fitfoood.view.workoutrecomendation.WorkOutActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var token: String
    private lateinit var label:String
    private lateinit var idhealth: String

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

        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            idhealth = user.userId
            val username = user.username.split(" ").firstOrNull() ?: user.username // Ambil kata pertama atau username jika tidak ada spasi
            binding.tvItem.text = "Hai, $username"

//            fetchBMIData()
            showRecyclerList()
            loadProfilePicture()
        }
        homeViewModel.getSessionBMI().observe(viewLifecycleOwner){result->
            label = result.label
            label = label.toUpperCase()
            binding.textViewBMICard.text = label

        }





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


        }
    }

//    private fun fetchBMIData() {
//        homeViewModel.getBMI(token, idhealth ).observe(viewLifecycleOwner){result->
//            when(result){
//                is ApiResponse.Success -> {
//                    val bmiData = result.data?.data?.firstOrNull()
//                    if (bmiData != null) {
//                        binding.textViewBMICard.text = bmiData.label
//                    }
//                }
//                is ApiResponse.Error -> {
//                    // Handle error
//                }
//                is ApiResponse.Loading -> {
//                    // Show loading
//                }
//            }
//        }
//    }

    private fun loadProfilePicture() {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val encodedImage = sharedPreferences.getString("profile_picture", null)
        if (encodedImage != null) {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.imgAvatar.setImageBitmap(bitmap)
        }
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
