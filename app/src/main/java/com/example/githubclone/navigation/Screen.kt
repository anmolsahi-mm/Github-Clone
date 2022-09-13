package com.example.githubclone.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Main : Screen("main_screen")
    object QRScannerScreen: Screen("qr_scanner_screen")
}
