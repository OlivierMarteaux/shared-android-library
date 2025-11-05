package com.oliviermarteaux.shared.composables.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

/**
 * A wrapper around [Text] that applies the `titleMedium` typography style from
 * [MaterialTheme.typography] while allowing full customization of text properties.
 *
 * ### Features:
 * - Uses Material 3 `titleMedium` typography as the default style.
 * - Allows overriding color, font size, weight, family, letter spacing, line height, and more.
 * - Supports text alignment, overflow handling, soft wrapping, and maximum/minimum lines.
 * - Provides a callback [onTextLayout] for obtaining layout results.
 *
 * ### Parameters:
 * @param text The text to display.
 * @param modifier [Modifier] applied to the text composable.
 * @param color Text color. Defaults to [Color.Unspecified], using the style's color.
 * @param fontSize Font size of the text. Defaults to [TextUnit.Unspecified].
 * @param fontStyle Optional font style, e.g., [FontStyle.Italic].
 * @param fontWeight Optional font weight, e.g., [FontWeight.Bold].
 * @param fontFamily Optional font family.
 * @param letterSpacing Letter spacing for the text.
 * @param textDecoration Optional text decoration, e.g., underline or line-through.
 * @param textAlign Optional alignment of the text within its bounds.
 * @param lineHeight Line height for the text.
 * @param overflow How to handle text overflow. Defaults to [TextOverflow.Clip].
 * @param softWrap Whether the text should break at soft line breaks.
 * @param maxLines Maximum number of lines for the text. Defaults to `Int.MAX_VALUE`.
 * @param minLines Minimum number of lines for the text. Defaults to 1.
 * @param onTextLayout Optional callback that receives [TextLayoutResult].
 *
 * ### Example Usage:
 * ```kotlin
 * TextTitleMedium(
 *     text = "Subtitle",
 *     color = Color.DarkGray,
 *     fontWeight = FontWeight.Medium
 * )
 * ```
 *
 * @see Text
 * @see MaterialTheme.typography
 */
@Composable
fun TextTitleMedium (
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
){
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = MaterialTheme.typography.titleMedium,
    )
}