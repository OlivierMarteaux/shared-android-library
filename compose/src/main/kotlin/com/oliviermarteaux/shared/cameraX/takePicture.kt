package com.oliviermarteaux.shared.cameraX

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun takePicture(context: Context, imageCapture: ImageCapture, onImageCapture: (String) -> Unit){
    val outputDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    Log.d("OM_TAG", "takePicture: outputDir = $outputDir")
    CoroutineScope(Dispatchers.Main).launch {
        val savedFile = captureAndSaveImage(context, imageCapture, outputDir)
        savedFile?.let { onImageCapture(Uri.fromFile(it).toString()) }
        Log.d("OM_TAG", "takePicture: result = $savedFile")
        Log.d("OM_TAG", "takePicture: uri = ${Uri.fromFile(savedFile)}")
    }
}