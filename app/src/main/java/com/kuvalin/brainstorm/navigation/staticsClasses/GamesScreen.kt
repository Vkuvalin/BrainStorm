package com.kuvalin.brainstorm.navigation.staticsClasses

sealed class GamesScreen(
    val route: String
) {
    object Operations : Screen(ROUTE_OPERATIONS)
    object FlickMaster : Screen(ROUTE_FLICK_MASTER)
    object Simplicity : Screen(ROUTE_SIMPLICITY)
    object ReverseRPS : Screen(ROUTE_REVERSE_RPS)
    object TouchTheNumber : Screen(ROUTE_TOUCH_THE_NUMBER)
    object FollowTheLeader : Screen(ROUTE_FOLLOW_THE_LEADER)
    object AdditionAddiction : Screen(ROUTE_ADDITION_ADDICTION)
    object Matching : Screen(ROUTE_MATCHING)
    object ColorOfDeception : Screen(ROUTE_COLOR_OF_DECEPTION)
    object Concentration : Screen(ROUTE_CONCENTRATION)
    object HighOrLow : Screen(ROUTE_HIGH_OR_LOW)
    object BirdWatching : Screen(ROUTE_BIRD_WATCHING)
    object AdditionLink : Screen(ROUTE_ADDITION_LINK)
    object Reflection : Screen(ROUTE_REFLECTION)
    object UnfollowTheLeader : Screen(ROUTE_UNFOLLOW_THE_LEADER)

    companion object {

        const val ROUTE_OPERATIONS = "game_operations"
        const val ROUTE_FLICK_MASTER = "game_flick_master"
        const val ROUTE_SIMPLICITY = "game_simplicity"
        const val ROUTE_REVERSE_RPS = "game_reverse_rps"
        const val ROUTE_TOUCH_THE_NUMBER = "game_touch_the_number"
        const val ROUTE_FOLLOW_THE_LEADER = "game_follow_the_leader"
        const val ROUTE_ADDITION_ADDICTION = "game_addition_addiction "
        const val ROUTE_MATCHING = "game_matching"
        const val ROUTE_COLOR_OF_DECEPTION = "game_color_of_deception"
        const val ROUTE_CONCENTRATION = "game_concentration"
        const val ROUTE_HIGH_OR_LOW = "game_high_or_low"
        const val ROUTE_BIRD_WATCHING = "game_bird_watching"
        const val ROUTE_ADDITION_LINK = "game_addition_link"
        const val ROUTE_REFLECTION = "game_reflection"
        const val ROUTE_UNFOLLOW_THE_LEADER = "game_unfollow_the_leader"
    }
}