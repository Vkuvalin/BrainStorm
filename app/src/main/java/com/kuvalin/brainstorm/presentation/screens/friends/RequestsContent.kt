package com.kuvalin.brainstorm.presentation.screens.friends

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.dynamicFontSize
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("MutableCollectionMutableState")
@Composable
fun RequestsContent(
    paddingParent: PaddingValues
){

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: FriendsViewModel = viewModel(factory = component.getViewModelFactory())


    // Список пользователей
    val listUsers by remember { mutableStateOf(mutableStateListOf<Triple<UserInfo, Boolean, String>>()) }

    // Функция дополнительной информации о юзере
    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(Triple(UserInfo(uid = "123"), false, "")) }
    if (clickUserRequestPanel){
        UserInfoDialog(
            userInfo = dynamicUserInfo.first,
            sender = dynamicUserInfo.second,
            chatId = dynamicUserInfo.third,
            type = 1
        ) { clickUserRequestPanel = false }
    }


    LaunchedEffect(Unit) {

        val listUserRequest = viewModel.getUserRequests.invoke()
        if (listUserRequest != null) {

            for (user in listUserRequest){
                if (user.answerState && !user.friendState) {
                    viewModel.deleteUserRequestFB.invoke(user.uid)
                } else if (user.answerState && user.friendState) { // TODO По факту данное должно происходить во FriendMainScreen (но пусть пока тут остается)
                    val userInfo = viewModel.getUserInfoFB.invoke(uid = user.uid)
                    if (userInfo != null) {
                        viewModel.addFriend(userInfo, user.chatId)
                    }
                }else {
                    val userInfo = viewModel.getUserInfoFB.invoke(uid = user.uid)
                    if (userInfo != null) { // TODO переделать, чтобы не был null (имя подставлять или запрашивать)

                        // TODO Думаю, можно было бы найти лучшее решение с передачей чата, но глобально вроде не критично
                        withContext(Dispatchers.Main) { listUsers.add(Triple(userInfo, user.sender, user.chatId)) }
                        // И тогда подумать: не лучше ли тогда сразу обновлять список целиком?
                        // Предварительно собрав его тут

                        // ОПРЕДЕЛЕННО ЛУЧШЕ TODO
                    }
                }
            }

        }

    }
    // TODO Как только я захожу на экран, сначала должна проигрываться анимация мозга-ожидания
    // TODO ВОТ ТУТ ВСТАВИТЬ АНИМАЦИЮ, которая будет проигрываться, пока не будет закончена загрузка

    /* ########################################################################################## */

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))
            .padding(top = paddingParent.calculateTopPadding())
    ) {

        if (listUsers.size != 0){
            LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(listUsers.size) {position ->
                    UserRequestOrFriendPanel(userInfo = listUsers[position].first) {
                        dynamicUserInfo = listUsers[position]
                        clickUserRequestPanel = true
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