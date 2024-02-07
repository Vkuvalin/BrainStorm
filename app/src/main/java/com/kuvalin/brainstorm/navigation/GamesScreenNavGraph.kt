package com.kuvalin.brainstorm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.GamesScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

@Composable
fun GamesScreenNavGraph(
    navHostController: NavHostController,
    operationsScreenContent: @Composable () -> Unit,
    flickMasterScreenContent: @Composable () -> Unit,
    simplicityScreenContent: @Composable () -> Unit,
    reverseRPSScreenContent: @Composable () -> Unit,
    touchTheNumberScreenContent: @Composable () -> Unit,
    followTheLeaderScreenContent: @Composable () -> Unit,
    additionAddictionScreenContent: @Composable () -> Unit,
    matchingScreenContent: @Composable () -> Unit,
    colorOfDeceptionScreenContent: @Composable () -> Unit,
    concentrationScreenContent: @Composable () -> Unit,
    highOrLowScreenContent: @Composable () -> Unit,
    birdWatchingScreenContent: @Composable () -> Unit,
    additionLinkScreenContent: @Composable () -> Unit,
    reflectionScreenContent: @Composable () -> Unit,
    unfollowTheLeaderScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Games.route // Можно ли так? Скоро узнаю
    ) {

        composable(GamesScreen.Operations.route) {
            operationsScreenContent()
        }
        composable(GamesScreen.FlickMaster.route) {
            flickMasterScreenContent()
        }
        composable(GamesScreen.Simplicity.route) {
            simplicityScreenContent()
        }
        composable(GamesScreen.ReverseRPS.route) {
            reverseRPSScreenContent()
        }
        composable(GamesScreen.TouchTheNumber.route) {
            touchTheNumberScreenContent()
        }
        composable(GamesScreen.FollowTheLeader.route) {
            followTheLeaderScreenContent()
        }
        composable(GamesScreen.AdditionAddiction.route) {
            additionAddictionScreenContent()
        }
        composable(GamesScreen.Matching.route) {
            matchingScreenContent()
        }
        composable(GamesScreen.ColorOfDeception.route) {
            colorOfDeceptionScreenContent()
        }
        composable(GamesScreen.Concentration.route) {
            concentrationScreenContent()
        }
        composable(GamesScreen.HighOrLow.route) {
            highOrLowScreenContent()
        }
        composable(GamesScreen.BirdWatching.route) {
            birdWatchingScreenContent()
        }
        composable(GamesScreen.AdditionLink.route) {
            additionLinkScreenContent()
        }
        composable(GamesScreen.Reflection.route) {
            reflectionScreenContent()
        }
        composable(GamesScreen.UnfollowTheLeader.route) {
            unfollowTheLeaderScreenContent()
        }

    }
}
