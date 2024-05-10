package com.kuvalin.brainstorm.navigation.mainmenu.war

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

fun NavGraphBuilder.warScreenNavGraph(
    searchForWarScreenContent: @Composable () -> Unit,
    warScreenContent: @Composable () -> Unit
) {

    val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        fadeIn(tween(durationMillis = 1500))
    }
    val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(tween(durationMillis = 1500))
    }


    navigation(
        startDestination = Screen.SearchForWar.route,
        route = Screen.HomeWar.route
    ) {
        composable(
            route = Screen.SearchForWar.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            searchForWarScreenContent()
        }
        composable(
            route = "${Screen.War.route}/{sessionId}",
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType }),
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            warScreenContent()
        }

    }

}
