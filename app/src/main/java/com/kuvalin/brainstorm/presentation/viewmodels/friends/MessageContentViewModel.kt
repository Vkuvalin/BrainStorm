package com.kuvalin.brainstorm.presentation.viewmodels.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.globalClasses.DecAction
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MessageContentViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase
): ViewModel() {


    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */
    private val _listFriendsUserInfo = MutableStateFlow<List<UserInfo>>(emptyList())
    val listFriendsUserInfo: StateFlow<List<UserInfo>> = _listFriendsUserInfo

    private val _listChatsInfo = MutableStateFlow<List<ChatInfo>>(emptyList())
    val listChatsInfo: StateFlow<List<ChatInfo>> = _listChatsInfo

    private val _clickUserRequestPanel = MutableStateFlow(false)
    val clickUserRequestPanel: StateFlow<Boolean> = _clickUserRequestPanel

    private val _dynamicUserInfo = MutableStateFlow(UserInfo(uid = "123"))
    val dynamicUserInfo: StateFlow<UserInfo> = _dynamicUserInfo

    private val _clickChat = MutableStateFlow(false)
    val clickChat: StateFlow<Boolean> = _clickChat

    private val _chatClose = MutableStateFlow(false)
    val chatClose: StateFlow<Boolean> = _chatClose

    private val _dynamicChatInfo = MutableStateFlow(Pair(ChatInfo("",""), ""))
    val dynamicChatInfo: StateFlow<Pair<ChatInfo, String>> = _dynamicChatInfo
    /* ########################################################################################## */



    /* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ #################### 🌈 ############# */
    init {
        loadFriendsAndChatDecorator()
    }

    // Таким образом к подгрузке данных будет прикреплена анимация мозга
    private fun loadFriendsAndChatDecorator(){
        viewModelScope.launch {
            UniversalDecorator().executeAsync(
                mainFunc = {
                    loadFriendsAndChats()
                    delay(2000)
                },
                beforeActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", true) }),
                afterActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", false) })
            )
        }
    }

    private fun loadFriendsAndChats() {
        viewModelScope.launch {
            val friendsList = getFriendsListUseCase.invoke()
            if (friendsList != null) {
                _listFriendsUserInfo.value = friendsList.map { friend ->
                    UserInfo(
                        uid = friend.uid,
                        name = friend.name,
                        email = friend.email,
                        avatar = friend.avatar,
                        country = friend.country
                    )
                }
                _listChatsInfo.value = friendsList.mapNotNull { it.chatInfo }
            }
        }
    }
    /* ########################################################################################## */



    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */
    fun onUserRequestPanelClick(userInfo: UserInfo) {
        _dynamicUserInfo.value = userInfo
        _clickUserRequestPanel.value = true
    }

    fun onUserRequestPanelDismiss() {
        _clickUserRequestPanel.value = false
    }

    fun onChatSelected(chatInfo: ChatInfo, userName: String) {
        _dynamicChatInfo.value = Pair(chatInfo, userName)
        _clickChat.value = true
        _chatClose.value = true
    }

    fun onChatClosed() {
        viewModelScope.launch {
            _clickChat.value = false
            delay(200) // Небольшая задержка для плавного закрытия чата
            _chatClose.value = false
        }
    }
    /* ########################################################################################## */

}