package com.example.fitfoood.view.setting

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.fitfoood.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED) {
            // Handle setting alarms again if needed
            // For simplicity, re-set an example alarm
            val sharedPreferences = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
            val notifId = sharedPreferences.getInt("notif_id", -1)
            val timeInMillis = sharedPreferences.getLong("time_in_millis", -1)
            val title = sharedPreferences.getString("title", null)
            val message = sharedPreferences.getString("message", null)

            if (notifId != -1 && timeInMillis != -1L && title != null && message != null) {
                setOneTimeAlarm(context, TYPE_ONE_TIME, timeInMillis, notifId, title, message)
            }
        } else {
            val title = intent.getStringExtra(EXTRA_TITLE)
            val message = intent.getStringExtra(EXTRA_MESSAGE)
            val notifId = intent.getIntExtra(EXTRA_NOTIF_ID, 0)
            if (title != null && message != null) {
                showAlarmNotification(context, title, message, notifId)
            }
        }
    }

    fun setOneTimeAlarm(context: Context, type: String, timeInMillis: Long, notifId: Int, title: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)
        intent.putExtra(EXTRA_NOTIF_ID, notifId)
        intent.putExtra(EXTRA_TITLE, title)
        intent.putExtra(EXTRA_MESSAGE, message)
        val pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        // Use setExactAndAllowWhileIdle for exact timing
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        }

        // Save alarm details in SharedPreferences
        val sharedPreferences = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("notif_id", notifId)
            putLong("time_in_millis", timeInMillis)
            putString("title", title)
            putString("message", message)
            apply()
        }

        Toast.makeText(context, "$title alarm set up", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, notifId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.fit_food_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val EXTRA_TYPE = "type"
        const val EXTRA_NOTIF_ID = "notif_id"
        const val EXTRA_TITLE = "title"
        const val EXTRA_MESSAGE = "message"
    }
}
