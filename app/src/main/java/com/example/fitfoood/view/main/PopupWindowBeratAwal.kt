package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fitfoood.R
import com.example.fitfoood.databinding.PopupWindowBeratAwalBinding
import com.example.fitfoood.databinding.PopupWindowBeratSasaranBinding

class PopupWindowBeratAwal : DialogFragment() {

    private var _binding: PopupWindowBeratAwalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopupWindowBeratAwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}