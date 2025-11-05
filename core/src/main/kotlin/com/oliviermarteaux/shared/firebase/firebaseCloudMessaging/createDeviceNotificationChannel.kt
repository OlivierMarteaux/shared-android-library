package com.oliviermarteaux.shared.firebase.firebaseCloudMessaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * Creates a notification channel for the device, required for Android O (API 26) and above.
 *
 * Notification channels allow apps to group notifications and control their behavior
 * such as importance, sound, and vibration.
 *
 * ### Parameters:
 * @param notifId The unique ID for the notification channel. Defaults to `"allUsers"`.
 * @param notifName The user-visible name of the channel. Defaults to `"allUsers"`.
 * @param notifImportance The importance level of the channel. Defaults to [NotificationManager.IMPORTANCE_DEFAULT].
 * @param notifDesc The description of the channel, shown to the user. Defaults to `"allUsers"`.
 * @param notifManager The [NotificationManager] used to create the notification channel.
 *
 * ### Notes:
 * - This function does nothing on Android versions below API 26 since notification channels
 *   are not supported.
 * - Multiple calls with the same [notifId] will update the existing channel instead of creating a new one.
 *
 * ### Example Usage:
 * ```kotlin
 * val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
 * createDeviceNotificationChannel(
 *     notifId = "messages",
 *     notifName = "Messages",
 *     notifImportance = NotificationManager.IMPORTANCE_HIGH,
 *     notifDesc = "Notifications for new messages",
 *     notifManager = notifManager
 * )
 * ```
 */
fun createDeviceNotificationChannel(
    notifId: String = "allUsers",
    notifName: String = "allUsers",
    notifImportance: Int = NotificationManager.IMPORTANCE_DEFAULT,
    notifDesc: String = "allUsers",
    notifManager: NotificationManager
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val defaultChannel = NotificationChannel(
            notifId,
            notifName,
            notifImportance
        ).apply {
            description = notifDesc
        }
        notifManager.createNotificationChannel(defaultChannel)
    }
}