package com.example.fitfoood.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.fitfoood.databinding.DboxLogoutBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.login.LoginActivity

class LogoutFragment : DialogFragment() {

    private var _binding: DboxLogoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DboxLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
    super.onStart()
    dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingViewModel = ViewModelFactory.getInstance(requireContext()).create(SettingViewModel::class.java)

        binding.btnYes.setOnClickListener {
            settingViewModel.logout()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        binding.btnNo.setOnClickListener {
            dismiss() // Dismiss the dialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
