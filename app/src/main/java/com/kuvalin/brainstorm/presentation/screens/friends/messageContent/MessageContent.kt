package com.kuvalin.brainstorm.presentation.screens.friends.messageContent

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
import com.kuvalin.brainstorm.presentation.viewmodels.friends.MessageContentViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor


@Composable
fun MessageContent(
    paddingParent: PaddingValues
){

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: MessageContentViewModel = viewModel(factory = component.getViewModelFactory())

    // Анимация мозга
    val animBrainLoadState by GlobalStates.animBrainLoadState.collectAsState()

    // Список друзей
    val listFriendsUserInfo by viewModel.listFriendsUserInfo.collectAsState()

    // Chat
    val clickChat by viewModel.clickChat.collectAsState()
    val chatClose by viewModel.chatClose.collectAsState()
    val dynamicChatInfo by viewModel.dynamicChatInfo.collectAsState()


    // Функция дополнительной информации о юзере
    val clickUserRequestPanel by viewModel.clickUserRequestPanel.collectAsState()
    val dynamicUserInfo by viewModel.dynamicUserInfo.collectAsState()
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 2) { viewModel.onUserRequestPanelDismiss() }
    }

    //endregion ################################################################################# */



    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */

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
                ChatContent(dynamicChatInfo.second, dynamicChatInfo.first.chatId) {
                    viewModel.onChatClosed()
                }
            } else {
                if (listFriendsUserInfo.isNotEmpty() && !animBrainLoadState){
                    DisplayListChats(chatClose, listFriendsUserInfo, viewModel)
                }else {
                    if (!animBrainLoadState){ MessagesNotFound() }
                }
            }

        }

    }

    //endregion ################################################################################# */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region DisplayListChats
@Composable
private fun DisplayListChats(
    chatClose: Boolean,
    listFriendsUserInfo: List<UserInfo>,
    viewModel: MessageContentViewModel
) {
    if (!chatClose) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(listFriendsUserInfo.size) { position ->
                UserMessagePanel(
                    userInfo = listFriendsUserInfo[position],
                    onPressAvatar = {
                        viewModel.onUserRequestPanelClick(listFriendsUserInfo[position])
                    },
                    onPressRightPart = {
                        viewModel.onChatSelected(
                            viewModel.listChatsInfo.value[position],
                            listFriendsUserInfo[position].name ?: ""
                        )
                    }
                )
            }
        }
    }
}
//endregion
//region MessagesNotFound
@Composable
private fun MessagesNotFound() {
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
//endregion ################################################################################# */



