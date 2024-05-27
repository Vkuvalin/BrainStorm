package com.kuvalin.brainstorm.navigation.friends

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen

@Composable
fun FriendsScreenNavGraph(
    navHostController: NavHostController,
    listFriendsScreenContent: @Composable () -> Unit,
    messagesScreenContent: @Composable () -> Unit,
    requestsScreenContent: @Composable () -> Unit
) {

    val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        fadeIn(tween(durationMillis = 200, easing = FastOutLinearInEasing))
    }
    val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        fadeOut(tween(durationMillis = 200, easing = FastOutLinearInEasing))
    }

    NavHost(
        navController = navHostController,
        startDestination = FriendsScreen.ListFriends.route
    ) {
        composable(
            FriendsScreen.ListFriends.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            listFriendsScreenContent()
        }
        composable(
            FriendsScreen.Messages.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            messagesScreenContent()
        }
        composable(
            FriendsScreen.Requests.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            requestsScreenContent()
        }
    }
}


