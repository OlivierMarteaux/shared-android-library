package com.oliviermarteaux.shared.ui

import com.oliviermarteaux.shared.utils.TOAST_DURATION
import kotlinx.coroutines.delay

/**
 * Temporarily sets a Boolean flag to `true` for a specified duration, then resets it to `false`.
 *
 * This function is useful for showing transient UI states, such as displaying a toast or
 * temporary message in a composable, without directly handling the UI inside the function.
 *
 * ### Parameters:
 * @param duration Duration in milliseconds to keep the flag set to `true`. Defaults to [TOAST_DURATION].
 * @param setFlag Lambda function used to set or reset the Boolean flag.
 *
 * ### Behavior:
 * 1. Immediately calls [setFlag] with `true`.
 * 2. Suspends for [duration] milliseconds.
 * 3. Calls [setFlag] with `false` to reset the flag.
 *
 * ### Example Usage:
 * ```kotlin
 * var showToast by remember { mutableStateOf(false) }
 *
 * LaunchedEffect(Unit) {
 *     showToastFlag {
 *         showToast = it
 *     }
 * }
 * ```
 */
suspend fun showToastFlag(
    duration: Long = TOAST_DURATION,
    setFlag: (Boolean) -> Unit,
) {
    setFlag(true)
    delay(duration)
    setFlag(false)
}