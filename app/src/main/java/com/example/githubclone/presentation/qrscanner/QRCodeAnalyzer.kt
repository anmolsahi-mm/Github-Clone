package com.example.githubclone.presentation.qrscanner

import android.annotation.SuppressLint
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@SuppressLint("UnsafeOptInUsageError")
suspend fun processImageProxy(
    barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy,
    onSuccess: (barcodes: List<Barcode>) -> Unit,
) {
    val inputImage =
        InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

    try {
        onSuccess(barcodeScanner.process(inputImage).await())
    } catch (exception: Exception) {
        Timber.e(exception.message.toString())
    } finally {
        imageProxy.close()
    }
}