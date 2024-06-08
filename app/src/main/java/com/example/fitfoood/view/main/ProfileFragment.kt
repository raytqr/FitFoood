package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import android.content.Intent
import com.example.fitfoood.databinding.FragmentProfileBinding
import com.example.fitfoood.view.setting.SettingActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return  binding.root
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
            btnSetting.setOnClickListener {
                startActivity(Intent(requireContext(), SettingActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
