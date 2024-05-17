package com.kuvalin.brainstorm.presentation.screens.friends

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.presentation.viewmodels.WarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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


    // Динамический размер текста
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = (screenWidth/19) // == 20.sp
    // TODO Придумать позже универсальную функцию, что будет принимать screenWidth и желаемый результат


    // Список пользователей
    val listUsers by remember { mutableStateOf(mutableStateListOf<UserInfo>()) }

    // Функция дополнительной информации о юзере
    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(UserInfo(uid = "123")) }
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 1) { clickUserRequestPanel = false }
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
                        viewModel.addFriend(userInfo)
                    }
                }else {
                    val userInfo = viewModel.getUserInfoFB.invoke(uid = user.uid)
                    if (userInfo != null) { // TODO переделать, чтобы не был null (имя подставлять или запрашивать)
                        withContext(Dispatchers.Main) { listUsers.add(userInfo) }
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
        Log.d("REQUESTS_CONTENT", " $listUsers <------------------- listUsers222")
        if (listUsers.size != 0){
            Log.d("REQUESTS_CONTENT", " $listUsers <------------------- listUsers333")
            LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(listUsers.size) {position ->
                    UserRequestOrFriendPanel(userInfo = listUsers[position]) {
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
                Text("No new requests have been received.", fontSize = dynamicFontSize.sp)
            }
        }


    }
}