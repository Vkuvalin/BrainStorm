package com.kuvalin.brainstorm.presentation.screens.mainmenu.war

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.navigation.mainmenu.war.WarScreenState
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
import com.kuvalin.brainstorm.presentation.viewmodels.WarViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.roundToInt


/**
 * Здесь аналогично не буду больше морочиться и подтягивать лишнюю информацию.
 * Пусть просто остаются заглушки. Целью было разработать архитектуру и реализовать это.
 */


@Composable
fun WarScreen(
    navigationState: NavigationState
){

    BackHandler {
        navigationState.navigateToHome()
        GlobalStates.putScreenState("runGameScreenState", false)
    }

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Получаем название игровой сессии
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val sessionId = navBackStackEntry?.arguments?.getString("sessionId")

    val component = getApplicationComponent()
    val viewModel: WarViewModel = viewModel(factory = component.getViewModelFactory())



    val topBarHeight = 80 // Костыль

    // Аватар // TODO - потом
    val uriAvatar by remember { mutableStateOf<Uri?>(null) }
    // Нужно будет тянуть с прошлого экрана. Вообще мне тут же в конце вообще весь User понадобится

    var scopeCyanPlayer by remember { mutableIntStateOf(0) }
    var scopePinkPlayer by remember { mutableIntStateOf(0) }
    val ratio = ((scopeCyanPlayer - scopePinkPlayer).toFloat() / 2000) // 2000 = 0.5 = лучший вид

    var round by remember { mutableIntStateOf(1) }
    val timer = remember { mutableIntStateOf(11) }

    var gameState by remember { mutableStateOf(false) }

    val items = GamesNavigationItem::class.sealedSubclasses.mapNotNull { it.objectInstance }

    // Эту хрень буду тащить из инета, но пока не реализовывал
    val selectedGames = listOf("Flick Master", "Path To Safety", "Make10")


    val warScreenState = WarScreenState.warScreenState.collectAsState()
    /* ########################################################################################## */


    // Динамическая функция отправки актуального scope
    fun updateUserScope(points: Int){
        val scope = CoroutineScope(Dispatchers.IO)
        scopeCyanPlayer += points
        if (scopeCyanPlayer < 0){ scopeCyanPlayer = 0 }

        scope.launch {
            viewModel.updateUserScope.invoke(
                sessionId = sessionId!!,
                gameName = selectedGames[round-2],
                scope = scopeCyanPlayer
            )
        }
    }

    // Данный поток будет на постоянке обновлять Scope оппонента.
    if (gameState){
        LaunchedEffect(Unit) {
            viewModel.getActualOpponentScope
                .invoke(sessionId = sessionId!!, gameName = selectedGames[round-2])
                .collect { scopePinkPlayer = it }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (warScreenState.value != WarScreenState.WarGameResults) {
            TopWarBar(topBarHeight, uriAvatar, scopeCyanPlayer, timer, scopePinkPlayer, ratio)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //region WarScreenState
            when(warScreenState.value){
                WarScreenState.PreparingForTheGame -> {
                    LaunchedEffect(Unit) {
                        timer.intValue = 11
                        gameState = false
                        scopeCyanPlayer = 0
                        scopePinkPlayer = 0
                    }
                    PreparingForTheGame(timer.intValue, round, items, selectedGames)
                    Timer(timer = timer)
                    if (timer.intValue == 0 && !gameState){
                        when(round){
                            1 -> {WarScreenState.putWarScreenState(WarScreenState.WarGameOne)}
                            2 -> {WarScreenState.putWarScreenState(WarScreenState.WarGameTwo)}
                            3 -> {WarScreenState.putWarScreenState(WarScreenState.WarGameResults)}
                        }
                    }
                }
                WarScreenState.WarGameOne -> {
                    LaunchedEffect(Unit) {
                        round++
                        timer.intValue = 20
                        gameState = true
                    }
                    if (gameState){ Timer(timer = timer) }


                    WarGameScreen(
                        viewModel = viewModel,
                        topBarHeight = topBarHeight,
                        gameName = selectedGames[0],
                        resetTime = {timer.intValue = 11},
                        putActualScope = {
                            scopeCyanPlayer += it
                            updateUserScope(it)
                        }
                    ){
                        navigationState.navigateToHome()
                        GlobalStates.putScreenState("runGameScreenState", false)
                    }
                }
                WarScreenState.WarGameTwo -> {
                    LaunchedEffect(Unit) {
                        round++
                        timer.intValue = 20
                        gameState = true
                    }
                    if (gameState){ Timer(timer = timer) }
                    WarGameScreen(
                        viewModel = viewModel,
                        topBarHeight = topBarHeight,
                        gameName = selectedGames[1],
                        resetTime = {timer.intValue = 11},
                        putActualScope = {
                            scopeCyanPlayer += it
                            updateUserScope(it)
                        }
                    ){
                        navigationState.navigateToHome()
                        GlobalStates.putScreenState("runGameScreenState", false)
                    }
                }
                WarScreenState.WarGameThree -> {}
                WarScreenState.WarGameResults -> {
                    WarGameResult(
                        selectedGames,
                        viewModel,
                        sessionId
                    ) {
                        navigationState.navigateToHome()
                        GlobalStates.putScreenState("runGameScreenState", false)
                    }
                }
            }
            //endregion
        }

    }

}


//region Timer
@Composable
private fun Timer(
    timer: MutableIntState
){
    LaunchedEffect(Unit) {
        while (timer.intValue > 0){
            timer.intValue -= 1
            delay(1000)
        }
    }
}
//endregion

//region Top War Bar
@Composable
private fun TopWarBar(
    topBarHeight: Int,
    uriAvatar: Uri?,
    scopeCyanPlayer: Int,
    timer: MutableIntState,
    scopePinkPlayer: Int,
    ratio: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight.dp)
            .background(color = Color(0xFF373737))
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 7.dp)
        ) {
            Avatar(uriAvatar = uriAvatar, color = CyanAppColor)
            Scope(scope = scopeCyanPlayer, style = "left")

            Time(time = timer.intValue)

            Scope(scope = scopePinkPlayer, style = "right")
            Avatar(uriAvatar = uriAvatar, color = PinkAppColor)
        }

    }

    GameProgressIndicator(ratio = ratio)
}
//region Time
@Composable
private fun Time(
    time: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = "TIME",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f),
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
        Text(
            text = if (time < 10) "0$time" else "$time",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(5f),
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}
//endregion
//region Scope
@Composable
private fun Scope(
    scope: Int,
    style: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxHeight()
            .let {
                when (style) {
                    "left" -> it.padding(bottom = 5.dp, end = 20.dp)
                    "right" -> it.padding(bottom = 5.dp, start = 20.dp)
                    else -> it.padding(bottom = 5.dp)
                }
            }
    ) {
        Text(
            text = "SCORE",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
        )
        Text(
            text = "$scope",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}
//endregion
//region Avatar
@Composable
private fun Avatar(
    uriAvatar: Uri?,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color.White)
                .border(width = 7.dp, color = color, shape = CircleShape)
            ,
            contentAlignment = Alignment.Center
        ) {
            if (uriAvatar == null) {
                AssetImage(fileName = "av_user.png")
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = uriAvatar),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }
    }
}
//endregion
//endregion
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


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
        ) {
            RoundCircleIndicator(time = timer.toFloat(), round = round)
        }



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
                    for (game in items) {
                        if (game.sectionName in selectedGames) {
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
private fun GameProgressIndicator(
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
//region WarGameScreen
@Composable
private fun WarGameScreen(
    viewModel: WarViewModel,
    topBarHeight: Int,
    gameName: String,
    putActualScope: (gameScope: Int) -> Unit, // TODO Где-то в процессе двойная рекомпозиция
    resetTime: () -> Unit,
    onBackButtonClick: () -> Unit
) {

    val scope = CoroutineScope(Dispatchers.IO)
    val userUid = Firebase.auth.uid ?: "zero_user_uid"

    when(gameName){
        //region GamesNavigationItem.FlickMaster.sectionName
        GamesNavigationItem.FlickMaster.sectionName -> {
            FlickMaster(
                onBackButtonClick = {onBackButtonClick()},
                putActualScope = { gameScope ->  putActualScope(gameScope) }
            ) {countCorrect, countIncorrect, gameScope, internalAccuracy ->
                scope.launch {

                    // GameResult
                    val finalScope = if(gameScope == 0) ((countCorrect*53)-(countIncorrect*22))
                    else (gameScope*53)-(gameScope*22)
                    val finalAccuracy = (internalAccuracy * 1000).roundToInt() / 10.0f

                    viewModel.addGameResult.invoke(
                        GameResult(
                            uid = userUid,
                            gameName = gameName,
                            scope = finalScope,
                            accuracy = finalAccuracy
                        )
                    )
                }
                resetTime()
                WarScreenState.putWarScreenState(WarScreenState.PreparingForTheGame)
            }
        }
        //endregion
        GamesNavigationItem.AdditionAddiction.sectionName -> {AdditionAddiction()} // Не реализована
        GamesNavigationItem.Reflection.sectionName -> {Reflection()}               // Не реализована
        //region GamesNavigationItem.PathToSafety.sectionName
        GamesNavigationItem.PathToSafety.sectionName -> {
            PathToSafety(
                topBarHeight = topBarHeight,
                onBackButtonClick = { onBackButtonClick() },
                putActualScope = { gameScope ->  putActualScope(gameScope) }
            )
            {countCorrect, countIncorrect, gameScope, internalAccuracy ->
                scope.launch {
                    val finalScope = if(gameScope == 0) ((countCorrect*53)-(countIncorrect*22))
                    else (gameScope*53)-(gameScope*22)
                    val finalAccuracy = (internalAccuracy * 1000).roundToInt() / 10.0f

                    viewModel.addGameResult.invoke(
                        GameResult(
                            uid = userUid,
                            gameName = gameName,
                            scope = finalScope,
                            accuracy = finalAccuracy
                        )
                    )

                    // TODO Сделать addWarGameResult - аналогично со статистикой
                    // Новые поля: win: Boolean, gameName уже не важен

                }
                resetTime()
                WarScreenState.putWarScreenState(WarScreenState.PreparingForTheGame)
            }
        }
        //endregion
        GamesNavigationItem.RapidSorting.sectionName -> {RapidSorting()}    // Не реализована
        GamesNavigationItem.Make10.sectionName -> {Make10()}                // Не реализована
        GamesNavigationItem.BreakTheBlock.sectionName -> {BreakTheBlock()}  // Не реализована
        GamesNavigationItem.HexaChain.sectionName -> {HexaChain()}     // Не реализована
        GamesNavigationItem.ColorSwitch.sectionName -> {ColorSwitch()} // Не реализована
    }
}
//endregion
//region WarGameResult
@Composable
fun WarGameResult(
    selectedGames: List<String>,
    viewModel: WarViewModel,
    sessionId: String?,
    onBackButtonClick: () -> Unit
) {
    val scope = CoroutineScope(Dispatchers.IO)
    val context = LocalContext.current

    val uriAvatar by remember { mutableStateOf<Uri?>(null) }
    val userUid = Firebase.auth.uid ?: "zero_user_uid"

    val localDensity = LocalDensity.current
    var parentWidth by remember { mutableIntStateOf(0) }

    var firstGameCyanScope by remember { mutableIntStateOf(0) }
    var secondGameCyanScope by remember { mutableIntStateOf(0) }
    var thirdGameCyanScope by remember { mutableIntStateOf(0) }
    var totalCyan by remember { mutableIntStateOf(0) }

    var firstGamePinkScope by remember { mutableIntStateOf(0) }
    var secondGamePinkScope by remember { mutableIntStateOf(0) }
    var thirdGamePinkScope by remember { mutableIntStateOf(0) }
    var totalPink by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        if (sessionId != null) {
            firstGameCyanScope = viewModel.getScopeFromWarGame.invoke(sessionId, selectedGames[0], type = "user")
            firstGamePinkScope = viewModel.getScopeFromWarGame.invoke(sessionId, selectedGames[0], type = "")

            secondGameCyanScope = viewModel.getScopeFromWarGame.invoke(sessionId, selectedGames[1], type = "user")
            secondGamePinkScope = viewModel.getScopeFromWarGame.invoke(sessionId, selectedGames[1], type = "")

            thirdGameCyanScope = viewModel.getScopeFromWarGame.invoke(sessionId, selectedGames[2], type = "user")
            thirdGamePinkScope = viewModel.getScopeFromWarGame.invoke(sessionId, selectedGames[2], type = "")

            totalCyan = firstGameCyanScope + secondGameCyanScope + thirdGameCyanScope
            totalPink = firstGamePinkScope + secondGamePinkScope + thirdGamePinkScope


            viewModel.addWarResult.invoke(
                WarResult(
                    uid = userUid,
                    scope = totalCyan,
                    result = if (totalCyan > totalPink) "win"
                    else if (totalCyan == totalPink) "draw" else "loss"
                )
            )

        }
    }


    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        //region Users Avatars
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            //region CyanUser
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .onGloballyPositioned { coordinates ->
                        parentWidth = with(localDensity) {
                            coordinates.size.width.toDp().value.toInt()
                        }
                    }
                ,
            ) {
                AssetImage(fileName = if (totalCyan > totalPink) "winner.png"
                else if (totalCyan == totalPink) "winner.png" else "loser.png")
                Avatar(
                    uriAvatar = uriAvatar,
                    color = CyanAppColor,
                    modifier = Modifier
                        .scale(0.6f)
                        .offset(y = (-parentWidth / 5).dp)
                )
                AssetImage(
                    fileName = "ic_profile_russia.png", // TODO User Country
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (parentWidth / 5).dp, y = (parentWidth / 10).dp)
                )
            }
            //endregion
            Text(text = "VS", fontSize = 30.sp, color = Color(0xFF373737))
            //region PinkUser
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                ,
            ) {
                AssetImage(fileName = if (totalCyan < totalPink) "winner.png"
                else if (totalCyan == totalPink) "winner.png" else "loser.png")
                Avatar(
                    uriAvatar = uriAvatar,
                    color = PinkAppColor,
                    modifier = Modifier
                        .scale(0.6f)
                        .offset(y = (-parentWidth / 5).dp)
                )
                AssetImage(
                    fileName = "ic_profile_russia.png", // TODO User Country
                    modifier = Modifier
                        .size(40.dp)
                        .offset(x = (parentWidth / 5).dp, y = (parentWidth / 10).dp)
                )
            }
            //endregion
        }
        //endregion
        //region RoundStatistics
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            RoundStatistics(
                visualType = 1,
                roundNumber = "Round1",
                gameName = selectedGames[0],
                cyanUserScore = firstGameCyanScope,
                pinkUserScore = firstGamePinkScope
            )

            RoundStatistics(
                roundNumber = "Round2",
                gameName = selectedGames[1],
                cyanUserScore = secondGameCyanScope,
                pinkUserScore = secondGamePinkScope
            )

            RoundStatistics(
                roundNumber = "Round3",
                gameName = selectedGames[2],
                cyanUserScore = thirdGameCyanScope,
                pinkUserScore = thirdGamePinkScope
            )

            RoundStatistics(
                visualType = 2,
                roundNumber = "Total",
                cyanUserScore = totalCyan,
                pinkUserScore = totalPink
            )

        }
        //endregion

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            WarScreenButton(type = "Home"){
                onBackButtonClick()
            }
            WarScreenButton(type = "Add"){
                if (sessionId != null) {
                    scope.launch {
                        viewModel.addFriendInGame.invoke(sessionId)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Запрос был отправлен", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

}

//region RoundStatistics
@Composable
private fun RoundStatistics(
    visualType: Int = 0,
    roundNumber: String,
    gameName: String = "",
    cyanUserScore: Int,
    pinkUserScore: Int
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (visualType == 1) {
                Text(
                    text = "SCORE",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = "$cyanUserScore",
                color = Color.Black,
                fontSize = if (visualType != 2) 18.sp else 22.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$roundNumber",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                )
                Text(
                    text = "$gameName",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Row(
                modifier = Modifier
            ) {
                GameProgressIndicator(
                    ratio = ((cyanUserScore - pinkUserScore).toFloat() / 2000),
                    height = if (visualType == 2) 30 else 20
                )
            }

        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (visualType == 1) {
                Text(
                    text = "SCORE",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = "$pinkUserScore",
                color = Color.Black,
                fontSize = if (visualType != 2) 18.sp else 22.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            )
        }

    }
}
//endregion
//endregion
//region WarScreenButton
@Composable
private fun WarScreenButton(
    type: String,
    onPressButton: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(14))
            .background(color = if (type == "Home") Color(0xFF373737) else CyanAppColor)
            .border(
                width = 1.dp,
                color = if (type == "Home") Color(0xFF373737) else CyanAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = type,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
//endregion









