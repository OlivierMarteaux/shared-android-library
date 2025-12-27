package com.oliviermarteaux.shared.composables

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

@Composable
fun SharedGoogleMapFromString(
    modifier: Modifier = Modifier,
    address: String
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        // Default position, e.g., Paris
        position = CameraPosition.fromLatLngZoom(LatLng(48.8566, 2.3522), 12f)
    }
    var latLng by remember(address) { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(address) {
        latLng = getLatLngFromAddress(context, address)
    }

    LaunchedEffect(latLng) {
//        latLng?.let {
//            cameraPositionState.animate(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(it, 15f))
//        }
        latLng?.let {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false,
            scrollGesturesEnabled = false,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = false
        ),
        properties = MapProperties(isMyLocationEnabled = false)
    ) {
        latLng?.let {
            Marker(
                state = MarkerState(position = it),
                title = address
            )
        }
    }
}

private suspend fun getLatLngFromAddress(context: Context, address: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context)
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses?.isNotEmpty() == true) {
                val location = addresses[0]
                LatLng(location.latitude, location.longitude)
            } else {
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}