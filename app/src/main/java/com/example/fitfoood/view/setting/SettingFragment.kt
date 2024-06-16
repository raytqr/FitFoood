package com.example.fitfoood.view.setting

import android.os.Bundle
import android.widget.ImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.fitfoood.R

class SettingFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }
    override fun onClick(v: View?) {
        if (v?.id == R.id.arrowAccount) {
            val accountFragment = AccountFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.frame_container,
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
                    R.id.frame_container,
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
                    R.id.frame_container,
                    languageFragment,
                    ReminderFragment::class.java.simpleName
                )
            }
        }
    }
}