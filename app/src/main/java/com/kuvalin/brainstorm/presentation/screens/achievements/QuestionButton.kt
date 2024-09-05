package com.kuvalin.brainstorm.presentation.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor


@Composable
fun QuestionButton(
    onClickDismiss: () -> Unit
){

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)

    // Высота Dialog ()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dialogHeight = DynamicSize(baseDimension = screenWidth, desiredSize = (screenWidth*1.3).toFloat())
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Dialog(
        onDismissRequest = {
            MusicPlayer(context = context.value).playChoiceClick()
            onClickDismiss()
        },
        content = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(dialogHeight)
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

                    QuestionLabel()
                    Spacer(modifier = Modifier.height(20.dp))

                    AssetImage( fileName = "ic_achievement_button.png", )
                    Spacer(modifier = Modifier.height(20.dp))
                    Discription(text = "Достигайте новых высот и открывайте награды, " +
                            "что будут напоминать о боевых победах и радовать вас!")
                    Spacer(modifier = Modifier.height(1.dp))
                    Discription(text = "После взятия необходимого рубежа они нальются " +
                            "цветом и предстанут перед вами во всей красе!")

                }

            }

        },
    )
    //endregion ################################################################################## */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region Discription
@Composable
private fun Discription(text: String) {
    Text(
        fontSize = 18.sp,
        style = TextStyle(textIndent = TextIndent(10.sp)),
        text = text,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 10.dp)
    )
}
//endregion
//region QuestionLabel
@Composable
private fun QuestionLabel() {
    Text(
        text = "Achievements",
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
    )
}
//endregion
//endregion ################################################################################## */


