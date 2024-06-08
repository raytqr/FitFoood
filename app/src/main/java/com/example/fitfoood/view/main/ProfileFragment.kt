package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bgBeratSasaran.setOnClickListener {
                val popupWindowBeratSasaran = PopupWindowBeratSasaran()
                popupWindowBeratSasaran.show(parentFragmentManager, "PopupWindowBeratSasaran")
            }
            bgBeratAwal.setOnClickListener {
                val popupWindowBeratAwal = PopupWindowBeratAwal()
                popupWindowBeratAwal.show(parentFragmentManager, "PopupWindowBeratAwal")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
