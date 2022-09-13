package com.example.githubclone.presentation.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.githubclone.navigation.bottombarnavigation.BottomNavGraph
import com.example.githubclone.navigation.bottombarnavigation.BottomScreen
import com.example.githubclone.utils.getCurrentRoute

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
    val navItems = remember {
        mutableStateListOf(
            BottomScreen.Home,
            BottomScreen.Notifications,
            BottomScreen.Explore,
            BottomScreen.Profile
        )
    }

    val currentRoute = getCurrentRoute(navController = navController)

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