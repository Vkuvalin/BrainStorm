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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable


@Composable
fun QuestionButton(
    onClickDismiss: () -> Unit
){

    Dialog(
        onDismissRequest = { onClickDismiss() },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(color = Color(0xE6E6E6E6))
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
                        .noRippleClickable { onClickDismiss() }
                )
                //endregion

                QuestionLabel()
                Spacer(modifier = Modifier.height(10.dp))

                AssetImage(
                    fileName = "ic_achievement_button.png",
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
                    style = TextStyle(textIndent = TextIndent(10.sp)),
                    text = "Достигайте новых высот и открывайте награды, что будут напоминать о боевых победах и радовать вас!",
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp)
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    fontSize = 18.sp,
                    style = TextStyle(textIndent = TextIndent(10.sp)),
                    text = "После взятия необходимого рубежа они нальются цветом и предстанут перед вами во всей красе!",
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 10.dp)
                )

            }
        },
    )
}

//region QuestionLabel
@Composable
private fun QuestionLabel() {
    Text(
        text = "Achievements",
        color = Color(0xFF00BBBA),
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


