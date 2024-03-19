package com.kuvalin.brainstorm.navigation.friends

import androidx.compose.runtime.Composable
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


