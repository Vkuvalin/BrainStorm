package com.kuvalin.brainstorm.navigation.staticsClasses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


// Бля, потом разобрать, хули тут написано. Это просто паттерн, но нужно его понять раз, чтобы потом легко воспроизводить.
class NavigationState(
    val navHostController: NavHostController
){

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {saveState = true}
            launchSingleTop = true
            restoreState = true
        }
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}