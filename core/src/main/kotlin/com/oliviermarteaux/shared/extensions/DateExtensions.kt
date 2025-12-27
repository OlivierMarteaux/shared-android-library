package com.oliviermarteaux.shared.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
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

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDate(): LocalDate =
    this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

// Convert Date → LocalTime
@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalTime(): LocalTime =
    this.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()