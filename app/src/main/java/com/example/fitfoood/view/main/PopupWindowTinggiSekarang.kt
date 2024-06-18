package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.response.BMI
import com.example.fitfoood.databinding.PopupWindowTinggiSekarangBinding
import com.example.fitfoood.view.ViewModelFactory

class PopupWindowTinggiSekarang : DialogFragment() {

    private var _binding: PopupWindowTinggiSekarangBinding? = null
    private val binding get() = _binding!!
    private lateinit var token: String
    private lateinit var userId: String
    private lateinit var weight: String
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupWindowTinggiSekarangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelFactory.getInstance(requireContext()).create(HomeViewModel::class.java)
        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            userId = user.userId
        }

        homeViewModel.getSessionBMI().observe(viewLifecycleOwner) { bmi ->
            this.weight = bmi.weight.toString()
        }

        binding.saveButton.setOnClickListener {
            val height = binding.textTbSasaran.text.toString().replace("cm", "").trim()
            homeViewModel.postBMI(token, userId, BMI()).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ApiResponse.Error -> {
                        Toast.makeText(requireContext(), "Terjadi kesalahan : ${result.message}", Toast.LENGTH_SHORT).show()
                    }
                    ApiResponse.Loading -> {
                    }
                    is ApiResponse.Success -> {
                        Toast.makeText(requireContext(), "Berhasil menyimpan tinggi", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
        }

        binding.plusBtn.setOnClickListener {
            updateHeightBy(1)
        }

        binding.minBtn.setOnClickListener {
            updateHeightBy(-1)
        }
    }

    private fun BMI(): BMI {
        val height = binding.textTbSasaran.text.toString().replace("cm", "").trim().toIntOrNull() ?: 0
        val weight = this.weight.toIntOrNull() ?: 0
        return BMI(height, weight)
    }

    private fun updateHeightBy(amount: Int) {
        val currentHeight = binding.textTbSasaran.text.toString().replace("cm", "").trim().toIntOrNull() ?: 0
        val newHeight = (currentHeight + amount).coerceAtLeast(0)
        binding.textTbSasaran.text = "$newHeight cm"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
