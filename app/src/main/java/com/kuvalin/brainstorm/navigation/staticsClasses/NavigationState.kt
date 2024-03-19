package com.kuvalin.brainstorm.navigation.staticsClasses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class NavigationState(
    val navHostController: NavHostController
){


    fun navigateTo(route: String) {
        navHostController.navigate(route)
        {
            popUpTo(navHostController.graph.findStartDestination().id) {saveState = true}
            launchSingleTop = true
            restoreState = true
        }
    }

    // Функция перехода в меню с главного экрана (точки)
    fun navigateToMenu() {
        navHostController.navigate(Screen.Menu.route)
    }

    // Функция перехода в профиль с главного экрана (аватарка)
    fun navigateToProfile() {
        navHostController.navigate(Screen.Profile.route)
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController(),
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}