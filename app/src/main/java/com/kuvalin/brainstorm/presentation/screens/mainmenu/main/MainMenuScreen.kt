package com.kuvalin.brainstorm.presentation.screens.mainmenu.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.kuvalin.brainstorm.R
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.LinearTrackColor
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelA
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelB
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelC
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelS


@Composable
fun MainMenuScreen(
    navigationState: NavigationState,
    paddingValues: PaddingValues
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    //endregion ################################################################################# */



    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .background(color = BackgroundAppColor)
            .fillMaxSize()
    ) {

        //region Аватарка + жизни/койны
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(color = CyanAppColor)
                .wrapContentHeight()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Avatar(navigationState, context.value) // Аватарка
            Spacer( modifier = Modifier .fillMaxHeight() .width(10.dp) )
            LiveAndCoins() // Жизни и койны

        }
        //endregion
        //region Карточки статистики, график и кнопка
        Column(
            modifier = Modifier
                .height(screenHeight)
                .width(screenWidth),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            StatisticsCards(screenWidth)
            DrawingChart(workMode = 1)
            ButtonChallenge{ navigationState.navigateToSearchForWar() }
        }
        //endregion

    }
    //endregion ################################################################################# */

}


//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region Жизни и койны
@Composable
private fun LiveAndCoins() {
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment = Alignment.Bottom,
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_like_set),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_like_set),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_like_set),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_like_set),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_like_set),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = R.drawable.ic_stars),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    text = "200",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
//endregion
//region Avatar
@Composable
private fun Avatar(
    navigationState: NavigationState,
    context: Context
) {
    Box(modifier = Modifier.noRippleClickable {
        navigationState.navigateToProfile()
        MusicPlayer(context = context).playChangeNavigation()
    }) {
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
        //region Pencil
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(10))
                .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(10))
                .background(color = Color.White)
                .align(alignment = Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            AssetImage(
                fileName = "ic_avatar_pencil.png",
                modifier = Modifier
                    .size(15.dp)
            )
        }
        //endregion
    }
}
//endregion

// Я зафаршмачил эту часть. Переделывать лень.
//region StatisticsCards
@Composable
fun StatisticsCards(screenWidth: Dp) {
    Row(
        modifier = Modifier
            .width(screenWidth)
            .offset(x = (35 / 2).dp)
        ,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        StatisticsCard(
            type = "Grade",
            cardName = "Grade",
            text = "Bear"
        )

        StatisticsCard(
            type = "League",
            cardName = "Rank",
            text = "924",
            text2 = "League Save" // League Up | League Drop
        )

    }
}
//endregion
//region Карточка статистики
@Composable
fun StatisticsCard(
    type: String,
    cardName: String,
    text: String,
    text2: String = ""
) {

    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)
    val configuration = LocalConfiguration.current

    val leagueList = mutableListOf(
        mutableListOf("S", SelectedGameLevelS, false),
        mutableListOf("A", SelectedGameLevelA, true),
        mutableListOf("B", SelectedGameLevelB, false),
        mutableListOf("C", SelectedGameLevelC, false)
    )

    val gradeList = mutableListOf( // TODO перевести в базу, а затем добавить в ShareStatistics.kt
        mutableListOf("level_1_mouse.png", false),
        mutableListOf("level_2_snake.png", false),
        mutableListOf("level_3_beaver.png", false),
        mutableListOf("level_4_pig.png", false),
        mutableListOf("level_5_cow.png", false),
        mutableListOf("level_6_cat.png", false),
        mutableListOf("level_7_fox.png", false),
        mutableListOf("level_8_dog.png", false),
        mutableListOf("level_9_giraffe.png", false),
        mutableListOf("level_10_hippo.png", false),
        mutableListOf("level_11_elephant.png", false),
        mutableListOf("level_12_bear.png", true),
        mutableListOf("level_13_eagle.png", false),
        mutableListOf("level_14_lion.png", false)
    )

    var onClickGrade by remember { mutableStateOf(false) }
    if (onClickGrade){ GradeDialog(MusicPlayer(context = context.value)) { onClickGrade = false } }

    Row(
        modifier = Modifier.width(configuration.screenWidthDp.dp / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .zIndex(2f)
                .background(color = Color.White)
                .border(width = 1.dp, color = Color.White, shape = CircleShape)
                .noRippleClickable {
                    if (type == "Grade"){
                        MusicPlayer(context.value).playChoiceClick()
                        onClickGrade = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (type == "Grade"){
                gradeList.forEach {
                    if (it[1] == true){
                        AssetImage(fileName = it[0] as String, modifier = Modifier.padding(5.dp))
                    }
                }
            }else{
                leagueList.forEach {
                    if (it[2] == true){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(color = it[1] as Color)
                                .border(
                                    width = 4.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        ){
                            Text(
                                text = it[0] as String,
                                fontSize = 24.sp,
                                color = Color.White,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                            )
                        }
                    }
                }

            }
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

//region ButtonChallenge
@Composable
private fun ButtonChallenge(
    onButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(25))
            .background(color = CyanAppColor)
            .border(width = 1.dp, color = BackgroundAppColor, shape = RoundedCornerShape(25))
            .noRippleClickable {
                onButtonClick()
            },
    ) {
        Text(
            text = "Challenge",
            fontSize = 25.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 20.dp)
        )
    }
}
//endregion
//region GradeDialog
@Composable
fun GradeDialog(
    mediaPlayer: MusicPlayer,
    onClickDismiss: () -> Unit
){

    val gradeList = mutableListOf(
        mutableListOf("level_1_mouse.png", false),
        mutableListOf("level_2_snake.png", false),
        mutableListOf("level_3_beaver.png", false),
        mutableListOf("level_4_pig.png", false),
        mutableListOf("level_5_cow.png", false),
        mutableListOf("level_6_cat.png", false),
        mutableListOf("level_7_fox.png", false),
        mutableListOf("level_8_dog.png", false),
        mutableListOf("level_9_giraffe.png", false),
        mutableListOf("level_10_hippo.png", false),
        mutableListOf("level_11_elephant.png", false),
        mutableListOf("level_12_bear.png", true),
        mutableListOf("level_13_eagle.png", false),
        mutableListOf("level_14_lion.png", false)
    )
    var checker = false  // Можно оставить?


    Dialog(
        onDismissRequest = {
            mediaPlayer.playChoiceClick()
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(color = BackgroundAppColor)
            ) {

                //region Крестик
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .offset(x = (10).dp, y = (-10).dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .background(color = Color.White)
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            mediaPlayer.playChoiceClick()
                            onClickDismiss()
                        }
                )
                //endregion
                GradeListLabel()
                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxWidth()){
                    LazyVerticalGrid(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(color = BackgroundAppColor),
                        columns = GridCells.Adaptive(minSize = 60.dp)
                    ) {
                        items(gradeList.size) { position ->
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .zIndex(2f)
                                    .border(width = 1.dp, color = BackgroundAppColor, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ){

                                if (!checker){
                                    if (gradeList[position][1] == true){ checker = true }
                                    AssetImage(fileName = gradeList[position][0] as String, modifier = Modifier.padding(5.dp))
                                }else {
                                    AssetImage(fileName = "ic_grade_question.png", modifier = Modifier.padding(5.dp))
                                }

                            }
                        }
                    }
                }


            }
        },
    )
}
//endregion
//region AchievementsItemLabel
@Composable
private fun GradeListLabel() {
    Text(
        text = "Grade List",
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .offset(y = -(20).dp)
    )
}
//endregion
//endregion ################################################################################# */


