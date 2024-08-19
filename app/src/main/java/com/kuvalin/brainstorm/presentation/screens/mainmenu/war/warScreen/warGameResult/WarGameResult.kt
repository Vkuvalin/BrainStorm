package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.warGameResult

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.Avatar
import com.kuvalin.brainstorm.presentation.viewmodels.main.WarViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.LinearTrackColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@Composable
fun WarGameResult(
    userInfo: UserInfo,
    userInfoOpponent: UserInfo,
    selectedGames: List<String>,
    viewModel: WarViewModel,
    sessionId: String?,
    onBackButtonClick: () -> Unit
) {

    Log.d("DEBUG-1", "-------------- ${userInfo.name} -------------- userInfo")
    Log.d("DEBUG-1", "-------------- ${userInfoOpponent.name} -------------- userInfoOpponent")

    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */
    val scope = CoroutineScope(Dispatchers.IO)
    val context = LocalContext.current

    val uriAvatar by remember { mutableStateOf<Uri?>(null) }

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

    var endOfCalculations by remember { mutableStateOf(false) }
    /* ########################################################################################## */


    /* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ #################### 🌈 ############# */
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

            endOfCalculations = true

            viewModel.addWarResult.invoke(
                WarResult(
                    uid = userInfo.uid,
                    scope = totalCyan,
                    result = if (totalCyan > totalPink) "win"
                    else if (totalCyan == totalPink) "draw" else "loss"
                )
            )

        }
    }
    /* ########################################################################################## */



    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xE6E6E6E6))
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            parentWidth = with(localDensity) {
                                coordinates.size.width.toDp().value.toInt()
                            }
                        }
                    ,
                ) {
                    if (endOfCalculations) {
                        AssetImage(fileName = if (totalCyan > totalPink) "winner.png"
                        else if (totalCyan == totalPink) "winner.png" else "loser.png")
                    }
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
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "${userInfo.name}", fontSize = 24.sp, fontWeight = FontWeight.W400, color = CyanAppColor)
            }

            //endregion
            Text(text = "VS", fontSize = 30.sp, color = LinearTrackColor)
            //region PinkUser
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    if (endOfCalculations) {
                        AssetImage(fileName = if (totalCyan < totalPink) "winner.png"
                        else if (totalCyan == totalPink) "winner.png" else "loser.png")
                    }
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
                Text(text = "${userInfoOpponent.name}", fontSize = 24.sp, fontWeight = FontWeight.W400, color = PinkAppColor)
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
            WarScreenButton(type = "Home"){ onBackButtonClick() }
            WarScreenButton(type = "Add"){
                if (sessionId != null) {
                    scope.launch {
                        viewModel.addFriendInGame.invoke(sessionId) // TODO вытащить сюда UID
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Запрос был отправлен", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }
    /* ########################################################################################## */


}