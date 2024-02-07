package com.kuvalin.brainstorm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

@Composable
fun FriendsScreenNavGraph(
    navHostController: NavHostController,
    listFriendsScreenContent: @Composable () -> Unit,
    messagesScreenContent: @Composable () -> Unit,
    requestsScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = FriendsScreen.ListFriends.route
    ) {
        composable(FriendsScreen.ListFriends.route) {
            listFriendsScreenContent()
        }
        composable(FriendsScreen.Messages.route) {
            messagesScreenContent()
        }
        composable(FriendsScreen.Requests.route) {
            requestsScreenContent()
        }
    }
}


