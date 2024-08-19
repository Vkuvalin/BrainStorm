package com.kuvalin.brainstorm.presentation.screens.game.gamescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.navigation.staticsClasses.GamesScreen
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.presentation.screens.game.games.AdditionAddiction
import com.kuvalin.brainstorm.presentation.screens.game.games.BreakTheBlock
import com.kuvalin.brainstorm.presentation.screens.game.games.ColorSwitch
import com.kuvalin.brainstorm.presentation.screens.game.games.FlickMaster
import com.kuvalin.brainstorm.presentation.screens.game.games.HexaChain
import com.kuvalin.brainstorm.presentation.screens.game.games.Make10
import com.kuvalin.brainstorm.presentation.screens.game.games.PathToSafety
import com.kuvalin.brainstorm.presentation.screens.game.games.RapidSorting
import com.kuvalin.brainstorm.presentation.screens.game.games.Reflection
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun GameScreen( navigationState: NavigationState ){

    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(350){ clickNavigation = false } }

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    lateinit var gameName: String
    lateinit var gameInstructionImage: String
    lateinit var gameDescription: String
    lateinit var miniatureGameImage: String

    val topBarHeight = remember { 50 } // Костыль
    val context = LocalContext.current

    var correct by remember { mutableIntStateOf(0) }
    var incorrect by remember { mutableIntStateOf(0) }
    var scope by remember { mutableIntStateOf(0) }
    var accuracy by remember { mutableFloatStateOf(0f) }

    // Подключаемся к объекту навигации, получаем список GameItems и вытягиваем нужные значения
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val listGamesItems = GamesNavigationItem::class.sealedSubclasses.mapNotNull {it.objectInstance}
    var loadFinish by remember { mutableStateOf(false) }
    listGamesItems.forEach { item ->
        val selected = navBackStackEntry?.destination?.hierarchy?.any {
            it.route == item.screen.route
        } ?: false

        if (selected){
            gameName = item.sectionName
            gameDescription = item.gameDescription
            miniatureGameImage = item.miniatureGameImage
            gameInstructionImage = item.gameInstructionImage
            loadFinish = true
        }
    }

    // Течение игры
    var startGameState by remember { mutableStateOf(false) }
    var endGameState by remember { mutableStateOf(false) }

    // Меняет стейт для скрытия/открытия TopAppBar 1 и 2
    val gameOut: () -> Unit = {
        MusicPlayer(context = context).playChoiceClick()
        GlobalStates.putScreenState("runGameScreenState", false)
    }
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        modifier = Modifier.fillMaxSize().background(color = BackgroundAppColor)
    ) {
        GameTopBar(
            topBarHeight = topBarHeight,
            onBackButtonClick = {
                loadFinish = false
                endGameState = false
                clickNavigation = true
                gameOut()
                navigationState.navHostController.popBackStack()
            }
        )
        Box(
            modifier = Modifier.fillMaxSize().background(color = BackgroundAppColor)
        ) {

            if (loadFinish){
                if(!startGameState) {

                    GameDialogAndStart(
                        gameName = gameName,
                        gameInstructionImage = gameInstructionImage,
                        gameDescription = gameDescription,
                        onDismissRequest = {
                            loadFinish = false
                            gameOut()
                            navigationState.navigateTo(GamesScreen.GameInitial.route)
                        }
                    ){startGameState = true}

                }else{

                    when(endGameState){
                        false -> {
                            when(gameName){
                                //region GamesNavigationItem.FlickMaster.sectionName
                                GamesNavigationItem.FlickMaster.sectionName -> {
                                    FlickMaster(
                                        onBackButtonClick = {
                                            loadFinish = false
                                            gameOut()
                                            navigationState.navigateTo(GamesScreen.GameInitial.route)
                                        },
                                        putActualScope = {}
                                    ){
                                        countCorrect, countIncorrect, gameScope, internalAccuracy ->
                                        correct = countCorrect
                                        incorrect = countIncorrect
                                        accuracy = internalAccuracy
                                        scope = gameScope
                                        endGameState = true
                                    }
                                }
                                //endregion
                                GamesNavigationItem.AdditionAddiction.sectionName -> {AdditionAddiction()} // Не буду реализовывать
                                GamesNavigationItem.Reflection.sectionName -> {Reflection()}               // Не буду реализовывать
                                //region GamesNavigationItem.PathToSafety.sectionName
                                GamesNavigationItem.PathToSafety.sectionName -> {
                                    PathToSafety(
                                        topBarHeight = topBarHeight,
                                        onBackButtonClick = {
                                            loadFinish = false
                                            gameOut()
                                            navigationState.navigateTo(GamesScreen.GameInitial.route)
                                        },
                                        putActualScope = {}
                                    )
                                    {countCorrect, countIncorrect,gameScope , internalAccuracy ->
                                        correct = countCorrect
                                        incorrect = countIncorrect
                                        accuracy = internalAccuracy
                                        scope = gameScope
                                        endGameState = true
                                    }
                                }
                                //endregion
                                GamesNavigationItem.RapidSorting.sectionName -> {RapidSorting()}    // Не буду реализовывать
                                GamesNavigationItem.Make10.sectionName -> {Make10()}                // Не буду реализовывать
                                GamesNavigationItem.BreakTheBlock.sectionName -> {BreakTheBlock()}  // Не буду реализовывать
                                GamesNavigationItem.HexaChain.sectionName -> {HexaChain()}          // Не буду реализовывать
                                GamesNavigationItem.ColorSwitch.sectionName -> {ColorSwitch()}      // Не буду реализовывать
                            }
                        }
                        true -> {
                            //region GameResults
                            GameResults(
                                gameName = gameName,
                                miniatureGameImage = miniatureGameImage,
                                correct = correct,
                                incorrect = incorrect,
                                scope = scope,
                                accuracy = accuracy,
                                onBackButtonClick = {
                                    loadFinish = false
                                    gameOut()
                                    navigationState.navigateTo(GamesScreen.GameInitial.route)
                                },
                                onRetryButtonClick = { endGameState = false }
                            )
                            //endregion
                        }
                    }

                }
            }

        }
    }
    //endregion ################################################################################## */

}

