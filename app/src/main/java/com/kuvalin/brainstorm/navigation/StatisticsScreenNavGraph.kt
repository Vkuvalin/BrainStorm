package com.kuvalin.brainstorm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.StatisticsScreen

@Composable
fun StatisticsScreenNavGraph(
    navHostController: NavHostController,
    warsStatisticsScreenContent: @Composable () -> Unit,
    friendsStatisticsScreenContent: @Composable () -> Unit,
    gamesStatisticsScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = StatisticsScreen.WarsStatistics.route
    ) {
        composable(StatisticsScreen.WarsStatistics.route) {
            warsStatisticsScreenContent()
        }
        composable(StatisticsScreen.FriendsStatistics.route) {
            friendsStatisticsScreenContent()
        }
        composable(StatisticsScreen.GamesStatistics.route) {
            gamesStatisticsScreenContent()
        }
    }
}
