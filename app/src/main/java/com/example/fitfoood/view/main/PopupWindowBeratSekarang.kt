package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupWindowBeratSekarangBinding.inflate(inflater, container, false)
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
            this.height = bmi.height.toString()
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
        val height = this.height.toIntOrNull() ?: 0
        return BMI(height , weight)



    }

    private fun updateWeightBy(amount: Int) {
        val currentWeight = binding.textBbSasaran.text.toString().replace("Kg", "").trim().toIntOrNull() ?: 0
        val newWeight = (currentWeight + amount).coerceAtLeast(0)
        binding.textBbSasaran.text = "$newWeight Kg"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
