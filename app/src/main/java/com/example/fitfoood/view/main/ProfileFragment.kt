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
import androidx.fragment.app.commit
import com.example.fitfoood.databinding.FragmentProfileBinding
import com.example.fitfoood.view.setting.AccountFragment
import com.example.fitfoood.view.setting.LanguageFragment
import com.example.fitfoood.view.setting.LogoutFragment
import com.example.fitfoood.view.setting.ReminderFragment
import com.example.fitfoood.view.setting.SettingActivity

class ProfileFragment : Fragment(), View.OnClickListener {

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

        val btnAccount: ImageView = view.findViewById(R.id.arrowAccount)
        btnAccount.setOnClickListener(this)

        val btnReminder: ImageView = view.findViewById(R.id.arrowReminder)
        btnReminder.setOnClickListener(this)

        val btnLanguage: ImageView = view.findViewById(R.id.arrowLanguage)
        btnLanguage.setOnClickListener(this)

        val btnLogout: ImageView = view.findViewById(R.id.arrowLogout)
        btnLogout.setOnClickListener {
            val showPopUp = LogoutFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }

        setupAction()
    }

    private fun setupAction() {
        binding.arrowLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.arrowAccount) {
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
        } else if (v?.id == R.id.arrowReminder) {
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
        } else if (v?.id == R.id.arrowLanguage) {
            val languageFragment = LanguageFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.fragment_container,
                    languageFragment,
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
