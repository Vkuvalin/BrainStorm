package com.kuvalin.brainstorm.navigation.staticsClasses

import android.net.Uri

sealed class Screen(
    val route: String
) {

    object Home : Screen(ROUTE_HOME)
    object Friends : Screen(ROUTE_FRIENDS)
    object Achievements : Screen(ROUTE_ACHIEVEMENTS)
    object Statistic : Screen(ROUTE_STATISTIC)
    object Games : Screen(ROUTE_GAMES)

    companion object {

        const val ROUTE_HOME = "home"
        const val ROUTE_FRIENDS = "friends"
        const val ROUTE_ACHIEVEMENTS = "achievements"
        const val ROUTE_STATISTIC = "statistic"
        const val ROUTE_GAMES = "games"
    }
}

// Не ебу, что это такое. Пока пусть побудет здесь
fun String.encode(): String {
    return Uri.encode(this)
}