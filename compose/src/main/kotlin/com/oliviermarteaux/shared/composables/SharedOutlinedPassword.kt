package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oliviermarteaux.shared.extensions.isHardEnough

/**
 * A reusable outlined text field specifically for password input with built-in validation,
 * error handling, and customizable icons and supporting text.
 *
 * This composable wraps [OutlinedTextField] and provides:
 * - Password-specific visual transformation ([PasswordVisualTransformation]).
 * - Optional validation for password strength or minimum length via [minLength].
 * - Automatic or custom error state handling ([isError]).
 * - Optional supporting text or error messages displayed below the field.
 * - Customizable leading/trailing icons, prefix/suffix, and text style.
 *
 * ### Behavior:
 * - If [passwordSetting] is true, validates that the password is non-empty and meets
 *   the minimum length ([minLength]). Otherwise, error checking is disabled.
 * - Displays an error state and optional [errorText] when validation fails.
 * - Supports IME actions and keyboard type appropriate for password input.
 *
 * ### Parameters:
 * @param value The current text entered in the password field.
 * @param modifier The [Modifier] applied to the text field layout.
 * @param enabled Controls whether the field is editable.
 * @param readOnly If `true`, the field is non-editable but still focusable.
 * @param textStyle The [TextStyle] applied to the input text.
 * @param label The label displayed above (and as placeholder within) the field.
 * @param leadingIcon Optional composable displayed at the start of the field.
 * @param trailingIcon Optional composable displayed at the end of the field.
 * @param prefix Optional composable displayed before the input text.
 * @param suffix Optional composable displayed after the input text.
 * @param supportingText Optional supporting text displayed below the field when not in error.
 * @param isError Optional explicit flag to control the error state. Defaults to automatic validation.
 * @param visualTransformation Visual transformation applied to the text (e.g., password masking).
 * @param keyboardActions Defines keyboard IME actions. Defaults to [KeyboardActions.Default].
 * @param singleLine If `true`, the text field will occupy a single line.
 * @param maxLines Maximum number of lines allowed. Defaults to 1 for single-line fields.
 * @param minLines Minimum number of lines allowed. Defaults to 1.
 * @param interactionSource Optional [MutableInteractionSource] for observing interaction states.
 * @param shape The shape of the text field container. Defaults to [OutlinedTextFieldDefaults.shape].
 * @param colors The color scheme of the text field, using [OutlinedTextFieldDefaults.colors].
 * @param imeAction IME action (e.g., Done, Next). Defaults to [ImeAction.Done].
 * @param keyboardType The keyboard type to use. Defaults to [KeyboardType.Password].
 * @param minLength Minimum required password length. Defaults to 6.
 * @param passwordSetting If `true`, enables automatic validation. Defaults to `false`.
 * @param bottomPadding Extra vertical spacing below the supporting/error text. Defaults to 0.dp.
 * @param errorText Text displayed when the field is in an error state.
 * @param onValueChange Lambda invoked when the input value changes.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
 *     SharedOutlinedPassword(
 *         value = password,
 *         onValueChange = onPasswordChange,
 *         label = "Password",
 *         supportingText = "Use at least 6 characters",
 *         errorText = "Password is too weak",
 *         passwordSetting = true,
 *         leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }
 *     )
 * }
 * ```
 *
 * @see OutlinedTextField
 * @see OutlinedTextFieldDefaults
 * @see PasswordVisualTransformation
 * @see KeyboardType.Password
 */
@Composable
fun SharedOutlinedPassword(
    //_ text field params
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
    supportingText: String? = null,
    isError: Boolean? = null,
    visualTransformation: VisualTransformation = PasswordVisualTransformation(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Password,
    minLength: Int = 6,
    passwordSetting:Boolean = false,
    bottomPadding: Dp = 0.dp,
    errorText: String? = null,
    onValueChange: (String) -> Unit,
){
    val defaultError = !value.run {isNotEmpty() && isHardEnough(minLength)}
    val error = if (passwordSetting) isError?:defaultError else false

    SupportingText(
        supportingText = supportingText,
        errorText = errorText,
        isError = error,
        bottomPadding = bottomPadding
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = { Text(label) },
            placeholder = { Text(label) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = null,
            isError = error,
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
            )
        )
    }
}