package com.kuvalin.brainstorm.navigation.statistics

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuvalin.brainstorm.navigation.staticsClasses.StatisticsScreen

@Composable
fun StatisticsScreenNavGraph(
    navHostController: NavHostController,
    warsStatisticsScreenContent: @Composable () -> Unit,
    friendsStatisticsScreenContent: @Composable () -> Unit,
    gamesStatisticsScreenContent: @Composable () -> Unit
) {

    val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        fadeIn(tween(durationMillis = 200, easing = FastOutLinearInEasing))
    }
    val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(tween(durationMillis = 200, easing = FastOutLinearInEasing))
    }

    NavHost(
        navController = navHostController,
        startDestination = StatisticsScreen.WarsStatistics.route
    ) {
        composable(
            StatisticsScreen.WarsStatistics.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            warsStatisticsScreenContent()
        }
        composable(
            StatisticsScreen.FriendsStatistics.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            friendsStatisticsScreenContent()
        }
        composable(
            StatisticsScreen.GamesStatistics.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            gamesStatisticsScreenContent()
        }
    }
}
