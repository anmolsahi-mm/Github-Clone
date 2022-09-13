package com.example.githubclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubclone.presentation.login.LoginScreen
import com.example.githubclone.presentation.main.MainScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination =
        FirebaseAuth.getInstance().currentUser?.let { Screen.Main.route } ?: Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen {
                navController.navigate(route = Screen.Main.route) {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = Screen.Main.route) {
            MainScreen()
        }
    }

}