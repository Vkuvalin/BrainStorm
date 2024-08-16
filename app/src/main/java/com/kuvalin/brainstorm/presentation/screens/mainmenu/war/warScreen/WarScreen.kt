package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.navigation.mainmenu.war.WarScreenState
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.warGameResult.WarGameResult
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.warGameScreen.WarGameScreen
import com.kuvalin.brainstorm.presentation.viewmodels.main.WarViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlin.math.abs


/**
 * Здесь аналогично не буду больше морочиться и подтягивать лишнюю информацию.
 * Пусть просто остаются заглушки. Целью было разработать архитектуру и реализовать это.
 */


@Composable
fun WarScreen(
    navigationState: NavigationState
){

    /* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ #################### 🌈 ############# */
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(350){ clickNavigation = false } }
    /* ########################################################################################## */



    /* ############# 🔄 ###################### BackHandler #################### 🔄 ############## */
    var onBackButtonClick by remember { mutableStateOf(false) }
    if (onBackButtonClick){
        WarScreenState.putWarScreenState(WarScreenState.PreparingForTheGame)
        navigationState.navigateToHome()
        GlobalStates.putScreenState("runGameScreenState", false)
        onBackButtonClick = false
    }

    BackHandler {
        onBackButtonClick = true
        clickNavigation = true
    }
    /* ########################################################################################## */



    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    val component = getApplicationComponent()
    val viewModel: WarViewModel = viewModel(factory = component.getViewModelFactory())


    // ############ Получение данных из ViewModel
    // UserInfo
    val userInfo by viewModel.userInfo.collectAsState()
    val userInfoOpponent by viewModel.userInfoOpponent.collectAsState()

    // Scope
    val scopeCyanPlayer by viewModel.scopeCyanPlayer.collectAsState()
    val scopePinkPlayer by viewModel.scopePinkPlayer.collectAsState()

    val round by viewModel.round.collectAsState() // Визуальное отображение раунда
    val timer by viewModel.timer.collectAsState() // Числовое значение таймера
    val gameState by viewModel.gameState.collectAsState() // Состояние начала/конца игры
    // ########################


    // ############ Получение аргументов
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val sessionId = navBackStackEntry?.arguments?.getString("sessionId")

    // Загрузка UserInfo
    LaunchedEffect(Unit) {
        navBackStackEntry?.arguments?.getString("uid")?.let { opponentUid ->
            viewModel.loadUserInfo(opponentUid)
        }
    }
    // ########################


    // ############ Остальные
    val topBarHeight = remember { 80 } // Костыль
    val uriAvatar by remember { mutableStateOf<Uri?>(null) } // Аватар TODO

    // Отношение очков (полоска сверху)
    val ratio = remember(scopeCyanPlayer, scopePinkPlayer) {
        derivedStateOf { ((scopeCyanPlayer - scopePinkPlayer).toFloat() / 2000) }
    }
