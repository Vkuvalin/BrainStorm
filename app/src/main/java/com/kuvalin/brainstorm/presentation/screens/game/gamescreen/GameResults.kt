package com.kuvalin.brainstorm.presentation.screens.game.gamescreen

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.ANIMATION_DURATION_350
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameScreenViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.MiniStatPanelBackground
import com.kuvalin.brainstorm.ui.theme.OrangeAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor


@Composable
fun GameResults(
    viewModel: GameScreenViewModel,
    onRetryButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
){
    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(ANIMATION_DURATION_350){ clickNavigation = false } }

    BackHandler {
        clickNavigation = true
        onBackButtonClick()
    }



    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)

    // Отрисовка элементов
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dynamicFontSize1 = DynamicFontSize(screenWidth, 20f)
    val dynamicFontSize2 = DynamicFontSize(screenWidth, 16f)
    val dynamicFontSize3 = DynamicFontSize(screenWidth, 13f)


    // Результаты игры и состояние
    val gameName by viewModel.gameName.collectAsState()
    val miniatureGameImage by viewModel.miniatureGameImage.collectAsState()

    val correct by viewModel.correct.collectAsState()
    val incorrect by viewModel.incorrect.collectAsState()
    val accuracy by viewModel.accuracy.collectAsState()

    val highestValue by viewModel.highestValue.collectAsState()
    val averageValue by viewModel.averageValue.collectAsState()
    val finalAccuracy by viewModel.finalAccuracy.collectAsState()
    val finalScope by viewModel.finalScope.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.addGameResult()
        viewModel.loadGameStatistic()
    }
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        modifier = Modifier
            .background(color = BackgroundAppColor)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        TitleSection(miniatureGameImage, gameName)
        //region Statistics section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
        ) {
            GameStatColumn("Incorrect", "$incorrect", PinkAppColor)
            GameAccuracyIndicator(accuracy, finalAccuracy)
            GameStatColumn("Correct", "$correct", CyanAppColor)
        }
        //endregion

        Spacer(modifier = Modifier.height(100.dp))

        //region Additional Statistics section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            //region BattleRecordColumn
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .weight(2f)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10))
                    .background(color = MiniStatPanelBackground)
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            ) {
                BattleRecordColumnContent(
                    dynamicFontSize2,
                    dynamicFontSize3,
                    highestValue,
                    dynamicFontSize1,
                    averageValue
                )
            }
            //endregion
            //region GameScore
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10))
                    .background(color = MiniStatPanelBackground)
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            ) {
                GameScoreColumn(dynamicFontSize2, finalScope, dynamicFontSize1)
            }
            //endregion
        }
        //endregion
        //region Buttons section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)){
                StringButton(
                    buttonText = "Retry",
                    buttonSize = 24,
                    color = OrangeAppColor
                ){
                    MusicPlayer(context = context.value).playChoiceClick()
                    onRetryButtonClick()
                }
            }

            Box(modifier = Modifier.weight(1f)){
                StringButton(
                    buttonText = "Back",
                    buttonSize = 24,
                    color = CyanAppColor
                ){ onBackButtonClick() }
            }
        }
        //endregion

    }
    //endregion ################################################################################# */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region GameScoreColumn
@Composable
private fun GameScoreColumn(
    dynamicFontSize2: TextUnit,
    finalScope: Int,
    dynamicFontSize1: TextUnit
) {
    Text(
        text = "Score",
        fontSize = dynamicFontSize2,
        fontWeight = FontWeight.W400,
        color = CyanAppColor,
    )
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = "$finalScope",
            fontSize = dynamicFontSize1,
            fontWeight = FontWeight.W400,
            color = Color.Black
        )
    }
}
//endregion
//region BattleRecordColumnContent
@Composable
private fun BattleRecordColumnContent(
    dynamicFontSize2: TextUnit,
    dynamicFontSize3: TextUnit,
    highestValue: Int,
    dynamicFontSize1: TextUnit,
    averageValue: Int
) {
    Text(
        text = "Battle Record",
        fontSize = dynamicFontSize2,
        fontWeight = FontWeight.W400,
        color = CyanAppColor,
    )
    Row {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = "Highest",
                fontSize = dynamicFontSize3,
                fontWeight = FontWeight.W400,
                color = Color.Black
            )
            Text(
                text = "$highestValue",
                fontSize = dynamicFontSize1,
                fontWeight = FontWeight.W400,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = "Average",
                fontSize = dynamicFontSize3,
                fontWeight = FontWeight.W400,
                color = Color.Black
            )
            Text(
                text = "$averageValue",
                fontSize = dynamicFontSize1,
                fontWeight = FontWeight.W400,
                color = Color.Black
            )
        }
    }
}
//endregion
//region TitleSection
@Composable
private fun TitleSection(miniatureGameImage: String, gameName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(6f)
                .wrapContentWidth(align = Alignment.End)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .background(color = Color.White)
                    .then(Modifier.padding(3.dp))
            ) {
                AssetImage(
                    fileName = miniatureGameImage,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Text(
            text = gameName,
            fontSize = 24.sp,
            fontWeight = FontWeight.W300,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(12f)
        )
    }
}
//endregion

//region GameAccuracyIndicator
@Composable
private fun GameAccuracyIndicator(accuracy: Float, finalAccuracy: Float) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = if (accuracy == null) 0f else accuracy,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(color = PinkAppColor),
            strokeWidth = 20.dp,
            color = CyanAppColor,
        )
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(color = BackgroundAppColor)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Accuracy", fontSize = 16.sp, fontWeight = FontWeight.W400)
            Text(text = "$finalAccuracy", fontSize = 26.sp, fontWeight = FontWeight.W300)
        }
    }
}
//endregion
//region GameStatColumn
@Composable
fun GameStatColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.W400, color = color)
        Text(text = value, fontSize = 26.sp, fontWeight = FontWeight.W300)
    }
}
//endregion
//region StringButton
@Composable
private fun StringButton(
    buttonText: String,
    buttonSize: Int,
    color: Color,
    onButtonClick: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(14))
            .background(color = color)
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onButtonClick() }
    ) {
        Text(
            text = buttonText,
            fontSize = buttonSize.sp,
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
    }
}
//endregion
//endregion ################################################################################# */



