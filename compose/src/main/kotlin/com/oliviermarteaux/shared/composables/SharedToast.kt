package com.oliviermarteaux.shared.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oliviermarteaux.shared.utils.TOAST_DURATION
import kotlinx.coroutines.delay

/**
 * Displays a temporary toast message at the bottom of the screen using a custom
 * composable layout. The toast automatically disappears after a specified duration.
 *
 * ### Behavior:
 * - Appears as a small overlay with rounded corners, dark background, and white text.
 * - Automatically dismisses after [durationMillis] milliseconds.
 * - Positioned at the bottom center of the screen with optional [bottomPadding].
 * - Supports animated size changes when content changes.
 *
 * ### Parameters:
 * @param text The message to display inside the toast.
 * @param modifier [Modifier] applied to the toast's [Surface] for further customization.
 * @param durationMillis Duration in milliseconds before the toast automatically disappears. Defaults to [TOAST_DURATION].
 * @param bottomPadding Vertical padding from the bottom of the screen in dp. Defaults to 80.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun ExampleScreen() {
 *     Button(onClick = { /* trigger toast */ }) {
 *         Text("Show Toast")
 *     }
 *     SharedToast(text = "This is a toast message!")
 * }
 * ```
 */
@Composable
fun SharedToast(
    text: String,
    modifier: Modifier = Modifier,
    durationMillis: Long = TOAST_DURATION,
    bottomPadding: Int = 80
) {
    var visible by remember { mutableStateOf(true) }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = modifier
                    .wrapContentWidth()
                    .animateContentSize()
                    .padding(8.dp),
                color = Color.Black.copy(alpha = 0.8f),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 4.dp
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp
                )
            }
        }

        // Auto dismiss after durationMillis
        LaunchedEffect(Unit) {
            delay(durationMillis)
            visible = false
        }
    }
}