//    val ratio = ((scopeCyanPlayer - scopePinkPlayer).toFloat() / 2000) // 2000 = 0.5 = лучший вид

    // Для игр
    val items = GamesNavigationItem::class.sealedSubclasses.mapNotNull { it.objectInstance }
    val selectedGames = listOf("Flick Master", "Path To Safety", "Make10") // Пока так

    // Состояние экрана (игры, ожидание, результат)
    val warScreenState by WarScreenState.warScreenState.collectAsState()
    // ########################

    /* ########################################################################################## */



    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */

    // Логика обновления scope оппонента
    if (gameState) {
        LaunchedEffect(Unit) {
            viewModel.observeOpponentScope(sessionId!!, selectedGames[round - 2])
        }
    }

    // UI компоненты
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (warScreenState != WarScreenState.WarGameResults) {
            TopWarBar(topBarHeight, uriAvatar, scopeCyanPlayer, timer, scopePinkPlayer, ratio.value)
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //region WarScreenState
            when(warScreenState){
                WarScreenState.PreparingForTheGame -> {
                    LaunchedEffect(Unit) {
                        viewModel.updateTimer(10)
                        viewModel.updateGameState(false)
                        viewModel.resetScopeCyanPlayer(0)
                        viewModel.resetScopePinkPlayer(0)
                    }
                    PreparingForTheGame(timer, round, items, selectedGames)
                    Timer(timer = timer, viewModel)
                    if (timer == 0 && !gameState){
                        when(round){
                            1 -> {WarScreenState.putWarScreenState(WarScreenState.WarGameOne)}
                            2 -> {WarScreenState.putWarScreenState(WarScreenState.WarGameTwo)}
                            3 -> {WarScreenState.putWarScreenState(WarScreenState.WarGameResults)}
                        }
                    }
                }
                WarScreenState.WarGameOne -> {
                    LaunchedEffect(Unit) {
                        viewModel.updateRound(round + 1)
                        viewModel.updateTimer(20)
                        viewModel.updateGameState(true)
                    }

                    if (gameState){ Timer(timer = timer, viewModel) }
                    WarGameScreen(
                        viewModel = viewModel,
                        topBarHeight = topBarHeight,
                        gameName = selectedGames[0],
                        resetTime = { viewModel.updateTimer(10) },
                        putActualScope = { points ->
                            viewModel.updateScopeCyanPlayer(points)
                            viewModel.updateUserScope(sessionId!!, selectedGames[0], points)
                        }
                    ){ onBackButtonClick = true }
                }
                WarScreenState.WarGameTwo -> {
                    LaunchedEffect(Unit) {
                        viewModel.updateRound(round + 1)
                        viewModel.updateTimer(20)
                        viewModel.updateGameState(true)
                    }
                    if (gameState){ Timer(timer = timer, viewModel) }
                    WarGameScreen(
                        viewModel = viewModel,
                        topBarHeight = topBarHeight,
                        gameName = selectedGames[1],
                        resetTime = {viewModel.updateTimer(10)},
                        putActualScope = {points ->
                            viewModel.updateScopeCyanPlayer(points)
                            viewModel.updateUserScope(sessionId!!, selectedGames[1], points)
                        }
                    ){ onBackButtonClick = true }
                }
                WarScreenState.WarGameThree -> {}
                WarScreenState.WarGameResults -> {
                    if (userInfo != null && userInfoOpponent != null){ // Из-за ошибки smartCast
                        WarGameResult(
                            userInfo = userInfo!!,
                            userInfoOpponent = userInfoOpponent!!,
                            selectedGames,
                            viewModel,
                            sessionId
                        ) { onBackButtonClick = true }
                    }

                }
            }
            //endregion
        }

    }

    /* ########################################################################################## */

}


/* ############# 🟡 ################ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############# 🟡 ############### */
//region PreparingForTheGame
@Composable
private fun PreparingForTheGame(
    timer: Int,
    round: Int,
    items: List<GamesNavigationItem>,
    selectedGames: List<String>
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))
    ) {

        // Область с отображением кругового индикатора
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
        ) {
            RoundCircleIndicator(time = timer.toFloat(), round = round)
        }


        // Область с отображением строки с играми
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {

                Text(
                    text = "Games",
                    color = Color(0xFF373737),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items.forEach { game ->
                        if (game.sectionName in selectedGames) {
                            key(game.sectionName) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                ) {
                                    GameCard(
                                        gameName = game.sectionName,
                                        miniatureGameImage = game.miniatureGameImage,
                                        selected = selectedGames.indexOf(game.sectionName) == (round - 1)
                                                && timer <= 3
                                    )
                                }
                            }
                        }
                    }
                }

            }

        }

    }
}

//region RoundCircleIndicator
@Composable
private fun RoundCircleIndicator(
    time: Float,
    round: Int
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = 1f - time/10,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .background(color = Color(0xFF00BAB9)),
            strokeWidth = 10.dp,
            color = Color(0xFF373737),
        )
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFE6E6E6))
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ROUND",
                fontSize = 18.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xFF373737)
            )
            Text(
                text = "$round",
                fontSize = 40.sp,
                fontWeight = FontWeight.W400,
                color = Color(0xFF373737)
            )
        }
    }
}
//endregion
//region GameProgressIndicator
@Composable
fun GameProgressIndicator(
    ratio: Float,
    height: Int = 20
) {
    LinearProgressIndicator(
        progress = 0.5f + if (abs(ratio) >= 0.5f) 0.4f else ratio,
        color = CyanAppColor,
        trackColor = PinkAppColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
    )
    Spacer(
        modifier = Modifier
            .height(0.5.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF373737))
    )
}
//endregion
//region GameCard
@Composable
private fun GameCard(
    gameName: String,
    miniatureGameImage: String,
    selected: Boolean
) {
    val infiniteSelected = rememberInfiniteTransition(label = "")
    val sizeAnimationSelected by infiniteSelected.animateFloat(
        initialValue = 2f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 300),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        AssetImage(
            fileName = miniatureGameImage,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(10))
                .background(color = Color.White)
                .border(
                    width = if (selected) sizeAnimationSelected.dp else 1.dp,
                    shape = RoundedCornerShape(10),
                    color = if (selected) PinkAppColor else Color.White,
                )
                .then(Modifier.padding(5.dp))
        )
        Text(
            text = gameName,
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}
//endregion
//endregion
/* ########################################################################################## */



