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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oliviermarteaux.shared.extensions.isValidEmail

/**
 * A reusable outlined text field designed specifically for email input with built-in validation,
 * error handling, and customizable icons and supporting text.
 *
 * This composable wraps [OutlinedTextField] and adds email-specific validation logic
 * (using [isValidEmail]), as well as a supporting text/error text section. It provides
 * consistent styling and behavior for email input fields across the app.
 *
 * ### Behavior:
 * - Automatically validates the entered text as an email address.
 * - Displays an error state and optional [errorText] when the email is invalid.
 * - Shows optional [supportingText] when additional context or help text is provided.
 * - Supports icons (leading, trailing, prefix, suffix) and standard text field customization options.
 * - Uses [KeyboardType.Email] and [ImeAction.Next] by default for proper keyboard behavior.
 *
 * ### Email Validation:
 * - The default error state is determined automatically by checking if [value] is a valid email.
 * - You can override this by explicitly setting [isError].
 *
 * ### Parameters:
 * @param value The current text entered in the field.
 * @param modifier The [Modifier] applied to the text field layout.
 * @param enabled Controls whether the text field is editable.
 * @param readOnly If `true`, the text field cannot be edited but remains focusable.
 * @param textStyle The [TextStyle] applied to the input text. Defaults to [LocalTextStyle.current].
 * @param label The label displayed above (and as placeholder within) the text field.
 * @param leadingIcon Optional composable displayed at the start of the text field.
 * @param trailingIcon Optional composable displayed at the end of the text field.
 * @param prefix Optional composable displayed before the input text.
 * @param suffix Optional composable displayed after the input text.
 * @param supportingText Optional supporting text displayed below the field when not in error.
 * @param isError Optional explicit flag to control the error state.
 * If `null`, the default email validation determines the error state.
 * @param visualTransformation Defines how the text is visually transformed (e.g., password masking).
 * Defaults to [VisualTransformation.None].
 * @param keyboardActions Defines keyboard IME action behavior. Defaults to [KeyboardActions.Default].
 * @param singleLine If `true`, the text field will only occupy one line.
 * @param maxLines The maximum number of text lines allowed. Defaults to 1 for single-line fields.
 * @param minLines The minimum number of text lines allowed. Defaults to 1.
 * @param interactionSource An optional [MutableInteractionSource] for tracking interactions (e.g., focus, press).
 * @param shape The shape of the text field container. Defaults to [OutlinedTextFieldDefaults.shape].
 * @param colors The color scheme for the text field, using [OutlinedTextFieldDefaults.colors].
 * @param imeAction The IME action (e.g., Next, Done). Defaults to [ImeAction.Next].
 * @param keyboardType The type of keyboard to use. Defaults to [KeyboardType.Email].
 ** @param bottomPadding Additional vertical spacing below the supporting text. Defaults to `0.dp`.
 * @param errorText The text displayed below the field when in an error state.
 * #### Value Change Handling
 * @param onValueChange Callback invoked whenever the input text changes.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun EmailInputField(
 *     email: String,
 *     onEmailChange: (String) -> Unit
 * ) {
 *     SharedOutlinedEmail(
 *         value = email,
 *         onValueChange = onEmailChange,
 *         label = "Email Address",
 *         supportingText = "We'll never share your email",
 *         errorText = "Please enter a valid email address",
 *         leadingIcon = {
 *             Icon(Icons.Default.Email, contentDescription = null)
 *         }
 *     )
 * }
 * ```
 *
 * @see OutlinedTextField
 * @see OutlinedTextFieldDefaults
 * @see KeyboardType
 * @see ImeAction
 * @see isValidEmail
 */
@Composable
fun SharedOutlinedEmail(
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
    supportingText: String? = null,
    isError: Boolean? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Email,
    bottomPadding: Dp = 0.dp,
    errorText: String? = null,
    /* on value change */
    onValueChange: (String) -> Unit,
){
    val defaultError: Boolean = !value.run{isNotEmpty() && isValidEmail()}
    val error :Boolean = isError?:defaultError

    SupportingText(
        supportingText = supportingText,
        errorText = errorText,
        isError = error,
        bottomPadding = bottomPadding,
    ){
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = {Text(label)},
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
            ),
        )
    }
}