package com.example.githubclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubclone.presentation.explore.ExploreScreen
import com.example.githubclone.presentation.home.HomeScreen
import com.example.githubclone.presentation.login.LoginScreen
import com.example.githubclone.presentation.notification.NotificationsScreen
import com.example.githubclone.presentation.profile.ProfileScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination =
        FirebaseAuth.getInstance().currentUser?.let { Screen.Home.route } ?: Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen {
                navController.navigate(route = Screen.Home.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen()
        }

        composable(Screen.Explore.route) {
            ExploreScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }

}