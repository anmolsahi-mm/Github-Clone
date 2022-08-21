package com.example.githubclone.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubclone.presentation.common.checkForExistingUser
import com.example.githubclone.presentation.home.HomeScreen
import com.example.githubclone.presentation.loginscreen.LoginScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun NavGraph(navController: NavHostController) {
    val activity = LocalContext.current as Activity
    val firebaseUser: FirebaseUser? = checkForExistingUser(activity = activity)

    NavHost(
        navController = navController,
        startDestination =
        if (firebaseUser != null) Screen.Home.route
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