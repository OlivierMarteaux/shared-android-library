package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

/**
 * A reusable wrapper around [Image] that provides consistent configuration options
 * for displaying images across the app.
 *
 * This composable simplifies image rendering by exposing commonly used parameters
 * such as scaling, alignment, opacity, and color filtering, while maintaining
 * the familiar [Image] API.
 *
 * ### Behavior:
 * - Renders an image using the given [painter].
 * - Supports custom scaling through [contentScale] (e.g., fit, crop, fill bounds).
 * - Allows adjusting image alignment, transparency, and optional tinting via [colorFilter].
 * - Intended for displaying both vector and raster drawables consistently in a shared UI layer.
 *
 * ### Parameters:
 * @param painter The [Painter] used to draw the image content (e.g., from a resource or bitmap).
 * @param modifier The [Modifier] applied to the image layout, allowing size, padding, or background customization.
 * @param contentDescription Text used by accessibility services to describe the image.
 * Use `null` for decorative or non-essential images.
 * @param alignment The alignment of the image content within its bounds.
 * Defaults to [Alignment.Center].
 * @param contentScale Defines how the image should be scaled to fit its bounds.
 * Defaults to [ContentScale.Fit].
 * @param alpha The opacity of the image, where `1f` is fully opaque and `0f` is fully transparent.
 * Defaults to [DefaultAlpha].
 * @param colorFilter Optional [ColorFilter] applied to the image (e.g., tint or blend effects).
 * Defaults to `null`.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun ProfilePicture(painter: Painter) {
 *     SharedImage(
 *         painter = painter,
 *         contentDescription = "User profile picture",
 *         modifier = Modifier.size(64.dp).clip(CircleShape),
 *         contentScale = ContentScale.Crop
 *     )
 * }
 * ```
 *
 * @see Image
 * @see Painter
 * @see ContentScale
 * @see ColorFilter
 * @see Alignment
 */
@Composable
fun SharedImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
){
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}