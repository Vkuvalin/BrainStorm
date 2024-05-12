package com.kuvalin.brainstorm.presentation.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.ui.theme.PinkAppColor

@Composable
fun FriendsContent(
    paddingValues: PaddingValues
){

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    //Button
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicRowWidth = (screenWidth/1.5).toInt()
    var onClickButtonState by remember { mutableStateOf(false) }
    if (onClickButtonState){
        AddFriendsButtonContent(){ onClickButtonState = false }
    }

    // Динамический размер текста
    val dynamicFontSize = (screenWidth/19) // == 20.sp
    // TODO Придумать позже универсальную функцию, что будет принимать screenWidth и желаемый результат


    // ListFriend (Будет прилетать из базы)
    val listFriends = listOf( // TODO
        UserInfo("1111", name = "Vitaly"),
        UserInfo("2222", name = "Liza"),
        UserInfo("3333", name = "Karen"),
        UserInfo("4444", name = "Evgeny")
    )

    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(UserInfo(uid = "123")) }
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 2) { clickUserRequestPanel = false }
    }

    /* ########################################################################################## */


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(color = Color(0xFFE6E6E6))
    ) {

        if (listFriends.size != 0){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(listFriends.size) {position ->
                    UserRequestOrFriendPanel(userInfo = listFriends[position]) {
                        dynamicUserInfo = listFriends[position]
                        clickUserRequestPanel = true
                    }
                }
            }
        }else {
            //region Кнопка добавления
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Challenge your friends!", fontSize = dynamicFontSize.sp)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(dynamicRowWidth.dp)
                        .clip(RoundedCornerShape(14))
                        .background(color = PinkAppColor)
                        .border(
                            width = 1.dp,
                            color = PinkAppColor,
                            shape = RoundedCornerShape(14)
                        )
                        .noRippleClickable { onClickButtonState = true }
                ){
                    Text(
                        text = "Add Friends",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    )
                }
            }
            //endregion
        }

    }

}