package com.oliviermarteaux.shared.firebase.messaging

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Subscribes the device to a Firebase Cloud Messaging (FCM) topic, allowing it to receive
 * push notifications sent to that topic.
 *
 * ### Parameters:
 * @param notifTopic The name of the FCM topic to subscribe to. Defaults to `"allUsers"`.
 *
 * ### Behavior:
 * - Uses [FirebaseMessaging.subscribeToTopic] to asynchronously subscribe the device.
 * - Logs a debug message if the subscription is successful.
 * - No action is taken if the subscription fails (you may extend with error handling if needed).
 *
 * ### Example Usage:
 * ```kotlin
 * subscribeToFcmNotificationTopic("news")
 * subscribeToFcmNotificationTopic() // subscribes to default "allUsers" topic
 * ```
 *
 * ### Notes:
 * - Ensure Firebase is properly initialized before calling this function.
 * - Topic subscriptions allow sending notifications to multiple devices grouped under the same topic.
 */
fun subscribeToFcmNotificationTopic(
    notifTopic: String = "allUsers"
) {
    FirebaseMessaging.getInstance().subscribeToTopic(notifTopic)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Subscribed to $notifTopic topic")
            }
        }
}