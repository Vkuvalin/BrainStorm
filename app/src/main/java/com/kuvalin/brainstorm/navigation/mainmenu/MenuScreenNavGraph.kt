package com.kuvalin.brainstorm.navigation.mainmenu

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kuvalin.brainstorm.navigation.mainmenu.war.warScreenNavGraph
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

fun NavGraphBuilder.menuScreenNavGraph(
    currentRoute: NavBackStackEntry?,
    menuScreenContent: @Composable () -> Unit,
    mainMenuScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,

    searchForWarScreenContent: @Composable () -> Unit,
    warScreenContent: @Composable () -> Unit
) {

    // Эта хуйня обновляется лишние разы, но в общем-то похуй
    var animatedContentTransitionDirection = AnimatedContentTransitionScope.SlideDirection.Left
    animatedContentTransitionDirection = if (currentRoute?.destination?.route == Screen.MainMenu.route) {
            AnimatedContentTransitionScope.SlideDirection.Right
        } else {
            AnimatedContentTransitionScope.SlideDirection.Left
        }


    navigation(
        startDestination = Screen.MainMenu.route,
        route = Screen.Home.route
    ) {
        composable(
            route = Screen.MainMenu.route,
            enterTransition = {slideIntoContainer(towards = animatedContentTransitionDirection,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = animatedContentTransitionDirection, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))}
        ) {
            mainMenuScreenContent()
        }
        composable(
            route = Screen.Menu.route,
            enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))}
        ) {
            menuScreenContent()
        }
        composable(
            route = Screen.Profile.route,
            enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))}
        ) {
            profileScreenContent()
        }


        warScreenNavGraph(
            searchForWarScreenContent = searchForWarScreenContent,
            warScreenContent = warScreenContent
        )

    }

}
