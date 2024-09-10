package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.warGameScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.navigation.mainmenu.war.WarScreenState
import com.kuvalin.brainstorm.presentation.screens.game.games.AdditionAddiction
import com.kuvalin.brainstorm.presentation.screens.game.games.BreakTheBlock
import com.kuvalin.brainstorm.presentation.screens.game.games.ColorSwitch
import com.kuvalin.brainstorm.presentation.screens.game.games.FlickMaster
import com.kuvalin.brainstorm.presentation.screens.game.games.HexaChain
import com.kuvalin.brainstorm.presentation.screens.game.games.Make10
import com.kuvalin.brainstorm.presentation.screens.game.games.PathToSafety
import com.kuvalin.brainstorm.presentation.screens.game.games.RapidSorting
import com.kuvalin.brainstorm.presentation.screens.game.games.Reflection
import com.kuvalin.brainstorm.presentation.viewmodels.main.WarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt




@Composable
fun WarGameScreen(
    viewModel: WarViewModel,
    topBarHeight: Int,
    gameName: String,
    putActualScope: (gameScope: Int) -> Unit, // TODO Где-то в процессе двойная рекомпозиция
    resetTime: () -> Unit,
    onBackButtonClick: () -> Unit
) {

    val scope = CoroutineScope(Dispatchers.IO)
    var userUid by remember { mutableStateOf("") }
    LaunchedEffect(Unit) { userUid = viewModel.getUserUid.invoke() }// TODO забыл, но ща не буду отвлекаться


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
        GamesNavigationItem.AdditionAddiction.sectionName -> { AdditionAddiction() } // Не реализована
        GamesNavigationItem.Reflection.sectionName -> { Reflection() }               // Не реализована
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
        GamesNavigationItem.RapidSorting.sectionName -> { RapidSorting() }    // Не реализована
        GamesNavigationItem.Make10.sectionName -> { Make10() }                // Не реализована
        GamesNavigationItem.BreakTheBlock.sectionName -> { BreakTheBlock() }  // Не реализована
        GamesNavigationItem.HexaChain.sectionName -> { HexaChain() }     // Не реализована
        GamesNavigationItem.ColorSwitch.sectionName -> { ColorSwitch() } // Не реализована
    }
}