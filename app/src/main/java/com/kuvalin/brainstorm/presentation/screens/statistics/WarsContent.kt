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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.presentation.viewmodels.StatisticsViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlin.math.roundToInt


@Composable
fun WarsContent(
    paddingParent: PaddingValues
) {

    // Компонент
    val component = getApplicationComponent()
    val viewModel: StatisticsViewModel = viewModel(factory = component.getViewModelFactory())

    val userUid = Firebase.auth.uid ?: "zero_user_uid"


    var wins by remember { mutableIntStateOf(0) }
    var losses by remember { mutableIntStateOf(0) }
    var draws by remember { mutableIntStateOf(0) }
    var highestScore by remember { mutableIntStateOf(0) }
    var winRate by remember { mutableFloatStateOf(0f) }


    LaunchedEffect(Unit) {
        val warStatistics = viewModel.getWarStatistic.invoke(userUid)
        wins = warStatistics.wins
        losses = warStatistics.losses
        draws = warStatistics.draws

        winRate = (wins/(wins+losses).toFloat())
        highestScore = warStatistics.highestScore
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))
            .padding(top = paddingParent.calculateTopPadding())
            .padding(horizontal = 30.dp)
    ) {

        RoundCircleIndicator(winRate)
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            StatisticBoxContent("WINS", wins)
            StatisticBoxContent("LOSSES", losses)
            StatisticBoxContent("DRAWS", draws)
        }
        Spacer(modifier = Modifier.height(50.dp))
        HighestScoreBoxContent(highestScore)

    }

}










//region RoundCircleIndicator
@Composable
private fun RoundCircleIndicator(
    winRate: Float
) {

    val winRateInterest = (winRate * 1000).roundToInt() / 10.0f

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            progress = winRate,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFE85B9D)),
            strokeWidth = 20.dp,
            color = Color(0xFF00BAB9),
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
            Text(text = "WIN RATE", fontSize = 16.sp, fontWeight = FontWeight.W400)
            Text(
                text = "$winRateInterest",
                fontSize = 26.sp,
                fontWeight = FontWeight.W300
            )
        }
    }

}
//endregion
//region StatisticBoxContent
@Composable
private fun StatisticBoxContent(name: String, value: Int) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFFF3F3F3))
            .width(100.dp)
            .height(75.dp)
    ) {
        Text(
            text = name,
            fontSize = 16.sp
        )
        Text(
            text = "$value",
            fontSize = 30.sp,
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
private fun HighestScoreBoxContent(value: Int) {

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
            fontSize = 14.sp
        )
        Text(
            text = "$value",
            fontSize = 30.sp,
            color = Color.Black
        )
    }

}
//endregion






