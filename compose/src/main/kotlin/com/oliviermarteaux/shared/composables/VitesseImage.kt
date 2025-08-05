package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import com.oliviermarteaux.shared.compose.R

/**
 * Displays an image from a photo URI with placeholder and error handling.
 *
 * @param photoUri The URI string of the image to display. May be null.
 * @param modifier Optional [Modifier] for styling the image.
 */
@Composable
fun VitesseImage(
    photoUri: String?,
    modifier: Modifier = Modifier,
){
    val context = LocalContext.current
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(photoUri)
            .crossfade(true)
            .placeholder(R.drawable.placeholder) // shown while loading
            .error(R.drawable.placeholder)       // shown on error
            .fallback(R.drawable.placeholder)    // shown if `photoUri` is null
            .build()
    )
    Image(
        painter = imagePainter,
        contentScale = ContentScale.Crop,
        modifier = modifier.border(Dp.Hairline, MaterialTheme.colorScheme.outlineVariant),
        contentDescription = null
    )
}