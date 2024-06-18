package com.example.fitfoood.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentAccountBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.artikel.ArtikelActivity
import com.example.fitfoood.view.foodchecker.SearchFoodActivity
import com.example.fitfoood.view.setting.EditAccountFragment


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.account)

        binding.toolbar.setOnClickListener {
            requireActivity().onBackPressed()
        }

        accountViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(AccountViewModel::class.java)

        observeViewModel()
    }

    private fun observeViewModel() {
        accountViewModel.getSession().observe(viewLifecycleOwner) { userModel ->

            binding.dumbName.text = userModel.username
            binding.dumbEmail.text = userModel.email
//            binding.dumbAge.text = userModel.dateOfBirth// Calculate age

        }
    }

//    private fun calculateAge(dateOfBirth: String): String {
//        // Implement your age calculation logic here
//        // Example: You can use SimpleDateFormat to parse dateOfBirth and calculate age
//        // For simplicity, I'll assume a placeholder method for demonstration
//        return "Age Calculation Placeholder"
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
