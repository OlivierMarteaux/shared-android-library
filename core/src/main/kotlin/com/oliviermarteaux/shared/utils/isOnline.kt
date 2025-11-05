package com.oliviermarteaux.shared.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission

/**
 * Checks whether the device currently has an active internet connection.
 *
 * This function queries the system's [ConnectivityManager] and returns `true` if
 * the active network has internet capability, otherwise `false`.
 *
 * ### Parameters:
 * @param context The [Context] used to access system connectivity services.
 *
 * ### Returns:
 * `true` if the device is connected to a network with internet access, `false` otherwise.
 *
 * ### Notes:
 * - Requires the [Manifest.permission.ACCESS_NETWORK_STATE] permission.
 * - Only checks for network availability and internet capability; it does not
 *   guarantee that the internet is reachable (e.g., server access may still fail).
 *
 * ### Example Usage:
 * ```kotlin
 * if (isOnline(context)) {
 *     // Safe to perform network operations
 * } else {
 *     // Show offline message or handle accordingly
 * }
 * ```
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}