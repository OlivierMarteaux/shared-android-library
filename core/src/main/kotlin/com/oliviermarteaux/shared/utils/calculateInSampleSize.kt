package com.oliviermarteaux.shared.utils

import android.content.Context
import android.util.Log

fun calculateInSampleSize(context: Context): Int {
    val memoryClass = getAvailableMemoryClass(context)
    Log.i("OM_TAG", "calculateInSampleSize: memoryClass = $memoryClass")

    return when {
        memoryClass >= 512 -> 1   // High-end devices
        memoryClass >= 256 -> 1   // Xiaomi Mi 11
        memoryClass >= 128 -> 4
        else -> 8                // Low-end devices
    }
}