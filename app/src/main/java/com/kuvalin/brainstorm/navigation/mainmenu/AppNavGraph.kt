package com.kuvalin.brainstorm.navigation.mainmenu

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

val navigationItems = listOf(
    Screen.MainMenu.route,
    NavigationItem.Friends.screen.route,
    NavigationItem.Achievements.screen.route,
    NavigationItem.Statistic.screen.route,
    NavigationItem.Games.screen.route
)


@Composable
fun AppNavGraph (
    navHostController: NavHostController,
    mainMenuScreenContent: @Composable () -> Unit,
    menuScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,

    friendsScreenContent: @Composable () -> Unit,
    achievementsScreenContent: @Composable () -> Unit,
    statisticScreenContent: @Composable () -> Unit,

    gamesScreenContent: @Composable () -> Unit
) {

    //region animatedContentTransitionDirection - определяем направление навигации
    var animatedContentTransitionDirection by remember { mutableStateOf(AnimatedContentTransitionScope.SlideDirection.Left) }
    var oldNavDestination by remember { mutableStateOf(Screen.MainMenu.route) }

//    var rrrrrr by remember { mutableStateOf(false) }
//    rrrrrr = GlobalStates.runGameScreenState.collectAsState().value
//    val noEnterTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = { fadeIn(tween(durationMillis = 2000)) }
//    val noExitTransition : AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = { fadeOut(tween(durationMillis = 2000)) }


    LaunchedEffect(Unit) {
        navHostController.addOnDestinationChangedListener { _, destination, _ ->

            val newNavDestination = destination.route
            var oldIndex = 0
            var newIndex = 0
            navigationItems.forEachIndexed { index, route ->
                if (route == oldNavDestination) { oldIndex = index }
                if (route == newNavDestination) { newIndex = index }
            }
            animatedContentTransitionDirection = if (oldIndex > newIndex) {
                AnimatedContentTransitionScope.SlideDirection.Right
            } else {
                AnimatedContentTransitionScope.SlideDirection.Left
            }
            oldNavDestination = newNavDestination!!
//            rrrrrr = false
        }
    }

//    LaunchedEffect(rrrrrr) {
//        if (oldNavDestination == Screen.Games.route && rrrrrr){
//            animatedContentTransitionDirection = AnimatedContentTransitionScope.SlideDirection.Start
//        }
//    }

    //endregion


    val currentRoute = navHostController.currentBackStackEntryAsState().value // Костыль
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ){
        menuScreenNavGraph(
            currentRoute = currentRoute,
            mainMenuScreenContent = mainMenuScreenContent,
            menuScreenContent = menuScreenContent,
            profileScreenContent = profileScreenContent
        )
        composable(
            route = Screen.Friends.route,
            enterTransition = {slideIntoContainer(towards = animatedContentTransitionDirection,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = animatedContentTransitionDirection, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))}
        ) {
            friendsScreenContent()
        }

        composable(
            route = Screen.Achievements.route,
            enterTransition = {slideIntoContainer(towards = animatedContentTransitionDirection,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = animatedContentTransitionDirection, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))}
        ) {
            achievementsScreenContent()
        }
        composable(
            route = Screen.Statistic.route,
            enterTransition = {slideIntoContainer(towards = animatedContentTransitionDirection,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = animatedContentTransitionDirection, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,animationSpec = tween(300))}
        ) {
            statisticScreenContent()
        }

        composable(
            route = Screen.Games.route,
            enterTransition = {slideIntoContainer(towards = animatedContentTransitionDirection,animationSpec = tween(300))},
            exitTransition = {slideOutOfContainer(towards = animatedContentTransitionDirection, animationSpec = tween(300))},
            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End,animationSpec = tween(300))},
            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End,animationSpec = tween(300))}
        ) {
            gamesScreenContent()
//            rrrrrr = true // Как памятник 5ти часам в воздух брошенным оставляю (может пригодиться)
        }

    }
}






