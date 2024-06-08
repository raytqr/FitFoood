package com.example.fitfoood.view.notification

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfoood.Artikel
import com.example.fitfoood.ArtikelAdapter
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentHomeBinding
import com.example.fitfoood.databinding.FragmentNotificationBinding
import com.example.fitfoood.view.artikel.ArtikelActivity
import com.example.fitfoood.view.artikel.DetailArtikelActivity
import com.example.fitfoood.view.foodrecomendation.FoodActivity
import com.example.fitfoood.view.main.Notification
import com.example.fitfoood.view.main.NotificationAdapter

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationList = listOf(
            Notification(R.drawable.ic_eat, "Waktunya Makan!", "Lorem ipsum dolor sit amet consectetur. "),
            Notification(R.drawable.ic_alarm, "Lorem Ipsum!", "Lorem ipsum dolor sit amet consectetur. "),
            Notification(R.drawable.ic_weight_scales, "Timbang Berat Badanmu!", "Lorem ipsum dolor sit amet consectetur. "),
            // Tambahkan artikel lainnya
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = NotificationAdapter(notificationList)

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = "Notifikasi"

    }




}