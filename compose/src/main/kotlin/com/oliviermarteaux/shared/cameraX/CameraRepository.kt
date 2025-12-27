package com.oliviermarteaux.shared.cameraX

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Singleton
class CameraRepository @Inject constructor() {

    var photoUrl: String? by mutableStateOf(null)
}