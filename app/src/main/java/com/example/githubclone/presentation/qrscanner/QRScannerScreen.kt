package com.example.githubclone.presentation.qrscanner

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.githubclone.R
import com.example.githubclone.utils.Constants.GITHUB_REPO_REGEX
import com.google.mlkit.vision.barcode.BarcodeScanning
import timber.log.Timber


@Composable
fun QRScannerScreen() {

    var isTorchEnable by remember { mutableStateOf(false) }
    var camera: Camera? = null
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

    if (hasCameraPermission) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()

                    val selector = CameraSelector.Builder()
                        .requireLensFacing(LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(previewView.width, previewView.height)
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    val barcodeScanner = BarcodeScanning.getClient()

                    val imageCapture = ImageCapture.Builder()
                        .setFlashMode(ImageCapture.FLASH_MODE_ON)
                        .build()

                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context)
                    ) { imageProxy ->
                        processImageProxy(barcodeScanner, imageProxy) { barcodes ->
                            barcodes.forEach {
                                Timber.d(it.rawValue.toString())
                                val regex =
                                    GITHUB_REPO_REGEX.toRegex()
                                if (regex.matches(it.rawValue.toString())) {
                                    val intent =
                                        Intent(ACTION_VIEW, Uri.parse(it.rawValue.toString()))
                                    context.startActivity(intent)
                                }
                            }
                        }
                    }
                    try {
                        camera = cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageCapture,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )

            QRCodeBackground()

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Icon(
                    modifier = Modifier.padding(32.dp),
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = stringResource(R.string.open_gallery),
                    tint = Color.White
                )

                Icon(
                    modifier = Modifier
                        .clickable {
                            isTorchEnable = !isTorchEnable
                            if (camera?.cameraInfo?.hasFlashUnit() == true) {
                                camera?.cameraControl?.enableTorch(isTorchEnable)
                            }
                        }
                        .padding(32.dp),
                    painter = painterResource(id = R.drawable.ic_flash),
                    contentDescription = stringResource(R.string.turn_on_flash),
                    tint = Color.White
                )
            }
        }
    }
}