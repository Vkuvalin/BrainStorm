package com.kuvalin.brainstorm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

fun NavGraphBuilder.menuScreenNavGraph(
    menuScreenContent: @Composable () -> Unit,
    mainMenuScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit
) {

    navigation(
        startDestination = Screen.MainMenu.route,
        route = Screen.Home.route
    ) {
        composable(Screen.MainMenu.route) {
            mainMenuScreenContent()
        }
        composable(Screen.Menu.route) {
            menuScreenContent()
        }
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
    }

}
