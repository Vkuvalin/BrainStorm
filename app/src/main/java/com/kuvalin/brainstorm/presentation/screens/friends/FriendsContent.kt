package com.kuvalin.brainstorm.presentation.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.noRippleClickable

@Composable
fun FriendsContent(
    paddingValues: PaddingValues
){

    //Button
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicRowWidth = (screenWidth/1.5).toInt()
    var onClickButtonState by remember { mutableStateOf(false) }
    if (onClickButtonState){
        AddFriendsButtonContent(){ onClickButtonState = false }
    }

    // ListFriend
    var listFriends = mutableListOf("Ваня", "Дима", "Катя", "Лиза")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(color = Color(0xFFE6E6E6))
    ) {

        if (listFriends.size == 0){
            // pass
        }else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Challenge your friends!", fontSize = 20.sp)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(dynamicRowWidth.dp)
                        .clip(RoundedCornerShape(14))
                        .background(color = Color(0xFFFE5FA6))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFFE5FA6),
                            shape = RoundedCornerShape(14)
                        )
                        .noRippleClickable { onClickButtonState = true }
                ){
                    Text(
                        text = "Add Friends",
                        fontSize = 24.sp,
                        color = Color(0xFFE6E6E6),
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    )
                }
            }
        }


    }

}