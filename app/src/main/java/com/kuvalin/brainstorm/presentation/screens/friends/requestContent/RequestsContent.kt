package com.kuvalin.brainstorm.presentation.screens.friends.requestContent

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.presentation.screens.friends.UserInfoDialog
import com.kuvalin.brainstorm.presentation.screens.friends.UserRequestOrFriendPanel
import com.kuvalin.brainstorm.presentation.viewmodels.friends.RequestContentViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor

@SuppressLint("MutableCollectionMutableState")
@Composable
fun RequestsContent(
    paddingParent: PaddingValues
){

    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: RequestContentViewModel = viewModel(factory = component.getViewModelFactory())

    // Анимация мозга
    val animBrainLoadState by GlobalStates.animBrainLoadState.collectAsState()

    // Список пользователей
    val listUsers by viewModel.listUsers.collectAsState()


    // Функция дополнительной информации о юзере
    val dynamicUserInfo by viewModel.dynamicUserInfo.collectAsState()
    val clickUserRequestPanel by viewModel.clickUserRequestPanel.collectAsState()
    if (clickUserRequestPanel){
        UserInfoDialog(
            userInfo = dynamicUserInfo.first,
            sender = dynamicUserInfo.second,
            chatId = dynamicUserInfo.third,
            type = 1
        ) {
            viewModel.loadUserRequestsDecorator()
            viewModel.onUserRequestPanelDismiss()
        }
    }

    /* ########################################################################################## */


    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor)
            .padding(top = paddingParent.calculateTopPadding())
    ) {

        if (listUsers.isNotEmpty() && !animBrainLoadState){
            DisplayListRequests(listUsers, viewModel)
        }else {
            if (!animBrainLoadState){ RequestsNotFound() }
        }
    }
    /* ########################################################################################## */

}


/* ############# 🟡 ################ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############# 🟡 ############### */
//region DisplayListRequests
@Composable
private fun DisplayListRequests(
    listUsers: List<Triple<UserInfo, Boolean, String>>,
    viewModel: RequestContentViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(listUsers.size) { position ->
            UserRequestOrFriendPanel(userInfo = listUsers[position].first) {
                viewModel.onUserRequestPanelClick(listUsers[position])
            }
        }
    }
}
//endregion
//region RequestsNotFound
@Composable
private fun RequestsNotFound() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No new requests have been received.",
            fontSize = DynamicFontSize(LocalConfiguration.current.screenWidthDp, 20f)
        )
    }
}
//endregion
/* ########################################################################################## */







