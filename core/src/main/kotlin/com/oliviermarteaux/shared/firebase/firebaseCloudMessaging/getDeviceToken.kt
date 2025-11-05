package com.oliviermarteaux.shared.firebase.firebaseCloudMessaging

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Retrieves the current Firebase Cloud Messaging (FCM) registration token for the device.
 *
 * The token uniquely identifies the device for push notifications and can be sent
 * to your backend or Firestore to target this device with messages.
 *
 * ### Behavior:
 * - Uses [FirebaseMessaging.getInstance().token] to asynchronously fetch the token.
 * - Logs a warning if the token fetch fails.
 * - Logs the token value on successful retrieval.
 * - Includes a placeholder `TODO` to send the token to your backend or Firestore.
 *
 * ### Example Usage:
 * ```kotlin
 * getDeviceToken()
 * ```
 *
 * ### Notes:
 * - The token may change periodically, so it is recommended to fetch it again when needed.
 * - Ensure Firebase is properly initialized in your app before calling this function.
 */
fun getDeviceToken(){
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("OM_TAG", "FCM: Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("OM_TAG", "FCM: token: $token")
            // TODO: send token to Firestore or backend
        }
}