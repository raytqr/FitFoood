package com.example.fitfoood.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.NotificationRowBinding

class NotificationAdapter(
    private val notificationList: List<Notification>,
    private val onNotificationClick: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = NotificationRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    inner class NotificationViewHolder(private val binding: NotificationRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.notifHeading.text = notification.title
            binding.icNotif.setImageResource(notification.image)
            binding.descNotif.text = notification.desc

            binding.root.setOnClickListener {
                onNotificationClick(notification)
            }
        }
    }
}

data class Notification(val image: Int, val title: String, val desc: String)
