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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// С масштабированием в приложении снова проблемы, но переписать части сейчас не видится проблемой

@Composable
fun GameResults(
    gameName: String,
    miniatureGameImage: String,
    correct: Int,
    incorrect: Int,
    scope: Int,
    accuracy: Float,
    onRetryButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
){
    BackHandler { onBackButtonClick() }

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Для проигрывания звуков
    val context = LocalContext.current
    val musicScope = CoroutineScope(Dispatchers.Default)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize1 = (screenWidth / 20)
    val dynamicFontSize2 = (screenWidth / 25)
    val dynamicFontSize3 = (screenWidth / 30)
    /* ########################################################################################## */


    Column(
        modifier = Modifier
            .background(color = Color(0xFFE6E6E6))
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .wrapContentHeight(align = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        //region Title
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
        //endregion
        //region Statistics
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Incorrect", fontSize = 16.sp, fontWeight = FontWeight.W400, color = Color(0xFFE85B9D))
                Text(text = "$incorrect", fontSize = 26.sp, fontWeight = FontWeight.W300)
            }

            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    progress = if (accuracy == null) 0f else accuracy,
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
                    Text(text = "Accuracy", fontSize = 16.sp, fontWeight = FontWeight.W400)
                    Text(
                        text = "${(accuracy * 1000).roundToInt() / 10.0f}",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W300
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Correct", fontSize = 16.sp, fontWeight = FontWeight.W400, color = Color(0xFF00BAB9))
                Text(text = "$correct", fontSize = 26.sp, fontWeight = FontWeight.W300)
            }
        }
        //endregion
        Spacer(modifier = Modifier.height(100.dp))
        //region Statistics2
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .weight(2f)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10))
                    .background(color = Color(0x80FFFFFF))
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Battle Record",
                    fontSize = dynamicFontSize2.sp,
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
                            fontSize = dynamicFontSize3.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black
                        )
                        Text(
                            text = "-",
                            fontSize = dynamicFontSize1.sp,
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
                            fontSize = dynamicFontSize3.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black
                        )
                        Text(
                            text = "-",
                            fontSize = dynamicFontSize1.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10))
                    .background(color = Color(0x80FFFFFF))
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "Score",
                    fontSize = dynamicFontSize2.sp,
                    fontWeight = FontWeight.W400,
                    color = CyanAppColor,
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = "${if(scope == 0) ((correct*53)-(incorrect*22)) else (scope*53)-(scope*22)}",
                        fontSize = dynamicFontSize1.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Black
                    )
                }
            }
        }
        //endregion
        //region Buttons
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
                    color = Color(0xFFFF7700)
                ){
                    musicScope.launch {
                        MusicPlayer(context = context).run {
                            playChoiceClick()
                            delay(3000)
                            release()
                        }
                    }
                    onRetryButtonClick()
                }
            }

            Box(modifier = Modifier.weight(1f)){
                StringButton(
                    buttonText = "Back",
                    buttonSize = 24,
                    color = CyanAppColor
                ){
                    musicScope.launch {
                        MusicPlayer(context = context).run {
                            playChoiceClick()
                            delay(3000)
                            release()
                        }
                    }
                    onBackButtonClick()
                }
            }
        }
        //endregion

    }
}

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

