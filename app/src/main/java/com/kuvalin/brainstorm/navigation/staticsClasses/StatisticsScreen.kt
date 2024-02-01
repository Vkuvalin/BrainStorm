package com.kuvalin.brainstorm.navigation.staticsClasses

sealed class StatisticsScreen(
    val route: String
) {
    object WarsStatistics : Screen(ROUTE_WARS_STATISTICS)
    object FriendsStatistics : Screen(ROUTE_FRIENDS_STATISTICS)
    object GamesStatistics : Screen(ROUTE_GAMES_STATISTICS)

    companion object {

        const val ROUTE_WARS_STATISTICS = "wars_statistics"
        const val ROUTE_FRIENDS_STATISTICS = "friends_statistics"
        const val ROUTE_GAMES_STATISTICS = "games_statistics"
    }
}