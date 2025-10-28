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