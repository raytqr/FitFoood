package com.example.fitfoood.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import android.content.Intent
import android.provider.Settings
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.commit
import com.example.fitfoood.databinding.FragmentProfileBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.setting.LanguageFragment
import com.example.fitfoood.view.setting.LogoutFragment
import com.example.fitfoood.view.setting.ReminderFragment

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.apply {
//            bgBeratSasaran.setOnClickListener {
//                val popupWindowBeratSasaran = PopupWindowBeratSasaran()
//                popupWindowBeratSasaran.show(parentFragmentManager, "PopupWindowBeratSasaran")
//            }
//            bgBeratAwal.setOnClickListener {
//                val popupWindowBeratAwal = PopupWindowBeratAwal()
//                popupWindowBeratAwal.show(parentFragmentManager, "PopupWindowBeratAwal")
//            }
//            btnSetting.setOnClickListener {
//                startActivity(Intent(requireContext(), SettingActivity::class.java))
//            }
//        }
        homeViewModel = ViewModelFactory.getInstance(requireContext()).create(HomeViewModel::class.java)

        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            val username = user.username
            binding.tvItem.text = "$username"
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
