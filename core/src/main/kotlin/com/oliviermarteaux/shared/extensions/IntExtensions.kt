package com.oliviermarteaux.shared.extensions

import android.content.Context
import androidx.annotation.DrawableRes

fun @receiver:DrawableRes Int.toUriString(context: Context): String {
    return "android.resource://${context.packageName}/$this"
}