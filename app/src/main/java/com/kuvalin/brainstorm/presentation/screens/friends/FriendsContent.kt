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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.dynamicFontSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FriendsContent(
    paddingValues: PaddingValues
){

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: FriendsViewModel = viewModel(factory = component.getViewModelFactory())


    //Button
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicRowWidth = (screenWidth/1.5).toInt()
    var onClickButtonState by remember { mutableStateOf(false) }
    if (onClickButtonState){
        AddFriendsButtonContent(){ onClickButtonState = false }
    }


    // Список друзей
    val listFriendsUserInfo by remember { mutableStateOf(mutableStateListOf<UserInfo>()) }
    val listFriends by remember { mutableStateOf(mutableStateListOf<Friend>()) } // ПОКА ТОЧНО НЕ ЗНАЮ

    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(UserInfo(uid = "123")) } // TODO
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 2) { clickUserRequestPanel = false }
    }



    // TODO Убрать подобное во viewModel и вытащить на уровень выше (и вообще что-то тут не очень написал)
    LaunchedEffect(Unit) {
        val friendsList = viewModel.getFriendsList.invoke()

        if (friendsList != null) {
            for (friend in friendsList){
                withContext(Dispatchers.Main) {
                    listFriendsUserInfo.add(
                        UserInfo(
                            uid = friend.uid,
                            name = friend.name,
                            email = friend.email,
                            avatar = friend.avatar,
                            country = friend.country,
                        )
                    )
                }
                listFriends.add(friend)
            }
        }
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
                    UserRequestOrFriendPanel(userInfo = listFriendsUserInfo[position]) {
                        dynamicUserInfo = listFriendsUserInfo[position]
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
                Text("Challenge your friends!", fontSize = dynamicFontSize(screenWidth, 20f))
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
                        .noRippleClickable {
                            onClickButtonState = true
                        }
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