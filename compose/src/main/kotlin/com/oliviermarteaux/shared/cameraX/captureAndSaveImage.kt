package com.oliviermarteaux.shared.cameraX

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun captureAndSaveImage(
    context: Context,
    imageCapture: ImageCapture,
    outputDirectory: File
): File? = suspendCoroutine { continuation ->

    val photoFile = File(
        outputDirectory,
        "IMG_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                continuation.resume(photoFile)
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
                continuation.resume(null)
            }
        }
    )
}
