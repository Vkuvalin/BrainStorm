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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor


@Composable
fun GameSettingsButton(
    onClickDismiss: () -> Unit
){
    // Для проигрывания звуков
    val context = LocalContext.current

    // Должно перекачевать в базу
    var selectSButtonState by remember { mutableStateOf(false) }
    var selectAButtonState by remember { mutableStateOf(true) }
    var selectBButtonState by remember { mutableStateOf(false) }
    var selectCButtonState by remember { mutableStateOf(false) }

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
                SettingsLabel()
                Text("Select League", color = Color.Black, fontSize = 18.sp, modifier = Modifier.offset(y = -(10).dp))
                //region SubButtons
                Row {
                    GameSettingsSubButton(
                        backgroundColor = if(selectSButtonState)
                            Color(0xFFE85B9D) else Color(0x59E85B9D),
                        buttonName = "S",
                        selectState = selectSButtonState
                    ){
                        selectSButtonState = true
                        selectAButtonState = false
                        selectBButtonState = false
                        selectCButtonState = false
                    }
                    GameSettingsSubButton(
                        backgroundColor = if(selectAButtonState)
                            Color(0xFFF28B01) else Color(0x59F28B01),
                        buttonName = "A",
                        selectState = selectAButtonState
                    ){
                        selectSButtonState = false
                        selectAButtonState = true
                        selectBButtonState = false
                        selectCButtonState = false
                    }
                    GameSettingsSubButton(
                        backgroundColor = if(selectBButtonState)
                            Color(0xFFF3CB00) else Color(0x59F3CB00),
                        buttonName = "B",
                        selectState = selectBButtonState
                    ){
                        selectSButtonState = false
                        selectAButtonState = false
                        selectBButtonState = true
                        selectCButtonState = false
                    }
                    GameSettingsSubButton(
                        backgroundColor = if(selectCButtonState)
                            Color(0xFFBECF0D) else Color(0x59BECF0D),
                        buttonName = "C",
                        selectState = selectCButtonState
                    ){
                        selectSButtonState = false
                        selectAButtonState = false
                        selectBButtonState = false
                        selectCButtonState = true
                    }
                }
                //endregion
            }
        },
    )
}


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
            color = if(selectState) Color.White else Color(0xE6E6E6E6),
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
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .offset(y = -(20).dp)
    )
}
//endregion

