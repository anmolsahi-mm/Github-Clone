package com.example.githubclone.presentation.qrscanner

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.githubclone.R
import com.example.githubclone.utils.showToast
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
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
                            android.util.Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    val barcodeScanner = BarcodeScanning.getClient()
                    val imageCapture = ImageCapture.Builder()
                        .setFlashMode(ImageCapture.FLASH_MODE_ON)
                        .build()

                    camera = cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageCapture,
                        imageAnalysis
                    )

                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context)
                    ) { imageProxy ->
                        processImageProxy(barcodeScanner, imageProxy) { barcodes ->
                            barcodes.forEach {
                                Timber.d(it.rawValue.toString())
                                val regex =
                                    "^(http(s)?://)?(\\w{3}\\.)?\\w+\\.com/[\\w\\d-_.]+/[\\w\\d-_.]+".toRegex()
                                if (regex.matches(it.rawValue.toString())) {
                                    val intent =
                                        Intent(ACTION_VIEW, Uri.parse(it.rawValue.toString()))
                                    context.startActivity(intent)
                                } else {
                                    context.showToast("Not a GitHub repository")
                                }
                            }
                        }
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
                modifier = Modifier.fillMaxSize()
            )

            MyCanvas() { width, height ->

            }


            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Icon(
                    modifier = Modifier.padding(32.dp),
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = "Open Gallery",
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
                    contentDescription = "Turn On Flash",
                    tint = Color.White
                )

            }

        }
    }

}

@Composable
fun MyCanvas(imageBounds: (width: Float, height: Float) -> Unit) {
    val configuration = LocalConfiguration.current
    Canvas(modifier = Modifier.fillMaxSize()) {
        val rectPath = Path().let {
            it.addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        center = Offset(x = size.center.x, y = size.center.y),
                        radius =
                        if (configuration.screenWidthDp < configuration.screenHeightDp) {
                            configuration.screenWidthDp.times(1).toFloat()
                        } else {
                            configuration.screenHeightDp.times(1).toFloat()
                        }
                    ),
                    cornerRadius = CornerRadius(40f)
                )
            )
            it.fillType = PathFillType.EvenOdd
            it.close()
            it
        }

        val outerPath = Path().let {
            it.moveTo(0f, 0f)
            it.lineTo(0f, this.size.height)
            it.lineTo(this.size.width, this.size.height)
            it.lineTo(this.size.width, 0f)
            it.fillType = PathFillType.EvenOdd
            it.addPath(rectPath)
            it.close()
            it
        }

        val textPaint = android.graphics.Paint().apply {
            this.color = Color.White.toArgb()
            this.textSize = 12.dp.toPx()
            this.textAlign = android.graphics.Paint.Align.CENTER
        }

        drawPath(path = outerPath, color = Color(0xB3000000))
        drawPath(path = rectPath, color = Color.Transparent)
        drawPath(path = rectPath, color = Color.Green, style = Stroke(width = 2.dp.toPx()))
        drawContext.canvas.nativeCanvas.drawText(
            "Scan a GitHub Repository QR code",
            rectPath.getBounds().bottomCenter.x,
            rectPath.getBounds().bottomCenter.y + 28.dp.toPx(),
            textPaint
        )
        imageBounds(rectPath.getBounds().width, rectPath.getBounds().height)
    }
}

@SuppressLint("UnsafeOptInUsageError")
fun processImageProxy(
    barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy,
    onSuccess: (barcodes: List<Barcode>) -> Unit,
) {
    val inputImage =
        InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

    barcodeScanner.process(inputImage)
        .addOnSuccessListener { barcodes ->
            onSuccess(barcodes)
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