package com.oliviermarteaux.shared.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.oliviermarteaux.shared.extensions.toDate
import java.time.LocalTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun randomTime(minHour: Int = 9, maxHour: Int = 22): Date {
    val hour = (minHour..maxHour).random()
    val minute = ((0..59).random()) / 10 * 10
    return LocalTime.of(hour,minute).toDate()
}