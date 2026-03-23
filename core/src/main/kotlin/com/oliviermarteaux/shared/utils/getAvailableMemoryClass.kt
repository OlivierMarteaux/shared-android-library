package com.oliviermarteaux.shared.utils

import android.app.ActivityManager
import android.content.Context

fun getAvailableMemoryClass(context: Context): Int {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return activityManager.memoryClass // in MB
}