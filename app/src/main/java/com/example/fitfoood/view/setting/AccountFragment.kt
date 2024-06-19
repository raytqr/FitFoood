package com.example.fitfoood.view.main

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentAccountBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.setting.EditAccountFragment
import java.text.SimpleDateFormat
import java.util.*

class AccountFragment : Fragment(), View.OnClickListener {

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

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.account)

        binding.toolbar.setOnClickListener {
            requireActivity().onBackPressed()
        }

        accountViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(AccountViewModel::class.java)

        accountViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            val dateOfBirth = user.dateOfBirth
            binding.dumbAge.text = calculateAge(dateOfBirth)
        }
        observeViewModel()

        val editAccount: Button = view.findViewById(R.id.btnEdit)
        editAccount.setOnClickListener(this)

        loadProfilePicture()
    }

    private fun loadProfilePicture() {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val encodedImage = sharedPreferences.getString("profile_picture", null)
        if (encodedImage != null) {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.imgPhoto.setImageBitmap(bitmap)
        }
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

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnEdit) {
            val editAccountFragment = EditAccountFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.fragment_container,
                    editAccountFragment,
                    EditAccountFragment::class.java.simpleName
                )
            }
        }
    }
}
