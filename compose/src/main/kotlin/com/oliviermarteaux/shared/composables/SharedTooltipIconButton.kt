package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipScope
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.PopupPositionProvider

/**
 * A reusable icon button that displays a tooltip when hovered, focused, or interacted with.
 * Combines [IconButton] with [SharedTooltipBox] for consistent tooltip behavior.
 *
 * ### Features:
 * - Displays an icon with optional tint and content description.
 * - Shows a tooltip when hovering, focusing, or interacting with the button.
 * - Fully customizable button colors, interaction source, and enabled state.
 * - Supports custom tooltip content and positioning via [positionProvider].
 *
 * ### Parameters:
 * @param icon The [IconSource] to display inside the button.
 * @param modifier [Modifier] applied to the tooltip icon button.
 * @param contentDescription Optional description for accessibility purposes.
 * @param tint Tint color applied to the icon. Defaults to [LocalContentColor.current].
 * @param onClick Lambda invoked when the button is clicked.
 * @param enabled Controls whether the button is clickable.
 * @param colors Color configuration for the icon button ([IconButtonColors]).
 * @param interactionSource Optional [MutableInteractionSource] for observing interactions.
 * @param positionProvider Determines the position of the tooltip relative to the button.
 * Defaults to [TooltipDefaults.rememberPlainTooltipPositionProvider].
 * @param tooltip Composable lambda defining the content of the tooltip.
 * @param state [TooltipState] controlling visibility and behavior of the tooltip.
 * @param focusable If `true`, the tooltip can receive focus for accessibility.
 * @param enableUserInput If `true`, the tooltip reacts to user input (hover, focus, or click).
 *
 * ### Example Usage:
 * ```kotlin
 * SharedTooltipIconButton(
 *     icon = IconSource.VectorIcon(Icons.Default.Info),
 *     tooltip = { Text("Information") },
 *     onClick = { /* handle click */ }
 * )
 * ```
 *
 * @see IconButton
 * @see SharedTooltipBox
 * @see SharedIcon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTooltipIconButton(
    icon: IconSource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    positionProvider: PopupPositionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
    tooltip: @Composable TooltipScope.() -> Unit = {},
    state: TooltipState = TooltipState(),
    focusable: Boolean = true,
    enableUserInput: Boolean = true,
){
    SharedTooltipBox(
        modifier = modifier,
        positionProvider = positionProvider,
        tooltip = tooltip,
        state = state,
        focusable = focusable,
        enableUserInput = enableUserInput,
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
        ) {
            SharedIcon(
                icon = icon,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}