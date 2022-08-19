package com.example.githubclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubclone.presentation.home.HomeScreen
import com.example.githubclone.presentation.loginscreen.LoginScreen

@Composable
fun GithubNavigation(navController: NavHostController, onSignInClick: () -> Unit) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route){
            LoginScreen(onSignInClick = onSignInClick)
        }

        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }

}