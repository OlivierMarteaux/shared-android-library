package com.oliviermarteaux.shared.utils

import android.content.Context
import android.util.Log

fun calculateInSampleSize(context: Context): Int {
    val memoryClass = getAvailableMemoryClass(context)
    Log.i("OM_TAG", "calculateInSampleSize: memoryClass = $memoryClass")

    return when {
        memoryClass >= 512 -> 1  // High-end devices - RedmiNote10: 512MB
        memoryClass >= 256 -> 1  // Xiaomi Mi 11: 256MB
        memoryClass > 192 -> 1
        memoryClass > 128 -> 2   // Honor 5C: 192MB - Low-end devices
        else -> 2                // Low-end devices
    }
}