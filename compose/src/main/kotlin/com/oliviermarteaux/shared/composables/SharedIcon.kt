package com.oliviermarteaux.shared.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon

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
/**
 * Displays an icon using either a [Painter] or [ImageVector] source via [IconSource].
 *
 * This composable provides a unified interface for rendering icons from multiple source types
 * while preserving the customization capabilities of the Material [Icon] component.
 *
 * ### Behavior:
 * - Automatically selects between [Icon] overloads based on the provided [IconSource] type.
 * - Supports custom tint colors and accessibility descriptions.
 * - Accepts an optional [Modifier] for sizing, padding, or alignment.
 *
 * ### Parameters:
 * @param icon The [IconSource] defining whether the icon uses a [Painter] or an [ImageVector].
 * @param modifier The [Modifier] to be applied to the icon for layout or styling purposes.
 * @param contentDescription A description of the icon for accessibility services (e.g., TalkBack).
 * Use `null` for decorative icons.
 * @param tint The tint color applied to the icon.
 * Defaults to [Color.Unspecified], which preserves the icon’s original color.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun ProfileIcon() {
 *     SharedIcon(
 *         icon = IconSource.VectorIcon(Icons.Default.Person),
 *         contentDescription = "Profile",
 *         tint = MaterialTheme.colorScheme.primary
 *     )
 * }
 *
 * @Composable
 * fun LogoIcon(painter: Painter) {
 *     SharedIcon(
 *         icon = IconSource.PainterIcon(painter),
 *         contentDescription = "App Logo"
 *     )
 * }
 * ```
 *
 * @see Icon
 * @see IconSource
 * @see Painter
 * @see ImageVector
 */
@Composable
fun SharedIcon(
    icon: IconSource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.Unspecified
) {
    when (icon) {
        is IconSource.PainterIcon -> Icon(
            painter = icon.painter,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
        is IconSource.VectorIcon -> Icon(
            imageVector = icon.imageVector,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint
        )
    }
}