package com.kuvalin.brainstorm.navigation.staticsClasses

sealed class GamesScreen(
    val route: String
) {
    object GameInitial : GamesScreen(ROUTE_GAME_INITIAL)
    object FlickMaster : GamesScreen(ROUTE_FLICK_MASTER)
    object AdditionAddiction : GamesScreen(ROUTE_ADDITION_ADDICTION)
    object Reflection : GamesScreen(ROUTE_REFLECTION)

    object PathToSafety : GamesScreen(ROUTE_PATH_TO_SAFETY)
    object RapidSorting : GamesScreen(ROUTE_RAPID_SORTING)
    object Make10 : GamesScreen(ROUTE_MAKE_10)
    object BreakTheBlock : GamesScreen(ROUTE_BREAK_THE_BLOCK)
    object HexaChain : GamesScreen(ROUTE_HEXA_CHAIN)
    object ColorSwitch : GamesScreen(ROUTE_COLOR_SWITCH)

    companion object {
        const val ROUTE_GAME_INITIAL = "game_initial"
        const val ROUTE_FLICK_MASTER = "game_flick_master"
        const val ROUTE_ADDITION_ADDICTION = "game_addition_addiction"
        const val ROUTE_REFLECTION = "game_reflection"

        const val ROUTE_PATH_TO_SAFETY = "game_path_to_safety"
        const val ROUTE_RAPID_SORTING = "game_rapid_sorting"
        const val ROUTE_MAKE_10 = "game_make_10"
        const val ROUTE_BREAK_THE_BLOCK = "game_break_the_block"
        const val ROUTE_HEXA_CHAIN = "game_hexa_chain"
        const val ROUTE_COLOR_SWITCH = "game_color_switch"
    }
}