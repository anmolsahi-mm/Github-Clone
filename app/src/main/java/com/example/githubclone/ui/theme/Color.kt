package com.example.githubclone.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val lightButtonColor = Color(0xFFFFFFFF)
val darkButtonColor = Color(0xFF1f1f24)
val darkB = Color(0xFF050505)
val lightTextColor = Color(0xFFbdbfc7)
val linkTextColor = Color(0XFF2e8fff)

val Colors.SignInButtonColor
    @Composable
    get() = if (isLight) darkButtonColor else lightButtonColor