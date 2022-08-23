package com.example.githubclone.presentation.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubclone.navigation.bottombarnavigation.BottomNavGraph
import com.example.githubclone.navigation.bottombarnavigation.BottomScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavBar(navController = navController)
    }) { paddingValues ->
        BottomNavGraph(navController = navController, paddingValues = paddingValues)
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navItems = listOf(
        BottomScreen.Home,
        BottomScreen.Notifications,
        BottomScreen.Explore,
        BottomScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        navItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = { navController.navigate(navItem.route) },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = "Bottom item icon")
                },
                label = { Text(text = stringResource(id = navItem.title)) }
            )
        }
    }
}