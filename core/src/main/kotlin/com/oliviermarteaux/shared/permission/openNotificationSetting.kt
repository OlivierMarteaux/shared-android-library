package com.oliviermarteaux.shared.permission

import android.content.Context
import android.content.Intent
import android.provider.Settings

fun openNotificationSetting(context: Context) {
    val intent = Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS

        // For Android 8+
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)

        // For Android 5-7
        putExtra("app_package", context.packageName)
        putExtra("app_uid", context.applicationInfo.uid)

        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    context.startActivity(intent)
}