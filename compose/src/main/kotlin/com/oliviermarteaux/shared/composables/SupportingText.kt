package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A helper composable that displays supporting text or error text below a content
 * composable, such as a text field. Automatically switches text and color based
 * on the [isError] state.
 *
 * ### Features:
 * - Displays optional supporting text when there is no error.
 * - Displays optional error text when [isError] is true.
 * - Automatically applies standard styling for font size, line height, and weight.
 * - Provides optional bottom padding below the text for spacing.
 * - Can wrap any content composable above the supporting/error text.
 *
 * ### Parameters:
 * @param supportingText Optional text to display when there is no error.
 * @param errorText Optional text to display when [isError] is true.
 * @param isError If `true`, displays [errorText] and applies error color.
 * Defaults to `false`.
 * @param bottomPadding Extra vertical space below the supporting/error text.
 * Defaults to 0.dp.
 * @param content Composable content displayed above the supporting/error text.
 *
 * ### Example Usage:
 * ```kotlin
 * SupportingText(
 *     supportingText = "Enter your full name",
 *     errorText = "Name cannot be empty",
 *     isError = name.isEmpty()
 * ) {
 *     OutlinedTextField(
 *         value = name,
 *         onValueChange = { name = it },
 *         label = { Text("Full Name") }
 *     )
 * }
 * ```
 */
@Composable
fun SupportingText(
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    errorText: String? = null,
    isError: Boolean = false,
    bottomPadding: Dp = 0.dp,
    content: @Composable () -> Unit,

    ) {
    Column (modifier = modifier){
        content()
        Box (modifier = Modifier.clearAndSetSemantics{}){
            if (supportingText != null || errorText != null) {
                Text(
                    text = if (isError) errorText ?: "" else supportingText ?: "",
                    color = if (isError) MaterialTheme.colorScheme.error else Color.Unspecified,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 4.dp)
                )
            }
            Spacer(Modifier.height(bottomPadding))
        }
    }
}