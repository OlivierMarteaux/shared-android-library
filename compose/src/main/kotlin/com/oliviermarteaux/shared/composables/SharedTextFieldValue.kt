package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

/**
 * A reusable text field with support for labels, icons, error handling, and
 * supporting text. This composable wraps [TextField] and adds a flexible
 * structure for consistent input fields across the app.
 *
 * ### Behavior:
 * - Displays a label and optional placeholder text.
 * - Supports leading/trailing icons, prefix, and suffix composables.
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
 * @param shape The shape of the text field container. Defaults to [TextFieldDefaults.shape].
 * @param colors The color scheme of the text field. Defaults to [TextFieldDefaults.colors].
 * @param imeAction The IME action button for the keyboard. Defaults to [ImeAction.Next].
 * @param keyboardType The type of keyboard to use. Defaults to [KeyboardType.Text].
 * @param onValueChange Lambda invoked when the input text changes.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun NameInput(name: String, onNameChange: (String) -> Unit) {
 *     SharedTextField(
 *         value = name,
 *         onValueChange = onNameChange,
 *         label = "Full Name",
 *         supportingText = "Enter your full name",
 *         leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
 *     )
 * }
 * ```
 *
 * @see TextField
 * @see TextFieldDefaults
 * @see TextFieldColors
 * @see KeyboardType
 */
@Composable
fun SharedTextFieldValue(
    //_ text field params
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String = "",
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: String  = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    ),
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    //_ on value change
    onValueChange: (TextFieldValue) -> Unit = {},
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle.copy(textAlign = TextAlign.Start),
        label = { Text(label) },
        placeholder = { Text(placeholder)},
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = { Text(supportingText) },
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