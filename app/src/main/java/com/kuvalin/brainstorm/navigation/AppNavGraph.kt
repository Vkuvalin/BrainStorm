package com.kuvalin.brainstorm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

@Composable
fun AppNavGraph (
    navHostController: NavHostController,
    mainMenuScreenContent: @Composable () -> Unit,
    friendsScreenContent: @Composable () -> Unit,
    achievementsScreenContent: @Composable () -> Unit,
    statisticScreenContent: @Composable () -> Unit,
    gamesScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ){
        composable(Screen.Home.route) {
            mainMenuScreenContent()
        }
        composable(Screen.Friends.route) {
            friendsScreenContent()
        }
        composable(Screen.Achievements.route) {
            achievementsScreenContent()
        }
        composable(Screen.Statistic.route) {
            statisticScreenContent()
        }
        composable(Screen.Games.route) {
            gamesScreenContent()
        }
    }
}