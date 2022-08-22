package com.example.githubclone.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login_screen")
    object Home: Screen("home_Screen")
}
