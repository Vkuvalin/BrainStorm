package com.kuvalin.brainstorm.presentation.screens.game.gamescreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.OrangeAppColor


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GameDialogAndStart(
    gameName: String,
    gameInstructionImage: String,
    gameDescription: String,
    onDismissRequest: () -> Unit,
    onStartButtonClick: () -> Unit
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    //region Анимация появления диалога (если меняю, то также изменить в GamesScreenNavGraph)
    var visibleState by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visibleState) 1f else 0.1f,
        animationSpec = tween(durationMillis = 550), label = ""
    )
    val alphaBackground by animateFloatAsState(
        targetValue = if (visibleState) 0.6f else 0.1f,
        animationSpec = tween(durationMillis = 400), label = ""
    )
    LaunchedEffect(Unit) { visibleState = true }
    //endregion

    // Ждем прогрузки анимации (скорость анимации также в GamesScreenNavGraph)
    GlobalStates.AnimLoadState(450){}
    val animLoadState by GlobalStates.animLoadState.collectAsState()

    // Проигрывание музыки
    val context = rememberUpdatedState(LocalContext.current)

    // Получаем нужные размеры экрана
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Dialog(
        onDismissRequest = {
            if (animLoadState) { onDismissRequest() }
        },
        content = {
            (LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(alphaBackground) // TODO ВАЖНАЯ штука!
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(alpha)
            ) {

                //region How To Play
                Text(
                    text = "How To Play",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                //endregion

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(screenWidth.dp)
                        .clip(RoundedCornerShape(3))
                        .background(color = BackgroundAppColor)
                ) {

                    Text(
                        text = gameName,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    AssetImage(
                        fileName = gameInstructionImage,
                        modifier = Modifier.weight(2f).fillMaxSize()
                    )

                    Text(
                        text = gameDescription,
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.weight(1f).padding(horizontal = 25.dp).padding(top = 5.dp),
                        textAlign = TextAlign.Center
                    )

                }

                //region START
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(14))
                        .background(color = OrangeAppColor)
                        .border(
                            width = 1.dp,
                            color = OrangeAppColor,
                            shape = RoundedCornerShape(14)
                        )
                        .noRippleClickable {
                            MusicPlayer(context.value).playChoiceStartGame()
                            onStartButtonClick()
                        }
                ) {
                    Text(
                        text = "START",
                        fontSize = 24.sp,
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    )
                }
                //endregion
            }

        },
    )

    //endregion ################################################################################## */
}