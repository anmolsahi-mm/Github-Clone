package com.example.githubclone.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.githubclone.R

sealed class Screen(val route: String, @StringRes val title: Int?, val icon: ImageVector?) {
    object Login : Screen("login_screen", null, null)

    object Home : Screen(
        "home_Screen",
        R.string.home,
        Icons.Default.Home
    )

    object Notifications : Screen(
        "notifications_screen",
        R.string.notifications,
        Icons.Default.Notifications
    )

    object Explore : Screen(
        "explore_screen",
        R.string.explore,
        Icons.Default.Place
    )

    object Profile : Screen(
        "Profile_screen",
        R.string.profile,
        Icons.Default.Person
    )}
