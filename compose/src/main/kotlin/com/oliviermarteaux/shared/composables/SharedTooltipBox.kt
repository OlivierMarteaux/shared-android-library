package com.oliviermarteaux.shared.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipScope
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupPositionProvider

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