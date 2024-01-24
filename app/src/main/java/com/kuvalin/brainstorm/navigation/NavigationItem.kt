package com.kuvalin.brainstorm.navigation

import com.kuvalin.brainstorm.navigation.staticsClasses.Screen

sealed class NavigationItem (
    val screen: Screen,
    val titleResId: Int,
    val iconFileName: String
){

    object Home: NavigationItem (
        screen = Screen.Home,
        titleResId = 1,
        iconFileName = "tab_home.png"
    )
    object Friends: NavigationItem (
        screen = Screen.Friends,
        titleResId = 2,
        iconFileName = "tab_friends.png"
    )
    object Achievements: NavigationItem (
        screen = Screen.Achievements,
        titleResId = 3,
        iconFileName = "tab_achievements.png"
    )
    object Statistic: NavigationItem (
        screen = Screen.Statistic,
        titleResId = 4,
        iconFileName = "tab_statistics.png"
    )
    object Games: NavigationItem (
        screen = Screen.Games,
        titleResId = 5,
        iconFileName = "tab_games.png"
    )
}