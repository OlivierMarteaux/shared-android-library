package com.oliviermarteaux.shared.composables

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a flexible source for an icon that can be either a [Painter] or an [ImageVector].
 *
 * This sealed class allows the [SharedIcon] composable to accept both drawable-based
 * and vector-based icons in a type-safe manner.
 *
 * ### Variants:
 * - [PainterIcon]: Wraps a [Painter] resource, typically used for raster or XML drawables.
 * - [VectorIcon]: Wraps an [ImageVector], typically used for Material or custom vector icons.
 */
sealed class IconSource {
    data class PainterIcon(val painter: Painter) : IconSource()
    data class VectorIcon(val imageVector: ImageVector) : IconSource()
}