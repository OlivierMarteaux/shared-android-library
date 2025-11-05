package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
/**
 * Displays a [CircularProgressIndicator] centered both vertically and horizontally
 * within the available space.
 *
 * This composable is useful for indicating a loading or in-progress state
 * while content is being fetched or processed.
 *
 * ### Behavior:
 * - The progress indicator is placed inside a [Column] that fills the entire available size.
 * - The indicator is centered using [Arrangement.Center] and [Alignment.CenterHorizontally].
 *
 * ### Parameters:
 * @param modifier The [Modifier] to be applied to the layout container.
 * Defaults to [Modifier], and typically used to adjust size, padding, or background.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun LoadingScreen() {
 *     CenteredCircularProgressIndicator()
 * }
 * ```
 *
 * @see CircularProgressIndicator
 * @see Modifier
 */
@Composable
fun CenteredCircularProgressIndicator(
    modifier: Modifier = Modifier
) {
    Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

