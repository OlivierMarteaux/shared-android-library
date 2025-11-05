package com.oliviermarteaux.shared.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipScope
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupPositionProvider

/**
 * A reusable tooltip box composable that displays a tooltip when hovering, focusing,
 * or interacting with the content. Wraps Material 3's [TooltipBox] for consistent
 * tooltip behavior across the app.
 *
 * ### Features:
 * - Displays a tooltip using a [PopupPositionProvider], with customizable content.
 * - Supports focusable and interactive tooltips.
 * - Can be used to provide context or hints for UI elements.
 *
 * ### Parameters:
 * @param modifier [Modifier] applied to the tooltip box.
 * @param positionProvider Provides the position of the tooltip relative to its content.
 * Defaults to [TooltipDefaults.rememberPlainTooltipPositionProvider].
 * @param tooltip Composable lambda defining the content of the tooltip.
 * @param state [TooltipState] controlling the visibility and behavior of the tooltip.
 * @param focusable If `true`, the tooltip can receive focus for accessibility.
 * @param enableUserInput If `true`, the tooltip reacts to user input (hover, focus, or click).
 * @param content Composable lambda representing the main content that triggers the tooltip.
 *
 * ### Example Usage:
 * ```kotlin
 * SharedTooltipBox(
 *     tooltip = { Text("This is a tooltip") }
 * ) {
 *     Icon(Icons.Default.Info, contentDescription = "Info")
 * }
 * ```
 *
 * @see TooltipBox
 * @see TooltipState
 * @see PopupPositionProvider
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTooltipBox(
    modifier: Modifier = Modifier,
    positionProvider: PopupPositionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
    tooltip: @Composable TooltipScope.() -> Unit = {},
    state: TooltipState = TooltipState(),
    focusable: Boolean = true,
    enableUserInput: Boolean = true,
    content: @Composable (() -> Unit)
){
    TooltipBox(
        modifier = modifier,
        positionProvider = positionProvider,
        tooltip = tooltip,
        state = state,
        focusable = focusable,
        enableUserInput = enableUserInput,
        content = content
    )
}