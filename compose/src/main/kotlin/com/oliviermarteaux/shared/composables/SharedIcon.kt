package com.oliviermarteaux.shared.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon

sealed class IconSource {
    data class PainterIcon(val painter: Painter) : IconSource()
    data class VectorIcon(val imageVector: ImageVector) : IconSource()
}
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