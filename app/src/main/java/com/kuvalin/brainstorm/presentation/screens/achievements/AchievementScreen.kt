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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.Achievement
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.achievement.AchievementsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor

@Composable
fun AchievementScreen(
    paddingParent: PaddingValues
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    val component = getApplicationComponent()
    val viewModel: AchievementsViewModel = viewModel(factory = component.getViewModelFactory())
    val achievementList by viewModel.achievementList.collectAsState()
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingParent.calculateTopPadding())
                .background(color = BackgroundAppColor),
            columns = GridCells.Fixed(2)
        ) {
            items(achievementList.size) { position ->
                AchievementItem(achievementList, position)
            }
        }
    }
    //endregion ################################################################################## */
}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region AchievementItem
@Composable
private fun AchievementItem(
    achievementList: List<Achievement>,
    position: Int
) {

    val context = LocalContext.current
    var clickOnAchievementState by remember { mutableStateOf(false) }

    if (clickOnAchievementState){
        AchievementItemContent(
            fileName = achievementList[position].icon,
            achievementName = achievementList[position].title,
            description = achievementList[position].description
        ){ clickOnAchievementState = false }
    }


    val isActive = achievementList[position].activeState
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
            bitmap = GetAssetBitmap(fileName = achievementList[position].icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isActive) 0.3f else 1f)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = achievementList[position].title,
            color = CyanAppColor,
            fontWeight = FontWeight.W500,
            fontSize = DynamicFontSize(LocalConfiguration.current.screenWidthDp, 16f),
            modifier = Modifier.alpha(if(isActive) 0.5f else 1f)
        )
    }
}
//endregion
//region AchievementItemContent
@Composable
fun AchievementItemContent(
    fileName: String,
    achievementName: String,
    description: String,
    onClickDismiss: () -> Unit
) {


    // Для проигрывания звуков
    val context = LocalContext.current

    // Немного странно тут считает размер, поэтому беру высоту.
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenWidthDp


    Dialog(
        onDismissRequest = {
            MusicPlayer(context = context).playChoiceClick()
            onClickDismiss()
        },
        content = {

            Column() {

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
                            MusicPlayer(context = context).playChoiceClick()
                            onClickDismiss()
                        }
                )
                //endregion

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight.dp)
                        .clip(RoundedCornerShape(3))
                        .background(color = BackgroundAppColor)
                ) {
                    AchievementItemLabel(achievementName)

                    AssetImage(fileName = fileName)
                    Text(
                        fontSize = 18.sp,
                        text = description,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp)
                    )
                }
            }

        },
    )

}
//endregion
//region AchievementItemLabel
@Composable
private fun AchievementItemLabel(text: String) {
    Text(
        text = text,
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 20.dp)

    )
}
//endregion
//endregion ################################################################################## */








