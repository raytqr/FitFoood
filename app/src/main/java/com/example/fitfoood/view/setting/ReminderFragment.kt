package com.example.fitfoood.view.setting

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentReminderBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class ReminderFragment : Fragment() {

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        binding.setBreakfastTime.setOnClickListener {
            openTimePicker()
        }
        binding.setLunchTime.setOnClickListener {
            openTimePicker()
        }
        binding.setDinnerTime.setOnClickListener {
            openTimePicker()
        }

        return binding.root
    }

    private fun openTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Alarm")
            .build()
        picker.show(childFragmentManager, "TAG")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}