package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A reusable outlined text field with support for labels, icons, error handling, and
 * supporting text. This composable wraps [OutlinedTextField] and adds a flexible
 * structure for consistent input fields across the app.
 *
 * ### Behavior:
 * - Displays a label and optional placeholder text.
 * - Supports leading/trailing icons, prefix, and suffix composables.
 * - Handles error state display with optional [errorText].
 * - Displays optional [supportingText] below the field.
 * - Accepts standard text field parameters such as [visualTransformation],
 *   [keyboardActions], [singleLine], and [keyboardType].
 *
 * ### Parameters:
 * @param value The current text value of the field.
 * @param modifier [Modifier] applied to the text field layout.
 * @param enabled Controls whether the field is editable.
 * @param readOnly If `true`, the field is non-editable but focusable.
 * @param textStyle [TextStyle] applied to the input text.
 * @param label The label text displayed above and as placeholder inside the field.
 * @param leadingIcon Optional composable displayed at the start of the field.
 * @param trailingIcon Optional composable displayed at the end of the field.
 * @param prefix Optional composable displayed before the input text.
 * @param suffix Optional composable displayed after the input text.
 * @param supportingText Optional supporting text displayed below the field.
 * @param isError Controls whether the field is displayed in an error state.
 * @param visualTransformation Transformation applied to the input text (e.g., password masking).
 * @param keyboardActions Defines IME action behavior.
 * @param singleLine If `true`, restricts the field to a single line.
 * @param maxLines Maximum number of text lines allowed.
 * @param minLines Minimum number of text lines allowed.
 * @param interactionSource Optional [MutableInteractionSource] for observing interactions.
 * @param shape The shape of the text field container. Defaults to [OutlinedTextFieldDefaults.shape].
 * @param colors The color scheme of the text field. Defaults to [OutlinedTextFieldDefaults.colors].
 * @param imeAction The IME action button for the keyboard. Defaults to [ImeAction.Next].
 * @param keyboardType The type of keyboard to use. Defaults to [KeyboardType.Text].
 * @param icon Optional [ImageVector] icon displayed inside the field.
 * @param iconModifier [Modifier] applied to the icon.
 * @param contentDescription Content description for accessibility of the icon.
 * @param tint The tint color applied to the icon. Defaults to [LocalContentColor.current].
 * @param bottomPadding Extra vertical spacing below the supporting/error text. Defaults to 0.dp.
 * @param errorText Text displayed when [isError] is true.
 * @param onValueChange Lambda invoked when the input text changes.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun NameInput(name: String, onNameChange: (String) -> Unit) {
 *     SharedOutlinedTextField(
 *         value = name,
 *         onValueChange = onNameChange,
 *         label = "Full Name",
 *         supportingText = "Enter your full name",
 *         leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
 *     )
 * }
 * ```
 *
 * @see OutlinedTextField
 * @see OutlinedTextFieldDefaults
 * @see TextFieldColors
 * @see KeyboardType
 */
@Composable
fun SharedOutlinedTextField(
    /*text field params*/
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: String ?  = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    /* icon params */
    icon: ImageVector? = null,
    iconModifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
    bottomPadding: Dp = 0.dp,
    errorText: String? = null,
    /*on value change*/
    onValueChange: (String) -> Unit,
){
    SupportingText(
        supportingText = supportingText,
        errorText = errorText,
        isError = isError,
        bottomPadding = bottomPadding,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle.copy(textAlign = TextAlign.Start),
            label = { Text(label) },
            placeholder = { Text(label) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = null,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
        )
    }
}