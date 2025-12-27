package com.oliviermarteaux.shared.composables

import android.content.Context
import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.oliviermarteaux.shared.compose.R

@Composable
fun StaticGoogleMap(
    // map
    address: String = "1600 Amphitheatre Parkway, Mountain View, CA 94043, USA",
    zoom: Int = 10,
    size: Size = Size(400, 400),
    mapApiKey: String = "",
    // async image
    modifier: Modifier = Modifier,
    crossfade: Boolean =  true,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    placeholder: Int = R.drawable.placeholder,
    error: Int = R.drawable.placeholder,
    fallback: Int = R.drawable.placeholder,
    context: Context = LocalContext.current,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    val sizeString: String = "${size.width}x${size.height}"
    val mapUrl = "https://maps.googleapis.com/maps/api/staticmap?" +
            "center=$address" +
            "&zoom=$zoom" +
            "&size=$sizeString" +
            "&key=$mapApiKey"
    SharedAsyncImage(
        photoUri = mapUrl,
        modifier = modifier,
        crossfade =  crossfade,
        contentScale = contentScale,
        contentDescription = contentDescription,
        placeholder = placeholder,
        error = error,
        fallback = fallback,
        context = context,
        alignment = alignment,
        alpha = alpha,
        colorFilter = colorFilter
    )
}