package com.example.fitfoood.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentSettingBinding

class SettingFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Setting"

        binding.cardViewAccount.setOnClickListener{
            val accountFragment = AccountFragment()
            parentFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, accountFragment, AccountFragment::class.java.simpleName)
            }
        }
        binding.cardViewReminder.setOnClickListener{
            val reminderFragment = ReminderFragment()
            parentFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, reminderFragment, ReminderFragment::class.java.simpleName)
            }
        }
        binding.cardViewLanguage.setOnClickListener {
            val languageFragment = LanguageFragment()
            parentFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, languageFragment, LanguageFragment::class.java.simpleName)
            }
        }
        binding.cardViewLogout.setOnClickListener {
            val showPopUp = LogoutFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
        }
    }

    override fun onClick(v: View?) {
        val fragmentManager = parentFragmentManager
        when (v?.id) {
            R.id.cardViewAccount -> {
                val accountFragment = AccountFragment()
                fragmentManager.commit {
                    addToBackStack(null)
                    replace(R.id.frame_container, accountFragment, AccountFragment::class.java.simpleName)
                }
            }
            R.id.cardViewReminder -> {
                val reminderFragment = ReminderFragment()
                fragmentManager.commit {
                    addToBackStack(null)
                    replace(R.id.frame_container, reminderFragment, ReminderFragment::class.java.simpleName)
                }
            }
            R.id.cardViewLanguage -> {
                val languageFragment = LanguageFragment()
                fragmentManager.commit {
                    addToBackStack(null)
                    replace(R.id.frame_container, languageFragment, LanguageFragment::class.java.simpleName)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
