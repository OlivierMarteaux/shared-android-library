package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * A reusable icon button that combines [IconButton] and [SharedIcon] for consistent styling and flexibility.
 *
 * This composable wraps a [Material3 IconButton] and displays an icon using the [SharedIcon] component,
 * allowing support for both [Painter]- and [ImageVector]-based icons via [IconSource].
 *
 * ### Behavior:
 * - Renders a clickable icon that triggers [onClick] when pressed.
 * - Supports standard Material properties such as colors, shapes, and enabled state.
 * - Inherits tint and layout control through [tint] and [iconModifier].
 * - If [enabled] is `false`, the button appears disabled and does not respond to clicks.
 *
 * ### Parameters:
 * @param icon The [IconSource] defining whether the icon uses a [Painter] or an [ImageVector].
 * @param contentDescription A text description for accessibility services (e.g., TalkBack).
 * Use `null` for purely decorative icons.
 * @param iconModifier A [Modifier] applied specifically to the inner icon (for size, padding, etc.).
 * @param tint The color applied to the icon.
 * Defaults to [LocalContentColor.current], inheriting the current content color.
 *
 * @param modifier A [Modifier] applied to the outer [IconButton] container.
 * @param enabled Controls whether the button is clickable.
 * When `false`, the button is displayed in a disabled state.
 * @param colors The color scheme for the icon button, defined by [IconButtonColors].
 * Defaults to [IconButtonDefaults.iconButtonColors].
 * @param interactionSource An optional [MutableInteractionSource] for observing interaction states.
 * Defaults to `null`.
 * @param shape The shape of the icon button.
 * Defaults to [IconButtonDefaults.standardShape].
 * @param onClick The lambda function invoked when the button is clicked.
 * Defaults to an empty lambda.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun FavoriteButton(
 *     isFavorite: Boolean,
 *     onToggleFavorite: () -> Unit
 * ) {
 *     SharedIconButton(
 *         icon = IconSource.VectorIcon(
 *             if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
 *         ),
 *         contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
 *         tint = if (isFavorite) Color.Red else LocalContentColor.current,
 *         onClick = onToggleFavorite
 *     )
 * }
 * ```
 *
 * @see SharedIcon
 * @see IconButton
 * @see IconSource
 * @see IconButtonDefaults
 * @see LocalContentColor
 */
@Composable
fun SharedIconButton(
    //_ Icon
    icon: IconSource,
    contentDescription: String? = null,
    iconModifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    //_ IconButton
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = IconButtonDefaults.standardShape,
    onClick: () -> Unit = {}
){
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
        shape = shape
    ) {
        SharedIcon(
            icon = icon,
            contentDescription = contentDescription,
            modifier = iconModifier,
            tint = tint
        )
    }
}