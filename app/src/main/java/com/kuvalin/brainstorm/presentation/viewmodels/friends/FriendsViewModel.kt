package com.kuvalin.brainstorm.presentation.viewmodels.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.AddFriendUseCase
import com.kuvalin.brainstorm.domain.usecase.AddMessageToFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticsFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetListMessagesUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserUidUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticFBUseCase
import com.kuvalin.brainstorm.domain.usecase.UpdateUserRequestFBUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(

    private val getGameStatisticsFBUseCase: GetGameStatisticsFBUseCase,
    private val getWarStatisticFBUseCase: GetWarStatisticFBUseCase,

    private val addFriendUseCase: AddFriendUseCase,
    private val updateUserRequestFBUseCase: UpdateUserRequestFBUseCase,

    private val getListMessagesUseCase: GetListMessagesUseCase,
    private val addMessageToFBUseCase: AddMessageToFBUseCase,

    private val getUserUidUseCase: GetUserUidUseCase

): ViewModel() {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    val addMessageToFB = addMessageToFBUseCase

    private val addFriend = addFriendUseCase
    private val getGameStatisticsFB = getGameStatisticsFBUseCase
    private val getWarStatisticFB = getWarStatisticFBUseCase

    val updateUserRequestFB = updateUserRequestFBUseCase
    //endregion ################################################################################# */



    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */

    // UserInfoDialog
    fun addFriend(userInfo: UserInfo, chatId: String) {
        viewModelScope.launch {
            addFriend.invoke(
                Friend(
                    uid = userInfo.uid,
                    ownerUid = Firebase.auth.uid ?: "123", //TODO
                    name = userInfo.name,
                    email = userInfo.email,
                    avatar = null, // TODO
                    country = userInfo.country,
                    chatInfo = ChatInfo(userInfo.uid, chatId),
                    gameStatistic = getGameStatisticsFB.invoke(userInfo.uid),
                    warStatistics = getWarStatisticFB.invoke(userInfo.uid)
                )
            )
        }
    }


    // ChatContent
    private val _listMessage = MutableStateFlow<List<Message>>(emptyList())
    val listMessage: StateFlow<List<Message>> = _listMessage

    private val _userUid = MutableStateFlow("")
    val userUid: StateFlow<String> = _userUid


    private fun getListMessages(chatId: String) {
        viewModelScope.launch {
            getListMessagesUseCase.invoke(chatId).collect{
                _listMessage.value = it
            }
        }
    }


    fun initialChatContent(chatId: String){
        viewModelScope.launch {
            getListMessages(chatId)
            _userUid.value = getUserUidUseCase.invoke()
        }
    }

    //endregion ################################################################################# */


    /* ############# 🛑 ###################### ЧУЛАНЧИК ###################### 🛑 ############### */
    // TODO - почитать
//    val listState = rememberLazyListState()
//    val listState = rememberLazyListState(initialFirstVisibleItemIndex = maxOf(listMessage.size - 10, listMessage.size - 1))

//        LaunchedEffect(listMessage.size) { // TODO - почитать
//            listState.animateScrollToItem(listMessage.size)
//        }

//            state = listState, // TODO - почитать
    //endregion ################################################################################# */

}



