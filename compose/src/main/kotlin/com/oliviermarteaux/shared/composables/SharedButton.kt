package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A reusable, customizable button component that displays a text label and handles user clicks.
 *
 * This composable wraps [Button] from Material Design 3, providing sensible defaults
 * and additional customization options for consistent styling across the app.
 *
 * ### Behavior:
 * - Displays a text label centered inside the button.
 * - Supports enabling/disabling, custom shapes, borders, colors, and elevation.
 * - Executes the provided [onClick] lambda when pressed.
 * - Uses [MaterialTheme.typography.labelMedium] for text styling.
 *
 * ### Parameters:
 * @param text The text label displayed inside the button.
 * @param modifier The [Modifier] applied to the button layout.
 * @param enabled Controls whether the button is clickable.
 * If `false`, the button appears disabled and does not respond to input.
 * @param shape The shape of the button container.
 * Defaults to [ButtonDefaults.shape].
 * @param colors The color configuration for the button’s background and content.
 * Defaults to [ButtonDefaults.buttonColors].
 * @param elevation The visual elevation of the button.
 * Defaults to [ButtonDefaults.buttonElevation].
 * @param border Optional [BorderStroke] to draw a border around the button.
 * Defaults to `null` (no border).
 * @param contentPadding The internal padding around the button content.
 * Defaults to [ButtonDefaults.ContentPadding].
 * @param interactionSource An optional [MutableInteractionSource] for observing interaction events
 * (e.g., pressed, focused, hovered).
 * Defaults to `null`.
 * @param textAlign The alignment of the text label within the button.
 * Defaults to [TextAlign.Center].
 * @param onClick The callback function invoked when the button is clicked.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun SubmitButton(onSubmit: () -> Unit) {
 *     SharedButton(
 *         text = "Submit",
 *         onClick = onSubmit,
 *         colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
 *     )
 * }
 * ```
 *
 * @see Button
 * @see ButtonDefaults
 * @see MaterialTheme.typography
 */
@Composable
fun SharedButton(
    text: String = "",
    modifier: Modifier = Modifier,
    icon: IconSource? = null,
    tint: Color = LocalContentColor.current,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    textAlign: TextAlign = TextAlign.Center,
    textColor: Color = Color.Unspecified,
    //_ last parameter = Lambda function
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        val horizontalArrangement = icon?.let{ Arrangement.Start }?:Arrangement.Center
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement,
            modifier = Modifier.fillMaxWidth(),
        ) {
            icon?.let {
                SharedIcon(
                    icon = icon,
                    modifier = Modifier.size(24.dp),
                    tint = tint
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
            Text(
                text = text,
                color = textColor,
                textAlign = textAlign,
            )
        }
    }
}