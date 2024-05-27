package com.kuvalin.brainstorm.presentation.viewmodels

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
import com.kuvalin.brainstorm.domain.usecase.DeleteUserRequestFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticsFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetListMessagesUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserRequestsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserUidUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticFBUseCase
import com.kuvalin.brainstorm.domain.usecase.UpdateUserRequestFBUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val getUserRequestsUseCase: GetUserRequestsUseCase,
    private val getUserInfoFBUseCase: GetUserInfoFBUseCase,

    private val getGameStatisticsFBUseCase: GetGameStatisticsFBUseCase,
    private val getWarStatisticFBUseCase: GetWarStatisticFBUseCase,
    private val getFriendsListUseCase: GetFriendsListUseCase,

    private val addFriendUseCase: AddFriendUseCase,
    private val updateUserRequestFBUseCase: UpdateUserRequestFBUseCase,

    private val deleteUserRequestFBUseCase: DeleteUserRequestFBUseCase,


    private val getListMessagesUseCase: GetListMessagesUseCase,
    private val addMessageToFBUseCase: AddMessageToFBUseCase,

    private val getUserUidUseCase: GetUserUidUseCase

): ViewModel() {

    val getUserRequests = getUserRequestsUseCase
    val getUserInfoFB = getUserInfoFBUseCase

    private val addFriend = addFriendUseCase
    private val getGameStatisticsFB = getGameStatisticsFBUseCase
    private val getWarStatisticFB = getWarStatisticFBUseCase

    val getFriendsList = getFriendsListUseCase

    val updateUserRequestFB = updateUserRequestFBUseCase
    val deleteUserRequestFB = deleteUserRequestFBUseCase
    val getUserUid = getUserUidUseCase

    suspend fun addFriend(userInfo: UserInfo, chatId: String) {

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

    // TODO Не забыть продумать архитектуру под модульное использование, не передавая напрямую в @Composable


    // Chat
    /**
    Такой подход позволяет легко менять слои приложения и делает код более модульным и поддерживаемым.
    Кроме того, это обеспечивает более чистую архитектуру и упрощает тестирование.
    */
    val addMessageToFB = addMessageToFBUseCase

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    fun getListMessages(chatId: String) {
        viewModelScope.launch {
            getListMessagesUseCase.invoke(chatId).collect{
                _messages.value = it
            }
        }
    }


    /*
    viewModel.getListMessages("MY8f5pnDYVUTk6mfxyf8NFObgcL2")
    val listMessage by viewModel.messages.collectAsState()
    */

}



