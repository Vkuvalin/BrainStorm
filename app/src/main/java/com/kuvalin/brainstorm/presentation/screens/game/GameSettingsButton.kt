package com.kuvalin.brainstorm.presentation.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.game.GameSettingsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.NotSelectedGameLevelA
import com.kuvalin.brainstorm.ui.theme.NotSelectedGameLevelB
import com.kuvalin.brainstorm.ui.theme.NotSelectedGameLevelC
import com.kuvalin.brainstorm.ui.theme.NotSelectedGameLevelS
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelA
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelB
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelC
import com.kuvalin.brainstorm.ui.theme.SelectedGameLevelS


@Composable
fun GameSettingsButton(
    onClickDismiss: () -> Unit
){
    // Для проигрывания звуков
    val context = LocalContext.current

    // Компонент
    val component = getApplicationComponent()
    val viewModel: GameSettingsViewModel = viewModel(factory = component.getViewModelFactory())

    // Стейты кнопок
    val selectSButtonState by viewModel.selectSButtonState.collectAsState()
    val selectAButtonState by viewModel.selectAButtonState.collectAsState()
    val selectBButtonState by viewModel.selectBButtonState.collectAsState()
    val selectCButtonState by viewModel.selectCButtonState.collectAsState()



    Dialog(
        onDismissRequest = {
            MusicPlayer(context = context).playChoiceClick()
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
                            MusicPlayer(context = context).playChoiceClick()
                            onClickDismiss()
                        }
                )
                //endregion

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(3))
                        .background(color = BackgroundAppColor)
                ) {
                    SettingsLabel()
                    Text("Select League", color = Color.Black, fontSize = 18.sp,
                        modifier = Modifier.offset(y = -(10).dp))
                    //region SubButtons
                    Row {
                        GameSettingsSubButton(
                            backgroundColor = if(selectSButtonState) SelectedGameLevelS else NotSelectedGameLevelS,
                            buttonName = "S",
                            selectState = selectSButtonState
                        ){ viewModel.selectButtonCategory(SettingsButtonCategory.S, context) }
                        GameSettingsSubButton(
                            backgroundColor = if(selectAButtonState) SelectedGameLevelA else NotSelectedGameLevelA,
                            buttonName = "A",
                            selectState = selectAButtonState
                        ){ viewModel.selectButtonCategory(SettingsButtonCategory.A, context) }
                        GameSettingsSubButton(
                            backgroundColor = if(selectBButtonState) SelectedGameLevelB else NotSelectedGameLevelB,
                            buttonName = "B",
                            selectState = selectBButtonState
                        ){ viewModel.selectButtonCategory(SettingsButtonCategory.B, context) }
                        GameSettingsSubButton(
                            backgroundColor = if(selectCButtonState) SelectedGameLevelC else NotSelectedGameLevelC,
                            buttonName = "C",
                            selectState = selectCButtonState
                        ){ viewModel.selectButtonCategory(SettingsButtonCategory.C, context) }
                    }
                    //endregion
                }
            }
        },
    )
}

enum class SettingsButtonCategory{ S, A, B, C }

//region GameSettingsSubButton
@Composable
fun GameSettingsSubButton(
    backgroundColor: Color,
    buttonName: String,
    selectState: Boolean,
    onPressButton: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 20.dp, horizontal = 5.dp)
            .size(50.dp)
            .clip(RoundedCornerShape(10))
            .background(color = backgroundColor)
            .border(
                width = 1.dp,
                color = backgroundColor,
                shape = RoundedCornerShape(10)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = buttonName,
            fontSize = 20.sp,
            color = if(selectState) Color.White else BackgroundAppColor,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}
//endregion
//region SettingsLabel
@Composable
private fun SettingsLabel() {
    Text(
        text = "Change League",
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
    )
}
//endregion

