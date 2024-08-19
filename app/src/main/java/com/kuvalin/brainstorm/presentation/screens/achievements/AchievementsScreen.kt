package com.kuvalin.brainstorm.presentation.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor

@Composable
fun AchievementsScreen(
    paddingParent: PaddingValues
) {

    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    // Вот эта срань позде переедет в базу
    val achievementsList = mutableListOf(
        mutableListOf("ic_3000 points.png", "3000 баллов", "Наберите более 3000 баллов за одну игру."),
        mutableListOf("ic_knowledge_base.png", "База знаний", "Достигните более 950 очков навыка 'память'."),
        mutableListOf("ic_accuracy.png", "Не промахнусь", "Достигните более 950 очков навыка 'точность'."),
        mutableListOf("ic_thinking.png", "Сама логика", "Достигните более 950 очков навыка 'суждение'."),
        mutableListOf("ic_calculate.png", "Калькулятор", "Достигните более 950 очков навыка 'вычисление'."),
        mutableListOf("ic_deft.png", "Не уйдешь", "5 раз вырвите победу в последней игре."),
        mutableListOf("ic_invincible.png", "Непобедимый", "Выиграйте 10 игр подряд."),
        mutableListOf("ic_observation.png", "Не скроешься", "Достигните более 950 очков навыка 'наблюдательность'."),
        mutableListOf("ic_conqueror.png", "Покоритель", "Доберитесь до S лиги."),
        mutableListOf("ic_speed.png", "Флеш не ровня", "Достигните более 950 очков навыка 'скорость'."),
        mutableListOf("ic_top_1.png", "Топ 1", "Займите 1-е место в лиге.")
    )
    val achievementsActiveState = mutableListOf(
        true, false, false, false, true, false, true, false, false, true, false
    )
    /* ########################################################################################## */


    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingParent.calculateTopPadding())
                .background(color = BackgroundAppColor),
            columns = GridCells.Fixed(2) // .Adaptive(minSize = 100.dp)
        ) {
            items(achievementsList.size) { position ->
                AchievementsItem(achievementsList, position, achievementsActiveState)
            }
        }
    }

}

//region AchievementsItem
@Composable
private fun AchievementsItem(
    achievementsList: MutableList<MutableList<String>>,
    position: Int,
    activeStateList: List<Boolean>
) {

    val context = LocalContext.current
    var clickOnAchievementState by remember { mutableStateOf(false) }

    if (clickOnAchievementState){
        AchievementsItemContent(
            fileName = achievementsList[position][0],
            achievementName = achievementsList[position][1],
            description = achievementsList[position][2]
        ){ clickOnAchievementState = false }
    }


    val isActive = activeStateList[position]
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20))
            .background(color = BackgroundAppColor)
            .padding(25.dp)
            .noRippleClickable {
                MusicPlayer(context).playChoiceClick()
                clickOnAchievementState = true
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            bitmap = GetAssetBitmap(fileName = achievementsList[position][0]),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isActive) 0.3f else 1f)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = achievementsList[position][1],
            color = CyanAppColor,
            fontWeight = FontWeight.W500,
            fontSize = DynamicFontSize(LocalConfiguration.current.screenWidthDp, 16f),
            modifier = Modifier.alpha(if(isActive) 0.5f else 1f)
        )
    }
}
//endregion
//region AchievementsItemContent
@Composable
fun AchievementsItemContent(
    fileName: String,
    achievementName: String,
    description: String,
    onClickDismiss: () -> Unit
) {

    // Для проигрывания звуков
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenWidthDp


    Dialog(
        onDismissRequest = {
            MusicPlayer(context = context).playChoiceClick()
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(color = BackgroundAppColor)
                    .height(screenHeight.dp),
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
                            MusicPlayer(context = context).playChoiceClick()
                            onClickDismiss()
                        }
                )
                //endregion
                AchievementsItemLabel(achievementName)
                Spacer(modifier = Modifier.height(10.dp))

                AssetImage(
                    fileName = fileName,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5))
                        .border(
                            width = 0.01.dp,
                            color = Color(0xE6E6E6),
                            shape = RoundedCornerShape(5)
                        )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    fontSize = 18.sp,
                    text = description,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )

            }
        },
    )
}
//endregion
//region AchievementsItemLabel
@Composable
private fun AchievementsItemLabel(text: String) {
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
            .offset(y = -(20).dp)
    )
}
//endregion





