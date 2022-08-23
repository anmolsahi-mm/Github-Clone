package com.example.githubclone.navigation.bottombarnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubclone.presentation.explore.ExploreScreen
import com.example.githubclone.presentation.home.HomeScreen
import com.example.githubclone.presentation.notification.NotificationsScreen
import com.example.githubclone.presentation.profile.ProfileScreen

@Composable
fun BottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues),
        startDestination = BottomScreen.Home.route
    ) {
        composable(BottomScreen.Home.route) {
            HomeScreen()
        }

        composable(BottomScreen.Notifications.route) {
            NotificationsScreen()
        }

        composable(BottomScreen.Explore.route) {
            ExploreScreen()
        }

        composable(BottomScreen.Profile.route) {
            ProfileScreen()
        }
    }
}