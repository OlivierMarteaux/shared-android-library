package com.oliviermarteaux.shared.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Observes the device's internet connectivity status and emits updates as a [Flow] of [Boolean].
 *
 * The returned flow emits `true` when an internet connection is available, and `false`
 * when it is lost. It uses a [ConnectivityManager.NetworkCallback] to monitor real-time
 * changes in network connectivity.
 *
 * ### Parameters:
 * @param context The [Context] used to access the system's [ConnectivityManager].
 *
 * ### Returns:
 * A [Flow] emitting `true` when internet is available and `false` otherwise.
 *
 * ### Notes:
 * - Requires the [Manifest.permission.ACCESS_NETWORK_STATE] permission.
 * - The initial value is emitted immediately based on the current network status.
 * - The flow automatically unregisters the network callback when collection is cancelled.
 *
 * ### Example Usage:
 * ```kotlin
 * lifecycleScope.launch {
 *     checkInternetConnection(context).collect { isConnected ->
 *         if (isConnected) {
 *             Log.d("Network", "Internet available")
 *         } else {
 *             Log.d("Network", "Internet lost")
 *         }
 *     }
 * }
 * ```
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun checkInternetConnection(context: Context): Flow<Boolean> = callbackFlow {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { trySend(true) }
        override fun onLost(network: Network) { trySend(false) }
    }

    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(request, callback)

    // Emit initial value
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    trySend(capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)

    awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
}