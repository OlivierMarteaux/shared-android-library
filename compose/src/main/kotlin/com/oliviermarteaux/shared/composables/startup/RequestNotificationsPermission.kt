package com.oliviermarteaux.shared.composables.startup

import android.Manifest
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

/**
 * Requests the **POST_NOTIFICATIONS** permission on Android 13 (Tiramisu, API level 33) and above.
 *
 * This composable handles the logic for requesting notification permission at runtime.
 * It uses a [LaunchedEffect] to automatically trigger the permission request process
 * when the composable is first composed.
 *
 * ### Behavior:
 * - For devices running **below Android 13**, the function does nothing since notification
 *   permission is granted by default.
 * - For **Android 13 and above**, it checks whether the app should display a rationale before
 *   requesting permission using [ActivityCompat.shouldShowRequestPermissionRationale].
 * - If no rationale is required, it logs that a custom permission request may be needed.
 * - Otherwise, it launches a permission request dialog using [rememberLauncherForActivityResult].
 *
 * ### Logging:
 * Multiple debug logs (`OM_TAG`, `FCM`) are included to trace the permission request flow.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun AppContent() {
 *     RequestNotificationPermission()
 * }
 * ```
 *
 * ### Notes:
 * - This function must be called from within a **@Composable** context.
 * - The permission request launcher is remembered across recompositions.
 * - The permission result is logged but not currently handled (you can extend `onResult`
 *   to perform additional actions based on whether the permission was granted).
 *
 * @see rememberLauncherForActivityResult
 * @see ActivityResultContracts.RequestPermission
 * @see Manifest.permission.POST_NOTIFICATIONS
 * @see ActivityCompat.shouldShowRequestPermissionRationale
 */
@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current
    Log.d("OM_TAG", "RequestNotificationPermission: defining launcher")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Log.d("FCM", "Notification permission granted: $isGranted")
        }
    )

    Log.d("OM_TAG", "RequestNotificationPermission: LaunchedEffect")
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.d("OM_TAG", "RequestNotificationPermission: requesting permission because Android>TIRAMISU")
            val shouldShowRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
                    Manifest.permission.POST_NOTIFICATIONS)
            Log.d("OM_TAG", "RequestNotificationPermission: shouldShowRationale=$shouldShowRationale")
            if (!shouldShowRationale) {
                Log.d("OM_TAG", "RequestNotificationPermission: custom request needed")
            } else {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}