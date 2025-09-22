package com.oliviermarteaux.shared.extensions

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

// 🔹 Scales both width & height (square)
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

// 🔹 Scales only width
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

// 🔹 Scales only height
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