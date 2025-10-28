package com.oliviermarteaux.shared.ui

import com.oliviermarteaux.shared.utils.TOAST_DURATION
import kotlinx.coroutines.delay

suspend fun showToastFlag(
    duration: Long = TOAST_DURATION,
    setFlag: (Boolean) -> Unit,
) {
    setFlag(true)
    delay(duration)
    setFlag(false)
}