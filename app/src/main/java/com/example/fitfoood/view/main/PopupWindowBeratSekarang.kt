package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.fitfoood.R
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.BMI
import com.example.fitfoood.databinding.PopupWindowBeratSekarangBinding
import com.example.fitfoood.view.ViewModelFactory

class PopupWindowBeratSekarang : DialogFragment() {

    private var _binding: PopupWindowBeratSekarangBinding? = null
    private val binding get() = _binding!!
    private lateinit var token: String
    private lateinit var userId: String
    private lateinit var height: String
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupWindowBeratSekarangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentManager = parentFragmentManager
        homeViewModel = ViewModelFactory.getInstance(requireContext()).create(HomeViewModel::class.java)
        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            userId = user.userId
        }

        homeViewModel.getSessionBMI().observe(viewLifecycleOwner) { bmi ->
            this.height = bmi.height.toString()
            val lastWeight = bmi.weight?.toIntOrNull() ?: 45
            binding.textBbSasaran.text = "$lastWeight Kg"
        }


        binding.saveButton.setOnClickListener {
            val berat = binding.textBbSasaran.text.toString().replace("Kg", "").trim()
            homeViewModel.postBMI(token, userId, BMI()).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ApiResponse.Error -> {
                        Toast.makeText(requireContext(), "Terjadi kesalahan : ${result.message}", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponse.Loading -> {
                    }
                    is ApiResponse.Success -> {
                        Toast.makeText(requireContext(), "Berhasil menyimpan berat", Toast.LENGTH_SHORT).show()
                        dismiss()

                        val profileFragment = ProfileFragment()
                        fragmentManager.beginTransaction().apply {
                            replace(R.id.fragment_container, profileFragment)
                            commit()
                        }
                    }
                }
            }
        }

        binding.plusBtn.setOnClickListener {
            updateWeightBy(1)
        }

        binding.minBtn.setOnClickListener {
            updateWeightBy(-1)
        }
    }

    private fun BMI(): BMI {
        val weight = binding.textBbSasaran.text.toString().replace("Kg", "").trim().toIntOrNull() ?: 0
        val height = if (this.height.isNotEmpty()) this.height.toIntOrNull() ?: 50 else 50
        return BMI(height , weight)
    }

    private fun updateWeightBy(amount: Int) {
        val currentWeight = binding.textBbSasaran.text.toString().replace("Kg", "").trim().toIntOrNull() ?: 0
        val newWeight = (currentWeight + amount).coerceAtLeast(1)
        binding.textBbSasaran.text = "$newWeight Kg"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
