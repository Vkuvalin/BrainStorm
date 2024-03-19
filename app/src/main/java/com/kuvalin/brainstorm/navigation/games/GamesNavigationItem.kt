package com.kuvalin.brainstorm.navigation.games

import com.kuvalin.brainstorm.navigation.staticsClasses.GamesScreen

sealed class GamesNavigationItem (
    val screen: GamesScreen,
    val miniatureGameImage: String,
    val gameInstructionImage: String,
    val sectionName: String,
    val gameDescription: String
){

    object FlickMaster: GamesNavigationItem(
        screen = GamesScreen.FlickMaster,
        miniatureGameImage = "ic_game_flick_master.jpg",
        gameInstructionImage = "img_flick_master_instructions.jpg",
        sectionName = "Flick Master",
        gameDescription = "Проводите пальцем в направлении синей стрелки и в обратном для красной."
    )
    object AdditionAddiction: GamesNavigationItem(
        screen = GamesScreen.AdditionAddiction,
        miniatureGameImage = "ic_game_addition_link.jpg",
        gameInstructionImage = "img_addition_addiction_instructions.jpg",
        sectionName = "Addition Addiction",
        gameDescription = "Перемещайтесь по цифрам, чтобы получить указанное значение."
    )
    object Reflection: GamesNavigationItem(
        screen = GamesScreen.Reflection,
        miniatureGameImage = "ic_game_reflection.jpg",
        gameInstructionImage = "img_reflection_instructions.jpg",
        sectionName = "Reflection",
        gameDescription = "Коснитесь места, где отражается свет."
    )
    object PathToSafety: GamesNavigationItem(
        screen = GamesScreen.PathToSafety,
        miniatureGameImage = "ic_game_path_to_safety.jpg",
        gameInstructionImage = "img_path_to_safety_instructions.jpg",
        sectionName = "Path To Safety",
        gameDescription = "Избегайте бомб, соединяйте флаги. Увеличьте свой счет, собрав звезду."
    )
    object RapidSorting: GamesNavigationItem(
        screen = GamesScreen.RapidSorting,
        miniatureGameImage = "ic_game_rapid_sorting.jpg",
        gameInstructionImage = "img_rapid_sorting_instructions.jpg",
        sectionName = "Rapid Sorting",
        gameDescription = "Та же форма - проведите пальцем в том же направлении, другая - противоположное."
    )
    object Make10: GamesNavigationItem(
        screen = GamesScreen.Make10,
        miniatureGameImage = "ic_game_make10.jpg",
        gameInstructionImage = "img_make10_instructions.jpg",
        sectionName = "Make10",
        gameDescription = "Нажимайте на блоки так, чтобы в сумме получилось 10."
    )
    object BreakTheBlock: GamesNavigationItem(
        screen = GamesScreen.BreakTheBlock,
        miniatureGameImage = "ic_game_break_the_block.jpg",
        gameInstructionImage = "img_break_the_block_instructions.jpg",
        sectionName = "Break The Block",
        gameDescription = "Разбейте блоки и создайте очерченную форму."
    )
    object HexaChain: GamesNavigationItem(
        screen = GamesScreen.HexaChain,
        miniatureGameImage = "ic_game_hexa_chain.jpg",
        gameInstructionImage = "img_hexa_chain_instructions.jpg",
        sectionName = "Hexa Chain",
        gameDescription = "Соедините шестиугольники одного цвета. Чем длиннее цепочка, тем больше счет."
    )
    object ColorSwitch: GamesNavigationItem(
        screen = GamesScreen.ColorSwitch,
        miniatureGameImage = "ic_game_color_switch.jpg",
        gameInstructionImage = "img_color_switch_instructions.jpg",
        sectionName = "Color Switch",
        gameDescription = "Нажмите для смены цвета так, чтобы он соответствовал падающему кружку."
    )

}