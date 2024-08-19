package com.kuvalin.brainstorm.navigation.games

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuvalin.brainstorm.navigation.staticsClasses.GamesScreen

@Composable
fun GamesScreenNavGraph(
    navHostController: NavHostController,
    gameInitialScreenContent: @Composable () -> Unit,
    flickMasterScreenContent: @Composable () -> Unit,
    additionAddictionScreenContent: @Composable () -> Unit,
    reflectionScreenContent: @Composable () -> Unit,
    pathToSafetyScreenContent: @Composable () -> Unit,
    rapidSortingScreenContent: @Composable () -> Unit,
    make10ScreenContent: @Composable () -> Unit,
    breakTheBlockScreenContent: @Composable () -> Unit,
    hexaChainScreenContent: @Composable () -> Unit,
    colorSwitchScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = GamesScreen.GameInitial.route
    ) {


        val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
            fadeIn(tween(durationMillis = 400, easing = FastOutLinearInEasing))
        }
        val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
            fadeOut(tween(durationMillis = 400, easing = FastOutLinearInEasing))
        }


        composable(
            route = GamesScreen.GameInitial.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            gameInitialScreenContent()
        }
        composable(
            route = GamesScreen.FlickMaster.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            flickMasterScreenContent()
        }
        composable(
            route = GamesScreen.AdditionAddiction.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            additionAddictionScreenContent()
        }
        composable(
            route = GamesScreen.Reflection.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            reflectionScreenContent()
        }
        composable(
            route = GamesScreen.PathToSafety.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            pathToSafetyScreenContent()
        }
        composable(
            route = GamesScreen.RapidSorting.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            rapidSortingScreenContent()
        }
        composable(
            route = GamesScreen.Make10.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            make10ScreenContent()
        }
        composable(
            route = GamesScreen.BreakTheBlock.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            breakTheBlockScreenContent()
        }
        composable(
            route = GamesScreen.HexaChain.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            hexaChainScreenContent()
        }
        composable(
            route = GamesScreen.ColorSwitch.route,
            enterTransition = {noEnterTransition()}, exitTransition = {noExitTransition()},
            popEnterTransition = {noEnterTransition()}, popExitTransition = {noExitTransition()}
        ) {
            colorSwitchScreenContent()
        }

    }
}
