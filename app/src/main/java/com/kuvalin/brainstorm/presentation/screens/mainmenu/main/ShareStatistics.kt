package com.kuvalin.brainstorm.presentation.screens.mainmenu.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.main.ShareStatisticsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.LinearTrackColor



@Composable
fun ShareContent(
    onClickDismiss: () -> Unit
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)

    // Component
    val component = getApplicationComponent()
    val viewModel: ShareStatisticsViewModel = viewModel(factory = component.getViewModelFactory())

    val userName by viewModel.userName.collectAsState()
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Dialog(
        onDismissRequest = {
            viewModel.playChoiceClickSound(context.value)
            onClickDismiss()
        },
        content = {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                //region Крестик
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .zIndex(2f)
                        .offset(x = (10).dp, y = (20).dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .background(color = Color.White)
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            MusicPlayer(context = context.value).playChoiceClick()
                            onClickDismiss()
                        }
                )
                //endregion

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(3))
                    .background(color = BackgroundAppColor)
            ) {

                ShareLabel("Share your stats!")

                Column(
                    modifier = Modifier
                        .border(width = 10.dp, color = Color.White, shape = RectangleShape)
                        .padding(top = 10.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //region Аватарка и имя
                    Row(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .background(color = CyanAppColor)
                            .wrapContentHeight()
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Аватарка
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(color = Color.White)
                                .border(width = 2.dp, color = Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            AssetImage(fileName = "av_user.png")
                        }

                        Spacer(modifier = Modifier
                            .fillMaxHeight()
                            .width(10.dp))

                        Text(
                            text = userName,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W400
                        )

                    }
                    //endregion
                    //region Карточки статистики
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Row(modifier = Modifier
                            .weight(1f)
                            .scale(1f)
                            .fillMaxWidth()
                            .offset(x = (35 / 2).dp)
                        ){
                            ShareStatisticsCard(
                                pathImage = "level_12_bear.png",
                                cardName = "Grade",
                                text = "Bear"
                            )
                        }

                        Row(modifier = Modifier
                            .weight(1f)
                            .scale(1f)
                            .fillMaxWidth()
                            .offset(x = (35 / 2).dp)
                        ){
                            ShareStatisticsCard(
                                pathImage = "av_user.png",
                                cardName = "Rank",
                                text = "924",
                                text2 = "League Save" // League Up | League Drop
                            )
                        }
                    }
                    //endregion
                    Box(modifier = Modifier.scale(0.85f)){ DrawingChart(workMode = 1) }
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    ShareCompany(fileName = "ic_facebook.png", companyName = "Facebook")
                    Spacer(modifier = Modifier.width(1.dp))
                    ShareCompany(fileName = "ic_vkontakte.png", companyName = "Vk")
                    Spacer(modifier = Modifier.width(1.dp))
                    ShareCompany(fileName = "ic_twitter.png", companyName = "Twitter")
                }

            }
            }
        },
    )
    //endregion ################################################################################# */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region ShareLabel
@Composable
private fun ShareLabel(text: String) {
    Text(
        text = text,
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(bottom = 20.dp, top = 10.dp)
    )
}
//endregion
//region ShareStatisticsCard
@Composable
fun ShareStatisticsCard(
    pathImage: String,
    cardName: String,
    text: String,
    text2: String = ""
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .zIndex(2f)
                .background(color = Color.White)
                .border(width = 1.dp, color = Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AssetImage(fileName = pathImage, modifier = Modifier.padding(3.dp))
        }

        Column(
            modifier = Modifier
                .offset(x = (-35).dp)
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = cardName,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(150.dp) // 140
                    .clip(RoundedCornerShape(25))
                    .background(color = Color.White)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(25))

            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(start = (40).dp)
                    )
                    if (text2 != "") {
                        Text(
                            text = text2,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W400,
                            letterSpacing= 0.1.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = (40).dp)
                                .offset(y = (-2).dp)
                        )
                    } else {
                        LinearProgressIndicator(
                            progress = 0.7f,
                            color = CyanAppColor,
                            trackColor = LinearTrackColor,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(45))
                        )
                    }
                }
            }
        }
    }
}
//endregion
//region ShareCompany
@Composable
fun ShareCompany(
    fileName: String,
    companyName: String
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width(70.dp)
    ) {
        AssetImage(
            fileName = fileName, modifier = Modifier
            .size(50.dp)
            .padding(bottom = 5.dp))
        Text(text = companyName, fontSize = 14.sp)
    }
}
//endregion
//endregion ################################################################################# */


