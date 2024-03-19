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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.navigation.games.GamesScreenNavGraph
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.navigation.staticsClasses.rememberNavigationState
import com.kuvalin.brainstorm.presentation.screens.game.gamescreen.GameScreen
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GamesMainScreen(
    paddingValuesParent: PaddingValues,
    onCardClick: () -> Unit // Малец неудачное название (Множественное нажатие?)
) {


    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = (screenWidth / 35)

    val navigationState = rememberNavigationState()

    // Данная шляпа нужна для скрытия списка игр
    val runGameScreenState = GlobalStates.runGameScreenState.collectAsState().value

    // Можно потом заменить. Так куда лаконичнее и дополнять не придется
    val items = GamesNavigationItem::class.sealedSubclasses.mapNotNull { it.objectInstance }
    /* ########################################################################################## */


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GamesScreenNavGraph(
            navHostController = navigationState.navHostController,
            gameInitialScreenContent = {
                if (!runGameScreenState) {
                    GlobalStates.putScreenState("runGameState", false)
                    GameScreenInitialContent(
                        paddingValuesParent,
                        items,
                        dynamicFontSize,
                        navigationState
                    ) {
                        GlobalStates.putScreenState("runGameState", true)
                        onCardClick()
                    }
                }
            },
            flickMasterScreenContent = { GameScreen(navigationState) { onCardClick() } },
            additionAddictionScreenContent = { GameScreen(navigationState) { onCardClick() } },
            reflectionScreenContent = { GameScreen(navigationState) { onCardClick() } },
            pathToSafetyScreenContent = { GameScreen(navigationState) { onCardClick() } },
            rapidSortingScreenContent = { GameScreen(navigationState) { onCardClick() } },
            make10ScreenContent = { GameScreen(navigationState) { onCardClick() } },
            breakTheBlockScreenContent = { GameScreen(navigationState) { onCardClick() } },
            hexaChainScreenContent = { GameScreen(navigationState) { onCardClick() } },
            colorSwitchScreenContent = { GameScreen(navigationState) { onCardClick() } }
        )
    }

}


//region GameScreenInitialContent
@Composable
private fun GameScreenInitialContent(
    paddingValuesParent: PaddingValues,
    items: List<GamesNavigationItem>,
    dynamicFontSize: Int,
    navigationState: NavigationState,
    onCardClick: () -> Unit
) {
    // Для проигрывания звуков
    val context = LocalContext.current
    val musicScope = CoroutineScope(Dispatchers.Default)

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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .background(color = BackgroundAppColor),
            columns = GridCells
                .Adaptive(minSize = 100.dp)
        ) {
            items(items.size) { position ->
                GameCard(
                    dynamicFontSize = dynamicFontSize,
                    sectionName = items[position].sectionName,
                    miniatureGameImage = items[position].miniatureGameImage
                ) {
                    onCardClick()
                    musicScope.launch {
                        MusicPlayer(context = context).run {
                            playChoiceClick()
                            delay(3000)
                            release()
                        }
                    }
                    navigationState.navigateTo(items[position].screen.route)
                }
            }
        }
    }
}

//endregion
//region GameCard
@Composable
fun GameCard(
    dynamicFontSize: Int,
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
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10))
                .background(color = Color.White)
                .border(width = 1.dp, shape = RoundedCornerShape(10), color = Color.White)
                .then(Modifier.padding(5.dp))
        )
        Text(text = sectionName, fontSize = dynamicFontSize.sp, color = Color.DarkGray)
    }
}
//endregion


/* #################################### ВСПОМОГАТЕЛЬНЫЕ ##################################### */
/* ########################################################################################## */


