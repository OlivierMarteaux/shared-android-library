package com.oliviermarteaux.shared.cameraX

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository
) : ViewModel() {
    fun savePhotoUrl(url: String) { cameraRepository.photoUrl = url }
}