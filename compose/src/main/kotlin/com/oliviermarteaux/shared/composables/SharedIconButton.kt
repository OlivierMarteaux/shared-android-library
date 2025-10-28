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