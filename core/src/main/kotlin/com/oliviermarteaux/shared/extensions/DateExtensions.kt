package com.oliviermarteaux.shared.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Converts this [Date] instance into a human-readable string formatted as "dd/MM/yyyy".
 *
 * The resulting string uses the default [Locale] of the device.
 *
 * ### Example Usage:
 * ```kotlin
 * val date = Date()
 * val formatted = date.toHumanDate() // e.g., "05/11/2025"
 * ```
 *
 * @receiver The [Date] instance to format.
 * @return A [String] representing the date in "dd/MM/yyyy" format.
 */
fun Date.toHumanDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}