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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTooltipIconButton(
    icon: ImageVector,
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