package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.searchForWarScreen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.presentation.screens.mainmenu.main.DrawingChart
import com.kuvalin.brainstorm.presentation.viewmodels.main.WarViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor


@Composable
fun SearchForWar(
    navigationState: NavigationState
){

    /* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ #################### 🌈 ############# */
    // Блокировка анимации при нажатии на навигационные кнопки и связанные с ней блоки
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(350){ clickNavigation = false } }
    LaunchedEffect(Unit) {
        GlobalStates.putScreenState("runGameScreenState", true)
        clickNavigation = true
    }
    /* ########################################################################################## */



    /* ############# 🔄 ###################### BackHandler #################### 🔄 ############## */
    BackHandler {
        navigationState.navigateToHome()
        GlobalStates.putScreenState("runGameScreenState", false)
        clickNavigation = true
    }
    /* ########################################################################################## */



    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    //region Переменные состояния
    val component = getApplicationComponent()
    val viewModel: WarViewModel = viewModel(factory = component.getViewModelFactory())

    val userName by viewModel.userName.collectAsState()
    val opponentUserName by viewModel.opponentUserName.collectAsState()
    val waitOpponent by viewModel.waitOpponent.collectAsState()

    var uriAvatar by remember { mutableStateOf<Uri?>(null) } // Аватар
    //endregion

    // Запуск поиска игры
    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
        viewModel.findAndNavigateToGame { sessionId, opponentId ->
            navigationState.navigateToWar(sessionId, opponentId)
        }
    }

    //region Анимация scale надписи ожидания
    val infiniteWaitText = rememberInfiniteTransition(label = "")
    val sizeAnimationWaitText by infiniteWaitText.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion

    /* ########################################################################################## */


    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //region User_Cyan
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(color = CyanAppColor)
        ){
            UserInfo( uriAvatar = uriAvatar, name = userName, grade = "Bear", rank = 941)
        }
        //endregion

        //region User_Pink + Wait
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(color = if (waitOpponent) BackgroundAppColor else PinkAppColor)
        ){
            if (!waitOpponent) {
                UserInfo( uriAvatar = uriAvatar, name = opponentUserName, grade = "Cat", rank = 248)
            } else {
                Text(
                    text = "Waiting for opponent...",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.scale(sizeAnimationWaitText)
                )
            }
        }
        //endregion
    }

    //region График
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ){
        Box(modifier = Modifier.scale(1f)){ DrawingChart(workMode = if (waitOpponent) 0 else 3) }
    }
    //endregion

    /* ########################################################################################## */


}


