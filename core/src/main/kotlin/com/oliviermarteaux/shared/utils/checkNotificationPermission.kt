package com.oliviermarteaux.shared.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Checks whether the app has permission to post notifications on the device.
 *
 * This function handles differences in Android versions:
 * - On Android 13 (API level 33, TIRAMISU) and above, it checks if the
 *   [Manifest.permission.POST_NOTIFICATIONS] permission is granted.
 * - On earlier versions, notification permission is considered granted by default.
 *
 * ### Parameters:
 * @param context The [Context] used to check the permission.
 *
 * ### Returns:
 * `true` if the app has permission to post notifications, `false` otherwise.
 *
 * ### Example Usage:
 * ```kotlin
 * if (checkNotificationPermission(context)) {
 *     // Safe to send notifications
 * } else {
 *     // Request notification permission
 * }
 * ```
 */
fun checkNotificationPermission(context: Context): Boolean {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        // Before Android 13, permission is automatically granted
        true
    }
}