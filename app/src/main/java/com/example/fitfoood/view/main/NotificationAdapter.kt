package com.example.fitfoood.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfoood.databinding.NotificationRowBinding

class NotificationAdapter(private val notificationList: List<Notification>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.NotificationViewHolder {
        val binding = NotificationRowBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.NotificationViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int {
        return  notificationList.size
    }

    inner class NotificationViewHolder(private val binding: NotificationRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listNotif: Notification) {
            binding.notifHeading.text = listNotif.title
            binding.icNotif.setImageResource(listNotif.image)
            binding.descNotif.text = listNotif.desc
        }
    }
}

data class Notification (val image: Int, val title: String, val desc: String)