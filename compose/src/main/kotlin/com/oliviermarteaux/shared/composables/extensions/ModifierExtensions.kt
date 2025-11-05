package com.oliviermarteaux.shared.composables.extensions

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import kotlin.ranges.coerceIn

/**
 * Scales the size of a composable based on a font size and an optional scale factor,
 * applying the result to both width and height.
 *
 * The computed size can be optionally constrained by [min] and [max] values.
 *
 * ### Parameters:
 * @param fontSize The base font size to scale from. Defaults to [LocalTextStyle.current.fontSize].
 * @param scale A multiplier applied to [fontSize]. Defaults to `1.0f`.
 * @param min Optional minimum size constraint in [Dp].
 * @param max Optional maximum size constraint in [Dp].
 *
 * ### Returns:
 * A [Modifier] that sets the size of the composable according to the scaled value.
 *
 * ### Example Usage:
 * ```kotlin
 * Box(
 *     Modifier.fontScaledSize(fontSize = 16.sp, scale = 1.5f, min = 24.dp, max = 32.dp)
 * )
 * ```
 */
@Composable
fun Modifier.fontScaledSize(
    fontSize: TextUnit = LocalTextStyle.current.fontSize,
    scale: Float = 1.0f,
    min: Dp? = null,
    max: Dp? = null
): Modifier {
    val density = LocalDensity.current
    val sizeDp = with(density) { fontSize.toDp() * scale }

    val finalSize = when {
        min != null && max != null -> sizeDp.coerceIn(min, max)
        min != null -> max(sizeDp, min)
        max != null -> min(sizeDp, max)
        else -> sizeDp
    }

    return this.then(Modifier.size(finalSize))
}

/**
 * Scales the width of a composable based on a font size and an optional scale factor,
 * while keeping height unchanged. The resulting width can be constrained by [min] and [max].
 *
 * ### Parameters:
 * @param fontSize The base font size to scale from. Defaults to [LocalTextStyle.current.fontSize].
 * @param scale A multiplier applied to [fontSize]. Defaults to `1.0f`.
 * @param min Optional minimum width in [Dp].
 * @param max Optional maximum width in [Dp].
 *
 * ### Returns:
 * A [Modifier] that sets the width of the composable according to the scaled value.
 *
 * ### Example Usage:
 * ```kotlin
 * Box(
 *     Modifier.fontScaledWidth(fontSize = 14.sp, scale = 2f, min = 20.dp)
 * )
 * ```
 */
@Composable
fun Modifier.fontScaledWidth(
    fontSize: TextUnit = LocalTextStyle.current.fontSize,
    scale: Float = 1.0f,
    min: Dp? = null,
    max: Dp? = null
): Modifier {
    val density = LocalDensity.current
    val sizeDp = with(density) { fontSize.toDp() * scale }

    val finalWidth = when {
        min != null && max != null -> sizeDp.coerceIn(min, max)
        min != null -> max(sizeDp, min)
        max != null -> min(sizeDp, max)
        else -> sizeDp
    }

    return this.then(Modifier.width(finalWidth))
}

/**
 * Scales the height of a composable based on a font size and an optional scale factor,
 * while keeping width unchanged. The resulting height can be constrained by [min] and [max].
 *
 * ### Parameters:
 * @param fontSize The base font size to scale from. Defaults to [LocalTextStyle.current.fontSize].
 * @param scale A multiplier applied to [fontSize]. Defaults to `1.0f`.
 * @param min Optional minimum height in [Dp].
 * @param max Optional maximum height in [Dp].
 *
 * ### Returns:
 * A [Modifier] that sets the height of the composable according to the scaled value.
 *
 * ### Example Usage:
 * ```kotlin
 * Box(
 *     Modifier.fontScaledHeight(fontSize = 12.sp, scale = 1.8f, max = 40.dp)
 * )
 * ```
 */
@Composable
fun Modifier.fontScaledHeight(
    fontSize: TextUnit = LocalTextStyle.current.fontSize,
    scale: Float = 1.0f,
    min: Dp? = null,
    max: Dp? = null
): Modifier {
    val density = LocalDensity.current
    val sizeDp = with(density) { fontSize.toDp() * scale }

    val finalHeight = when {
        min != null && max != null -> sizeDp.coerceIn(min, max)
        min != null -> max(sizeDp, min)
        max != null -> min(sizeDp, max)
        else -> sizeDp
    }

    return this.then(Modifier.height(finalHeight))
}