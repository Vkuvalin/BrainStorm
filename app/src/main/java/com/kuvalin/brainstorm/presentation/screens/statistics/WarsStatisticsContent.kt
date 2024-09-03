package com.kuvalin.brainstorm.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.presentation.viewmodels.statistics.WarsStatisticsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlin.math.roundToInt


@Composable
fun WarsStatisticsContent(
    paddingParent: PaddingValues,
    uid: String? = null,
    parentWidth: Int? = null
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */

    // ViewModel
    val viewModel: WarsStatisticsViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())

    // Загрузка статистики пользователя
    LaunchedEffect(Unit) {
        val currentUserUid = uid ?: viewModel.getUserUid.invoke()
        viewModel.loadWarStatistics(currentUserUid)
    }

    // Анимация мозга
    val animBrainLoadState by GlobalStates.animBrainLoadState.collectAsState()

    // Подписка на изменения в ViewModel
    val wins by viewModel.wins.collectAsState()
    val losses by viewModel.losses.collectAsState()
    val draws by viewModel.draws.collectAsState()
    val highestScore by viewModel.highestScore.collectAsState()
    val winRate by viewModel.winRate.collectAsState()

    // Получение размеров экрана и расчет коэффициента масштабирования
    val configuration = LocalConfiguration.current
    val screenWidth = parentWidth ?: configuration.screenWidthDp
    val compressionRatio = calculateCompressionRatio(screenWidth)

    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor)
            .padding(top = paddingParent.calculateTopPadding())
            .padding(horizontal = 30.dp)
    ) {

        if (!animBrainLoadState){
            AdaptiveBoxContent(compressionRatio){ RoundCircleIndicator(winRate, compressionRatio) }
            Spacer(modifier = Modifier.height((50*compressionRatio).dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AdaptiveBoxContent(compressionRatio){
                    Box(modifier = Modifier.weight(1f)){StatisticBoxContent("WINS", wins, compressionRatio) }
                }

                AdaptiveBoxContent(compressionRatio){
                    Box(modifier = Modifier.weight(1f)){ StatisticBoxContent("LOSSES", losses, compressionRatio) }
                }
                AdaptiveBoxContent(compressionRatio){
                    Box(modifier = Modifier.weight(1f)){StatisticBoxContent("DRAWS", draws, compressionRatio)}
                }
            }
            AdaptiveBoxContent(compressionRatio){ Spacer(modifier = Modifier.height((50*compressionRatio).dp)) }
            AdaptiveBoxContent(compressionRatio){ HighestScoreBoxContent(highestScore, compressionRatio) }
        }

    }
    //endregion ################################################################################## */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */

@Composable
private fun AdaptiveBoxContent(scale: Float, content: @Composable () -> Unit) {
    Box(modifier = Modifier.scale(scale)) {content()}
}


//region RoundCircleIndicator
@Composable
private fun RoundCircleIndicator(
    winRate: Float, scale: Float
) {

    val winRateInterest = (winRate * 1000).roundToInt() / 10.0f

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            progress = winRate,
            modifier = Modifier
                .size((150*scale).dp)
                .clip(CircleShape)
                .background(color = Color(0xFFE85B9D)),
            strokeWidth = 20.dp,
            color = Color(0xFF00BAB9),
        )
        Box(
            modifier = Modifier
                .size((110*scale).dp)
                .clip(CircleShape)
                .background(color = BackgroundAppColor)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "WIN RATE", fontSize = (16*scale).sp, fontWeight = FontWeight.W400)
            Text(
                text = "$winRateInterest",
                fontSize = (26*scale).sp,
                fontWeight = FontWeight.W300
            )
        }
    }

}
//endregion
//region StatisticBoxContent
@Composable
private fun StatisticBoxContent(name: String, value: Int, scale: Float) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFFF3F3F3))
            .width((100*scale).dp)
            .height((75*scale).dp)
    ) {
        Text(
            text = name,
            fontSize = (16*scale).sp
        )
        Text(
            text = "$value",
            fontSize = (30*scale).sp,
            color = when(name){
                "WINS" -> CyanAppColor
                "LOSSES" -> PinkAppColor
                "DRAWS" -> Color.Black
                else -> Color.Gray
            }
        )
    }

}
//endregion
//region HighestScoreBoxContent
@Composable
private fun HighestScoreBoxContent(value: Int, scale: Float) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFFF3F3F3))
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Highest Score",
            fontSize = (16*scale).sp
        )
        Text(
            text = "$value",
            fontSize = (30*scale).sp,
            color = Color.Black
        )
    }

}
//endregion

// Вспомогательная функция для расчета коэффициента сжатия экрана
private fun calculateCompressionRatio(screenWidth: Int): Float {
    return screenWidth / 393.toFloat()
}

//endregion ################################################################################## */


