package com.kuvalin.brainstorm.navigation.friends

import com.kuvalin.brainstorm.navigation.staticsClasses.FriendsScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

sealed class FriendsNavigationItem (
    val screen: Screen,
    val sectionName: String
){

    object ListFriends: FriendsNavigationItem(
        screen = FriendsScreen.ListFriends,
        sectionName = "Friends"
    )
    object Messages: FriendsNavigationItem(
        screen = FriendsScreen.Messages,
        sectionName = "Messages"
    )
    object Requests: FriendsNavigationItem(
        screen = FriendsScreen.Requests,
        sectionName = "Requests"
    )
}