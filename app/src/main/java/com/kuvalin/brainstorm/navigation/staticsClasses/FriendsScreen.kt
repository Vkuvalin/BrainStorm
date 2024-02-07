package com.kuvalin.brainstorm.navigation.staticsClasses

sealed class FriendsScreen(
    val route: String
) {
    object ListFriends : Screen(ROUTE_LIST_FRIENDS)
    object Messages : Screen(ROUTE_MESSAGES)
    object Requests : Screen(ROUTE_REQUESTS)

    companion object {

        const val ROUTE_LIST_FRIENDS = "list_friends"
        const val ROUTE_MESSAGES = "messages"
        const val ROUTE_REQUESTS = "requests"
    }
}