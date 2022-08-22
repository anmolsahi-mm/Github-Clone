package com.example.githubclone.presentation.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ExploreScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Explore", textAlign = TextAlign.Center)
    }
}