package com.example.dermacare.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.dermacare.R
import com.example.dermacare.ui.theme.LoginActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(rmessage: RemoteMessage) {
        super.onMessageReceived(rmessage)

        val title=rmessage.notification?.title?: "Derma Care"
        val message=rmessage.notification?.body?:"You have new Notification"

        showNotification(title,message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    private fun showNotification(title: String,message:String){
        val channelId = "dermacare_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE)
        as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "DermaCare Notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Skin Care reminders and updates"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        // Show notification
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    }