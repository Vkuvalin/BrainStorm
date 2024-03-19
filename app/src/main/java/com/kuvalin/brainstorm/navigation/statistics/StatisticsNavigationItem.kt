package com.kuvalin.brainstorm.navigation.statistics

import com.kuvalin.brainstorm.navigation.staticsClasses.Screen
import com.kuvalin.brainstorm.navigation.staticsClasses.StatisticsScreen

sealed class StatisticsNavigationItem (
    val screen: Screen,
    val sectionName: String
){

    object WarsStatistics: StatisticsNavigationItem(
        screen = StatisticsScreen.WarsStatistics,
        sectionName = "Wars"
    )
    object FriendsStatistics: StatisticsNavigationItem(
        screen = StatisticsScreen.FriendsStatistics,
        sectionName = "Friends"
    )
    object GamesStatistics: StatisticsNavigationItem(
        screen = StatisticsScreen.GamesStatistics,
        sectionName = "Games"
    )
}