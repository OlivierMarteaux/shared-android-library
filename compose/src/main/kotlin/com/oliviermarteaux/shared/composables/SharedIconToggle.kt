package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A reusable toggleable icon button that switches between two icons when toggled.
 *
 * This composable wraps [IconToggleButton] and uses [SharedIcon] to display either
 * a "checked" or "unchecked" icon based on the current state. It provides a flexible
 * and type-safe way to create toggleable icon buttons that support both [Painter]-
 * and [ImageVector]-based icons via [IconSource].
 *
 * ### Behavior:
 * - Displays [iconChecked] when [checked] is `true`, otherwise displays [iconUnchecked].
 * - Triggers [onCheckedChange] when toggled.
 * - Supports custom tint, shape, colors, and interaction handling through standard
 *   Material parameters.
 * - Can be disabled by setting [enabled] to `false`.
 *
 * ### Parameters:
 * @param iconChecked The [IconSource] to display when the toggle is in the checked state.
 * @param iconUnchecked The [IconSource] to display when the toggle is in the unchecked state.
 * @param modifier The [Modifier] applied to the outer [IconToggleButton] container.
 * @param contentDescription A description of the icon for accessibility services (e.g., TalkBack).
 * Use `null` for decorative icons.
 * @param tint The color used to tint the icon.
 * Defaults to [LocalContentColor.current].
 * @param iconModifier The [Modifier] applied to the internal [SharedIcon] composable.
 * @param checked Whether this toggle button is currently checked (selected).
 * @param onCheckedChange Called when the toggle state changes.
 * Receives the new `checked` value.
 * @param enabled Controls whether the button is interactive.
 * When `false`, the button is displayed as disabled.
 * @param colors The color configuration for different toggle states, provided by [IconToggleButtonColors].
 * Defaults to [IconButtonDefaults.iconToggleButtonColors].
 * @param interactionSource Optional [MutableInteractionSource] for observing interaction states
 * (e.g., pressed, focused, hovered).
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun FavoriteToggle(
 *     isFavorite: Boolean,
 *     onToggle: (Boolean) -> Unit
 * ) {
 *     SharedIconToggle(
 *         iconChecked = IconSource.VectorIcon(Icons.Default.Favorite),
 *         iconUnchecked = IconSource.VectorIcon(Icons.Default.FavoriteBorder),
 *         contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
 *         tint = if (isFavorite) Color.Red else LocalContentColor.current,
 *         checked = isFavorite,
 *         onCheckedChange = onToggle
 *     )
 * }
 * ```
 *
 * @see SharedIcon
 * @see IconToggleButton
 * @see IconSource
 * @see IconButtonDefaults.iconToggleButtonColors
 * @see LocalContentColor
 */
@Composable
fun SharedIconToggle(
    // SharedIcon parameters
    iconChecked: IconSource,
    iconUnchecked: IconSource,
    modifier : Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
    iconModifier: Modifier = Modifier,
    // IconToggleButton Parameters
    checked: Boolean  = false,
    onCheckedChange: (Boolean) -> Unit = {},
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,

) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource
    ) {
        if (checked) SharedIcon(
            icon = iconChecked,
            contentDescription = contentDescription,
            tint = tint,
            modifier = iconModifier
        )
        else SharedIcon(
            icon = iconUnchecked,
            contentDescription = contentDescription,
            tint = tint,
            modifier = iconModifier
        )
    }
}