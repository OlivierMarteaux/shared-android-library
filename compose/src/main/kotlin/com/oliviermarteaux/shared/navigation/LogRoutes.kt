package com.oliviermarteaux.shared.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * A composable function that logs the current navigation route to Logcat.
 *
 * This function observes the current back stack entry of a [androidx.navigation.NavController] and logs the
 * route of the currently visible screen whenever it changes.
 *
 * It is useful for debugging navigation flows in Jetpack Compose apps.
 *
 * Usage:
 * ```
 * LogRoute()
 * ```
 * Place this inside your Compose hierarchy where you want to monitor navigation changes.
 *
 * Note:
 * - The route is converted to uppercase before logging.
 * - Uses a [LaunchedEffect] to react to changes in the current back stack entry.
 * - Logs the output with tag `"OM_TAG"` at info level.
 */
@Composable
fun LogRoutes(
    navController: NavController
){

    // Observe current backstack entry
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(currentBackStackEntry) {
        currentBackStackEntry?.destination?.route?.let { route ->
            Log.i("OM_TAG", " ${route.uppercase()} SCREEN")
        }
    }
}