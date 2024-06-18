package com.example.fitfoood.view.setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.fitfoood.R
import com.example.fitfoood.databinding.ActivityMain2Binding
import com.example.fitfoood.databinding.FragmentReminderBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class ReminderFragment : Fragment() {
    private var binding: FragmentReminderBinding? = null
    private var timePicker: MaterialTimePicker? = null
    private var calendar: Calendar? = null
    private var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderBinding.inflate(inflater, container, false)
        createNotificationChannel()

        binding!!.setBreakfastTime.setOnClickListener {
            timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build()
            timePicker!!.show(childFragmentManager, "androidknowldge")
            timePicker!!.addOnPositiveButtonClickListener {
                if (timePicker!!.hour > 12) {
                    binding!!.setBreakfastTime.text =
                        String.format("%02d", timePicker!!.hour - 12) + ":" + String.format(
                            "%02d",
                            timePicker!!.minute
                        ) + " PM"
                } else {
                    binding!!.setBreakfastTime.text =
                        timePicker!!.hour.toString() + ":" + timePicker!!.minute + " AM"
                }
                calendar = Calendar.getInstance()
                calendar?.set(Calendar.HOUR_OF_DAY, timePicker!!.hour)
                calendar?.set(Calendar.MINUTE, timePicker!!.minute)
                calendar?.set(Calendar.SECOND, 0)
                calendar?.set(Calendar.MILLISECOND, 0)
            }
        }

        binding!!.switchEatReminder.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                if (calendar == null) {
                    // Handle case where time is not selected before toggling switch on
                    Toast.makeText(
                        requireContext(),
                        "Please select an alarm time first",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnCheckedChangeListener
                }
                setAlarm()
            } else {
                cancelAlarm()
            }
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.reminder)

        binding?.toolbar?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "fitfoodchannel"
            val desc = "Channel for Alarm Manager"
            val imp = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("androidknowledge", name, imp)
            channel.description = desc
            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun cancelAlarm() {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        if (alarmManager == null) {
            alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
        alarmManager?.cancel(pendingIntent ?: return)
        Toast.makeText(requireContext(), "Alarm Canceled", Toast.LENGTH_SHORT).show()
    }

    private fun setAlarm() {
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val localPendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        if (localPendingIntent != null) {
            pendingIntent = localPendingIntent
            alarmManager!!.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, calendar!!.timeInMillis, AlarmManager.INTERVAL_DAY,
                pendingIntent!!
            )
            Toast.makeText(requireContext(), "Alarm Set", Toast.LENGTH_SHORT).show()
        }
    }
}