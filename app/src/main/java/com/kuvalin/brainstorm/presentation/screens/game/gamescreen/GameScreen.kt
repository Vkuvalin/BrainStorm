package com.kuvalin.brainstorm.presentation.screens.game.gamescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.ANIMATION_DURATION_350
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
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameScreenViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun GameScreen( navigationState: NavigationState ){

    // Блокировка анимации
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(ANIMATION_DURATION_350){ clickNavigation = false } }

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    val topBarHeight = remember { 50 } // Костыль
    val context = rememberUpdatedState(LocalContext.current)


    // ############# ViewModel
    val viewModel: GameScreenViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
    // Вводные игровые данные
    val gameName by viewModel.gameName.collectAsState()
    val gameInstructionImage by viewModel.gameInstructionImage.collectAsState()
    val gameDescription by viewModel.gameDescription.collectAsState()

    // Течение игры
    val startGameState by viewModel.startGameState.collectAsState()
    val endGameState by viewModel.endGameState.collectAsState()
    // ##########################

    // Подключаемся к объекту навигации, получаем список GameItems и вытягиваем нужные значения
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val listGamesItems = GamesNavigationItem::class.sealedSubclasses.mapNotNull {it.objectInstance}
    val loadFinish by viewModel.loadFinish.collectAsState()
    listGamesItems.forEach { item ->
        val selected = navBackStackEntry?.destination?.hierarchy?.any {
            it.route == item.screen.route
        } ?: false

        if (selected){
            viewModel.updateGameData(
                item.sectionName,
                item.gameDescription,
                item.miniatureGameImage,
                item.gameInstructionImage
            )
        }
    }


    // Меняет стейт для скрытия/открытия TopAppBar 1 и 2
    val gameOut: () -> Unit = {
        viewModel.updateLoadFinish(false)
        viewModel.updateEndGameState(false)
        viewModel.updateStartGameState(false)
        clickNavigation = true
        MusicPlayer(context = context.value).playChoiceClick()
        GlobalStates.putScreenState("runGameScreenState", false)
        navigationState.navigateTo(GamesScreen.GameInitial.route)
    }
    //endregion ################################################################################# */
    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor)
    ) {
        GameTopBar(
            topBarHeight = topBarHeight,
            onBackButtonClick = { gameOut() }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundAppColor)
        ) {

            if (loadFinish){
                if(!startGameState) {

                    GameDialogAndStart(
                        gameName = gameName,
                        gameInstructionImage = gameInstructionImage,
                        gameDescription = gameDescription,
                        onDismissRequest = { gameOut() }
                    ){viewModel.updateStartGameState(true)}

                }else{

                    when(endGameState){
                        false -> {
                            when(gameName){
                                //region GamesNavigationItem.FlickMaster.sectionName
                                GamesNavigationItem.FlickMaster.sectionName -> {
                                    FlickMaster(
                                        onBackButtonClick = { gameOut() },
                                        putActualScope = {}
                                    ){
                                        countCorrect, countIncorrect, gameScope, internalAccuracy ->
                                        viewModel.updateGameResult(countCorrect, countIncorrect,
                                            internalAccuracy, gameScope)
                                        viewModel.updateEndGameState(true)
                                    }
                                }
                                //endregion
                                GamesNavigationItem.AdditionAddiction.sectionName -> {AdditionAddiction()} // Не буду реализовывать
                                GamesNavigationItem.Reflection.sectionName -> {Reflection()}               // Не буду реализовывать
                                //region GamesNavigationItem.PathToSafety.sectionName
                                GamesNavigationItem.PathToSafety.sectionName -> {
                                    PathToSafety(
                                        topBarHeight = topBarHeight,
                                        onBackButtonClick = { gameOut() },
                                        putActualScope = {}
                                    )
                                    {countCorrect, countIncorrect,gameScope , internalAccuracy ->
                                        viewModel.updateGameResult(countCorrect, countIncorrect,
                                            internalAccuracy, gameScope)
                                        viewModel.updateEndGameState(true)
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
                                viewModel = viewModel,
                                onBackButtonClick = { gameOut() },
                                onRetryButtonClick = { viewModel.updateEndGameState(false) }
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

