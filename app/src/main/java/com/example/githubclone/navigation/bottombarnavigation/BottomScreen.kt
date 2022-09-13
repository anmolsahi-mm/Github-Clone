package com.example.githubclone.navigation.bottombarnavigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.githubclone.R

sealed class BottomScreen (val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Home : BottomScreen(
        "home_Screen",
        R.string.home,
        Icons.Default.Home
    )

    object Notifications : BottomScreen(
        "notifications_screen",
        R.string.notifications,
        Icons.Default.Notifications
    )

    object Explore : BottomScreen(
        "explore_screen",
        R.string.explore,
        Icons.Default.Place
    )

    object Profile : BottomScreen(
        "Profile_screen",
        R.string.profile,
        Icons.Default.Person
    )
}