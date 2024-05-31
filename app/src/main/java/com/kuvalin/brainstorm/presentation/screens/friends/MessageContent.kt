package com.kuvalin.brainstorm.presentation.screens.friends

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.dynamicFontSize
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun MessageContent(
    paddingParent: PaddingValues
){

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: FriendsViewModel = viewModel(factory = component.getViewModelFactory())


    // Список друзей
    val listFriendsUserInfo by remember { mutableStateOf(mutableStateListOf<UserInfo>()) }
    val listChatsInfo by remember { mutableStateOf(mutableStateListOf<ChatInfo>()) }
    val listFriends by remember { mutableStateOf(mutableStateListOf<Friend>()) } // ПОКА ТОЧНО НЕ ЗНАЮ


    // Функция дополнительной информации о юзере
    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(UserInfo(uid = "123")) }
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 2) { clickUserRequestPanel = false }
    }


    // Chat
    var clickChat by remember { mutableStateOf(false) }
    var chatClose by remember { mutableStateOf(false) }
    var dynamicChatInfo by remember { mutableStateOf(Pair(ChatInfo("",""), "")) }

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
                    friend.chatInfo?.let { listChatsInfo.add(it) }// TODO
                }
                listFriends.add(friend)
            }
        }
    }



    /* ########################################################################################## */


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor)
            .padding(top = paddingParent.calculateTopPadding())
    ) {

        AnimatedContent(
            targetState = clickChat,
            transitionSpec = {
                fadeIn(
                    tween(durationMillis = 400, easing = FastOutLinearInEasing), initialAlpha = 0f
                ) togetherWith fadeOut(tween(durationMillis = 0), targetAlpha = 1f)
            }, label = ""
        )
        { chatSelected ->

            if (chatSelected) {
                ChatContent(
                    dynamicChatInfo.second,
                    dynamicChatInfo.first.chatId,
                ) {
                    clickChat = false
                    CoroutineScope(Dispatchers.Default).launch {
                        delay(200) // Бля, чет не придумал ничего лучше. Пока так заткну. Зато красиво
                        chatClose = false
                    }
                }
            } else {
                if (listFriendsUserInfo.size != 0){
                    if (!chatClose) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(listFriendsUserInfo.size) {position ->
                                UserMessagePanel(
                                    userInfo = listFriendsUserInfo[position],
                                    onPressAvatar = {
                                        dynamicUserInfo = listFriendsUserInfo[position]
                                        clickUserRequestPanel = true
                                    },
                                    onPressRightPart = {
                                        dynamicChatInfo = Pair(listChatsInfo[position], listFriendsUserInfo[position].name!!) // TODO !!!!
                                        clickChat = true
                                        chatClose = true
                                    }
                                )
                            }
                        }
                    }

                }else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Text(
                            text = "No new requests have been received.",
                            fontSize = dynamicFontSize(LocalConfiguration.current.screenWidthDp, 20f)
                        )
                    }
                }
            }

        }

    }
}


