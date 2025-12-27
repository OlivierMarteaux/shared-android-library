package com.oliviermarteaux.shared.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.oliviermarteaux.shared.core.R

fun showLocaleNotification(
    context: Context,
    channelId: String = "DefaultChannelId",
    channelTitle: String = "Default channel title",
    channelImportance: Int = NotificationManager.IMPORTANCE_HIGH,
    channelDescription: String = "Default channel description",
    notifTitle: String = "Default notification title",
    notifBody: String = "",
    notifIcon: Int = R.drawable.placeholder,
) {

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Create channel (Android 8+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelTitle,
            channelImportance
        ).apply {
            description = channelDescription
        }
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle(notifTitle)
        .setContentText(notifBody)
        .setSmallIcon(notifIcon)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
}