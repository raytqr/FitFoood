package com.example.fitfoood.view.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentNotificationDetailBinding

class NotificationDetailFragment : Fragment() {

    private var _binding: FragmentNotificationDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Detail Notifikasi"

        arguments?.let {
            val title = it.getString("NOTIF_TITLE")
            val imageResId = it.getInt("NOTIF_IMAGE")
            val desc = it.getString("NOTIF_DESC")

            binding.notifTitle.text = title
            binding.notifImage.setImageResource(imageResId)
            binding.notifDesc.text = desc
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
