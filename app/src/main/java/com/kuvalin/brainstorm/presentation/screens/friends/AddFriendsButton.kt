package com.kuvalin.brainstorm.presentation.screens.friends

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.screens.mainmenu.main.ShareCompany
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor


@Composable
fun AddFriendsButtonContent( onClickDismiss: () -> Unit ){

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */

    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)

    // Получаем нужные размеры экрана
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp


    Dialog(
        onDismissRequest = { playSoundAndDismiss(context.value, onClickDismiss) },
        content = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(screenWidth.dp)
            ) {

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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp)
                            .background(color = BackgroundAppColor),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        //region Текст + поле вводда
                        AddFriendsButtonLabel()
                        Text(text = "Enter your friend's User Code", modifier = Modifier.offset(y = (-10).dp))
                        Text(text = "Your code is CTS7551", modifier = Modifier.offset( y = (-10).dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
                        ) {
                            CustomTextFieldFiendsScreen()
                        }
                        //endregion
                        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                        //region Строка с ShareCompany
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ShareCompany(fileName = "ic_facebook.png", companyName = "Facebook")
                            Spacer(modifier = Modifier.width(10.dp))
                            ShareCompany(fileName = "ic_vkontakte.png", companyName = "Vk")
                            Spacer(modifier = Modifier.width(10.dp))
                            ShareCompany(fileName = "ic_twitter.png", companyName = "Twitter")
                        }
                        //endregion

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
private fun AddFriendsButtonLabel() {
    Text(
        text = "Add Friends",
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(top = 10.dp, bottom = 20.dp)
    )
}
//endregion
//region CustomTextField
@Composable
private fun CustomTextFieldFiendsScreen(placeholder: String = "Enter Code") {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
            isFocused = true
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        ),
        modifier = Modifier.onFocusChanged { isFocused = it.isFocused },
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(color = Color.White)
                    .border(
                        width = 2.dp,
                        color = Color(0xFFAAE9E6),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding
            ) {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = placeholder,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray
                    )
                }
                innerTextField()
            }
        }
    )
}
//endregion
//region playSoundAndDismiss
private fun playSoundAndDismiss(context: Context, onClickDismiss: () -> Unit) {
    MusicPlayer(context = context).playChoiceClick()
    onClickDismiss()
}
//endregion
//endregion ################################################################################# */


