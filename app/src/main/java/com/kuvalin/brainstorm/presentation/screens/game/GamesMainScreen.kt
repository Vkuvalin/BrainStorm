package com.kuvalin.brainstorm.presentation.screens.game

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.navigation.games.GamesScreenNavGraph
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.navigation.staticsClasses.rememberNavigationState
import com.kuvalin.brainstorm.presentation.screens.game.gamescreen.GameScreen
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameMainScreenViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GamesMainScreen( paddingValuesParent: PaddingValues ) {

    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    val navigationState = rememberNavigationState()
    val viewModel: GameMainScreenViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())

    // Стейт для скрытия/открытия TopAppBar 1 и 2
    val runGameScreenState by GlobalStates.runGameScreenState.collectAsState()

    // Список объектов с информацией об играх
    val items = remember { GamesNavigationItem::class.sealedSubclasses.mapNotNull { it.objectInstance } }

    /* ########################################################################################## */


    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */
    Box(
        modifier = Modifier.fillMaxSize().background(color = BackgroundAppColor)
    ) {
        GamesScreenNavGraph(
            navHostController = navigationState.navHostController,
            gameInitialScreenContent = {
                if (!runGameScreenState) {
                    GlobalStates.putScreenState("runGameScreenState", false)
                    GameScreenInitialContent(viewModel, paddingValuesParent, items, navigationState)
                }
            },
            flickMasterScreenContent =       { GameScreen(navigationState) },
            additionAddictionScreenContent = { GameScreen(navigationState) },
            reflectionScreenContent =        { GameScreen(navigationState) },
            pathToSafetyScreenContent =      { GameScreen(navigationState) },
            rapidSortingScreenContent =      { GameScreen(navigationState) },
            make10ScreenContent =            { GameScreen(navigationState) },
            breakTheBlockScreenContent =     { GameScreen(navigationState) },
            hexaChainScreenContent =         { GameScreen(navigationState) },
            colorSwitchScreenContent =       { GameScreen(navigationState) }
        )
    }
    /* ########################################################################################## */

}

/* ############# 🟡 ################ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############# 🟡 ############### */
//region GameScreenInitialContent
@Composable
private fun GameScreenInitialContent(
    viewModel: GameMainScreenViewModel,
    paddingValuesParent: PaddingValues,
    items: List<GamesNavigationItem>,
    navigationState: NavigationState
) {


    GlobalStates.AnimLoadState(400){}
    val animLoadState by GlobalStates.animLoadState.collectAsState()    // Ждем прогрузки анимации

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .padding(top = paddingValuesParent.calculateTopPadding())
            .background(color = BackgroundAppColor)
            .fillMaxSize()
            .then(Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
    ) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize().padding(top = 10.dp).background(color = BackgroundAppColor),
            columns = GridCells.Adaptive(minSize = 100.dp)
        ) {
            items(items.size) { position ->
                GameCard(
                    dynamicFontSize = DynamicFontSize(LocalConfiguration.current.screenWidthDp, 11f),
                    sectionName = items[position].sectionName,
                    miniatureGameImage = items[position].miniatureGameImage
                ) {
                    if (animLoadState) {
                        GlobalStates.putScreenState("runGameScreenState", true)
                        MusicPlayer(context = context).playChoiceClick()
                        viewModel.navigateWithDelay(navigationState, items[position].screen.route)
                    }
                }
            }
        }
    }
}

//endregion
//region GameCard
@Composable
fun GameCard(
    dynamicFontSize: TextUnit,
    sectionName: String,
    miniatureGameImage: String,
    onCardClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .noRippleClickable { onCardClick() }
    ) {
        AssetImage(
            fileName = miniatureGameImage,
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(10)).background(color = Color.White)
                .border(width = 1.dp, shape = RoundedCornerShape(10), color = Color.White)
                .then(Modifier.padding(5.dp))
        )
        Text(text = sectionName, fontSize = dynamicFontSize, color = Color.DarkGray)
    }
}
//endregion
/* ########################################################################################## */

