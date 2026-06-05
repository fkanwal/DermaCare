package com.example.dermacare.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dermacare.R
import com.example.dermacare.ui.theme.LoginActivity

class SkinCareReminderWorker (
     context: Context,
    params: WorkerParameters
): Worker(context,params) {
    override fun doWork(): Result {
        val reminderType = inputData.getString("reminder_type") ?: "morning"

        val title: String
        val message: String

        if (reminderType == "morning") {
            title = " Good Morning!"
            message = "Time for your morning skincare routine"
        } else {
            title = "🌙 Good Evening!"
            message = "Don't forget your evening skincare routine ✨"
        }
        showNotification(title, message)
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "dermacare_channel"
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "DermaCare Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Skin care reminders and updates"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}