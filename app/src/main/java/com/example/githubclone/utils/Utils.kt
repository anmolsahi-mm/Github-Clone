package com.example.githubclone.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

fun Context.showToast(message: String, isLongToast: Boolean = false) {
    Toast.makeText(
        this,
        message,
        if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}