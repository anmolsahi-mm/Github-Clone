package com.example.githubclone.presentation.qrscanner

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.example.githubclone.ui.theme.transparentBlackColor

@Composable
fun QRCodeBackground() {

    val configuration = LocalConfiguration.current

    Canvas(modifier = Modifier.fillMaxSize()) {

        val canvasHeight = this.size.height
        val canvasWidth = this.size.width
        val canvasCenter = this.size.center

        val textPaint = Paint().apply {
            this.color = Color.White.toArgb()
            this.textSize = 12.dp.toPx()
            this.textAlign = Paint.Align.CENTER
        }

        val scannerPath = Path().apply {
            addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        center = Offset(x = canvasCenter.x, y = canvasCenter.y),
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
            fillType = PathFillType.EvenOdd
            close()
        }

        val backgroundPath = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, canvasHeight)
            lineTo(canvasWidth, canvasHeight)
            lineTo(canvasWidth, 0f)
            fillType = PathFillType.EvenOdd
            addPath(scannerPath)
            close()
        }

        drawPath(path = backgroundPath, color = transparentBlackColor)
        drawPath(path = scannerPath, color = Color.Transparent)
        drawPath(path = scannerPath, color = Color.Green, style = Stroke(width = 2.dp.toPx()))

        drawContext.canvas.nativeCanvas.drawText(
            "Scan a GitHub Repository QR code",
            scannerPath.getBounds().bottomCenter.x,
            scannerPath.getBounds().bottomCenter.y + 28.dp.toPx(),
            textPaint
        )
    }
}