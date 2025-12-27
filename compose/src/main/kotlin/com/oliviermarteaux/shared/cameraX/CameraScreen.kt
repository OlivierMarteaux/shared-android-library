package com.oliviermarteaux.shared.cameraX

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.oliviermarteaux.shared.composables.SharedScaffold

@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = remember { Preview.Builder().build() }
    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    SharedScaffold(
        onFabClick =  {
            takePicture(
                context = context,
                imageCapture = imageCapture,
                onImageCapture = {
                    cameraViewModel.savePhotoUrl(it)
                    navigateBack()
                }
            )
        }
    ) {
        AndroidView(
            factory = { ctx ->
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                val previewView = PreviewView(ctx)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Bind use cases
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )

                    preview.surfaceProvider = previewView.surfaceProvider

                }, ContextCompat.getMainExecutor(ctx))

                previewView
            }
        )
    }
}