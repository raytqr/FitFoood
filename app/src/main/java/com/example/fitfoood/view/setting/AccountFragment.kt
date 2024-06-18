package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.databinding.FragmentAccountBinding
import com.example.fitfoood.view.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(AccountViewModel::class.java)

        accountViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            val dateOfBirth = user.dateOfBirth
            binding.dumbAge.text = calculateAge(dateOfBirth)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        accountViewModel.getSession().observe(viewLifecycleOwner) { userModel ->
            binding.dumbName.text = userModel.username
            binding.dumbEmail.text = userModel.email
        }
    }

    private fun calculateAge(dateOfBirth: String): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val dob = sdf.parse(dateOfBirth)
        val today = Calendar.getInstance()

        val dobCalendar = Calendar.getInstance().apply { time = dob }
        var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
