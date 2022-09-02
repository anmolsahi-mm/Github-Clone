package com.example.githubclone.presentation.qrscanner

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

@Composable
fun QRScannerScreen() {
    val code by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCameraPermission = granted
            })

    LaunchedEffect(key1 = true) {
        launcher.launch(android.Manifest.permission.CAMERA)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(Size(previewView.width, previewView.height))
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    val barcodeScanner = BarcodeScanning.getClient()

                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context)
                    ) { imageProxy ->
                        processImageProxy(barcodeScanner, imageProxy)
                    }

                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            text = code,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )

    }
}

@SuppressLint("UnsafeOptInUsageError")
fun processImageProxy(
    barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy,
) {
    val inputImage =
        InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

    barcodeScanner.process(inputImage)
        .addOnSuccessListener { barcodes ->
            barcodes.forEach {
                Timber.d(it.rawValue.toString())
            }
        }
        .addOnFailureListener {
            Timber.e(it.message.toString())
        }.addOnCompleteListener {
            // When the image is from CameraX analysis use case, must call image.close() on received
            // images when finished using them. Otherwise, new images may not be received or the camera
            // may stall.
            imageProxy.close()
        }
}