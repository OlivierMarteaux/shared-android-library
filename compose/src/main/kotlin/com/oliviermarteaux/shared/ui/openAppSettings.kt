package com.oliviermarteaux.shared.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log

/**
 * Opens the application's system settings page, allowing the user to modify
 * permissions, notifications, and other app-specific settings.
 *
 * ### Parameters:
 * @param context The [Context] used to start the settings activity. Usually an [Activity] or [Application] context.
 *
 * ### Behavior:
 * - Constructs an [Intent] targeting the app's details page in system settings.
 * - Logs the URI of the settings page for debugging purposes.
 * - Starts the activity to open the settings page.
 *
 * ### Example Usage:
 * ```kotlin
 * openAppSettings(context)
 * ```
 *
 * ### Notes:
 * - Ensure that the [context] used is valid and not null.
 * - This function does not return any result; the user navigates back manually.
 */
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        Log.d("OM_TAG", "shared library: openAppSettings: $data")
    }
    context.startActivity(intent)
}