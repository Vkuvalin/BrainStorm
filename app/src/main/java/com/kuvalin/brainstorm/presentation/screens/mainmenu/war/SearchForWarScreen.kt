package com.kuvalin.brainstorm.presentation.screens.mainmenu.war

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.presentation.screens.mainmenu.DrawingChart
import com.kuvalin.brainstorm.presentation.viewmodels.WarViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.delay


/**
 * UserInfo тащить сюда не буду. Пусть остаются заглушки.
*/


@Composable
fun SearchForWar(
    navigationState: NavigationState
){

    LaunchedEffect(Unit) { GlobalStates.putScreenState("runGameScreenState", true) }
    BackHandler {
        navigationState.navigateToHome()
        GlobalStates.putScreenState("runGameScreenState", false)
    }

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    val component = getApplicationComponent()
    val viewModel: WarViewModel = viewModel(factory = component.getViewModelFactory())


    // Аватар
    var uriAvatar by remember { mutableStateOf<Uri?>(null) }


    var waitOpponent by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        val result = viewModel.findTheGame.invoke()
        Log.d("FIREBASE_REQUEST", "$result")

        if (result.first){
            waitOpponent = false
            delay(3000)
            navigationState.navigateToWar(result.second)
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
            UserInfo( uriAvatar = uriAvatar, name = "Vlad", grade = "Bear", rank = 941)
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
                UserInfo( uriAvatar = uriAvatar, name = "Evgeny", grade = "Cat", rank = 248)
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

}

//region UserInfo
@Composable
private fun UserInfo(
    uriAvatar: Uri?,
    name: String,
    grade: String,
    rank: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .weight(2f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                //region Avatar
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Box {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color = Color.White)
                                .border(width = 2.dp, color = Color.White, shape = CircleShape),
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
                //region Имя + Grade/Rank
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //region Grade
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            ) {
                            Row(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Grade",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = grade,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W300
                                )
                            }
                        }
                        //endregion
                        //region Rank
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Rank",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "$rank",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W300
                                )
                            }
                        }
                        //endregion
                    }
                }
                //endregion
            }
        }

        //region Флаг
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
        ) {
            AssetImage(
                fileName = "ic_profile_russia.png",
                modifier = Modifier.size(60.dp)
            )
        }
        //endregion
    }
}
//endregion

