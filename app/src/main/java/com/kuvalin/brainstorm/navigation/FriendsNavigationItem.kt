package com.kuvalin.brainstorm.navigation

import com.kuvalin.brainstorm.navigation.staticsClasses.Screen
import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen

sealed class FriendsNavigationItem (
    val screen: Screen,
    val sectionName: String
){

    object ListFriends: FriendsNavigationItem (
        screen = FriendsScreen.ListFriends,
        sectionName = "Friends"
    )
    object Messages: FriendsNavigationItem (
        screen = FriendsScreen.Messages,
        sectionName = "Messages"
    )
    object Requests: FriendsNavigationItem (
        screen = FriendsScreen.Requests,
        sectionName = "Requests"
    )
}