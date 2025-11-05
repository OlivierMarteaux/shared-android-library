package com.oliviermarteaux.shared.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliviermarteaux.shared.utils.TOAST_DURATION

/**
 * Displays a toast message conditionally based on a [trigger] flag.
 * Wraps [SharedToast] to show a message only when [trigger] is true.
 *
 * ### Features:
 * - Shows a temporary toast message at the bottom of the screen.
 * - Automatically dismisses after [durationMillis].
 * - Supports custom bottom padding and modifier.
 * - Useful for triggering toast messages in response to state changes or events.
 *
 * ### Parameters:
 * @param modifier [Modifier] applied to the toast's [SharedToast] surface.
 * @param durationMillis Duration in milliseconds before the toast automatically disappears.
 * Defaults to [TOAST_DURATION].
 * @param bottomPadding Vertical padding from the bottom of the screen in dp. Defaults to 80.
 * @param trigger Boolean flag to control whether the toast is displayed.
 * @param text The message to display in the toast.
 *
 * ### Example Usage:
 * ```kotlin
 * var showToast by remember { mutableStateOf(false) }
 *
 * Button(onClick = { showToast = true }) {
 *     Text("Show Toast")
 * }
 *
 * TriggeredToast(
 *     trigger = showToast,
 *     text = "Action completed!"
 * )
 * ```
 *
 * @see SharedToast
 */
@Composable
fun TriggeredToast (
    modifier: Modifier = Modifier,
    durationMillis: Long = TOAST_DURATION,
    bottomPadding: Int = 80,
    trigger: Boolean,
    text: String,
){
    if (trigger){
        SharedToast(
            text = text,
            durationMillis = durationMillis,
            bottomPadding = bottomPadding,
            modifier = modifier,
        )
    }
}