package com.example.fitfoood.view.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentNotificationBinding
import com.example.fitfoood.view.main.Notification
import com.example.fitfoood.view.main.NotificationAdapter

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationList = listOf(
            Notification(R.drawable.ic_eat, "Waktunya Makan!", "Lorem ipsum dolor sit amet consectetur."),
            Notification(R.drawable.ic_alarm, "Lorem Ipsum!", "Lorem ipsum dolor sit amet consectetur."),
            Notification(R.drawable.ic_weight_scales, "Timbang Berat Badanmu!", "Lorem ipsum dolor sit amet consectetur.")
            // Add more notifications
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = NotificationAdapter(notificationList) { notification ->
            val fragment = NotificationDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("NOTIF_TITLE", notification.title)
                    putInt("NOTIF_IMAGE", notification.image)
                    putString("NOTIF_DESC", notification.desc)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Notifikasi"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
