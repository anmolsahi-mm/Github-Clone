package com.example.githubclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubclone.presentation.home.HomeScreen
import com.example.githubclone.presentation.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination =
        if (FirebaseAuth.getInstance().currentUser != null) Screen.Home.route
        else Screen.Login.route
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

        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }

}