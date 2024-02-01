package com.kuvalin.brainstorm.navigation

import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

sealed class NavigationItem (
    val screen: Screen,
    val iconFileName: String
){

    object Home: NavigationItem (
        screen = Screen.Home,
        iconFileName = "tab_home.png"
    )
    object Friends: NavigationItem (
        screen = Screen.Friends,
        iconFileName = "tab_friends.png"
    )
    object Achievements: NavigationItem (
        screen = Screen.Achievements,
        iconFileName = "tab_achievements.png"
    )
    object Statistic: NavigationItem (
        screen = Screen.Statistic,
        iconFileName = "tab_statistics.png"
    )
    object Games: NavigationItem (
        screen = Screen.Games,
        iconFileName = "tab_games.png"
    )
}