package com.oliviermarteaux.shared.utils

import android.util.Log

/**
 * Logs a debug message using the Android [Log] system with a fixed tag "OM_TAG".
 *
 * This function simplifies logging by providing a consistent tag and reduces boilerplate
 * when debugging.
 *
 * ### Parameters:
 * @param message The message to be logged.
 *
 * ### Example Usage:
 * ```kotlin
 * debugLog("This is a debug message")
 * ```
 *
 * ### Notes:
 * - Only logs messages at the DEBUG level.
 * - Should not be used for production logging of sensitive information.
 */
inline fun debugLog(message: String) {
    Log.d("OM_TAG", message)
}

