package com.example.fitfoood.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.Settings
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.example.fitfoood.data.ApiResponse
import com.example.fitfoood.data.pref.BMIModel
import com.example.fitfoood.databinding.FragmentProfileBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.setting.LogoutFragment
import com.example.fitfoood.view.setting.ReminderFragment

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var token: String
    private lateinit var idhealth: String

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
            bgBeratSekarang.setOnClickListener {
                val popupWindowBeratSekarang = PopupWindowBeratSekarang()
                popupWindowBeratSekarang.show(parentFragmentManager, "PopupWindowBeratSasaran")
            }
            bgTinggiSekarang.setOnClickListener {
                val popupWindowTinggiSekarang = PopupWindowTinggiSekarang()
                popupWindowTinggiSekarang.show(parentFragmentManager, "PopupWindowBeratAwal")
            }

            loadProfilePicture()

        }
        homeViewModel = ViewModelFactory.getInstance(requireContext()).create(HomeViewModel::class.java)

        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            val username = user.username
            idhealth = user.userId
            binding.tvItem.text = "$username"
            fetchBMIData()
        }




        val btnAccount: CardView = view.findViewById(R.id.cardViewAccount)
        btnAccount.setOnClickListener(this)

        val btnReminder: CardView = view.findViewById(R.id.cardViewReminder)
        btnReminder.setOnClickListener(this)

        val btnLanguage: CardView = view.findViewById(R.id.cardViewLanguage)
        btnLanguage.setOnClickListener(this)

        val btnLogout: CardView = view.findViewById(R.id.cardViewLogout)
        btnLogout.setOnClickListener {
            val showPopUp = LogoutFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }

        setupAction()
    }

    private fun loadProfilePicture() {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val encodedImage = sharedPreferences.getString("profile_picture", null)
        if (encodedImage != null) {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.imgAvatar.setImageBitmap(bitmap)
        }
    }

    private fun fetchBMIData() {
        homeViewModel.getBMI(token, idhealth).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResponse.Error -> {
//                    binding.tvItem.text = "Error: ${result.errorMessage}"
                }
                is ApiResponse.Loading -> {
                    // Handle loading state if needed
                }
                is ApiResponse.Success -> {
                    val bmiData = result.data?.data?.firstOrNull()
                    if (bmiData != null) {
                        binding.bbSekarangText.text = bmiData.weight.toString()
                        binding.tbSekarangText.text = bmiData.height.toString()
                        val formattedBmiUser = String.format("%.2f", bmiData.bmiUser)
                        binding.bmiNumber.text = "BMI = $formattedBmiUser"
                        binding.descBMI.text = bmiData.label
                    }
                    homeViewModel.saveSessionBMI(
                        BMIModel(
                            bmiData?.weight.toString(),
                            bmiData?.height.toString(),
                            bmiData?.bmiUser.toString(),
                            bmiData?.label.toString()
                        )
                    )
                }
            }
        })
    }

    private fun setupAction() {
        binding.cardViewLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.cardViewAccount) {
            val accountFragment = AccountFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.fragment_container,
                    accountFragment,
                    AccountFragment::class.java.simpleName
                )
            }
        } else if (v?.id == R.id.cardViewReminder) {
            val reminderFragment = ReminderFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.fragment_container,
                    reminderFragment,
                    ReminderFragment::class.java.simpleName
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
