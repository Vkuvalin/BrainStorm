package com.kuvalin.brainstorm.presentation.viewmodels.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.AddFriendUseCase
import com.kuvalin.brainstorm.domain.usecase.DeleteUserRequestFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticsFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserRequestsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticFBUseCase
import com.kuvalin.brainstorm.globalClasses.DecAction
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates.putScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestContentViewModel @Inject constructor(
    private val getUserRequestsUseCase: GetUserRequestsUseCase,
    private val getUserInfoFBUseCase: GetUserInfoFBUseCase,
    private val deleteUserRequestFBUseCase: DeleteUserRequestFBUseCase,
    private val addFriendUseCase: AddFriendUseCase,

    private val getGameStatisticsFBUseCase: GetGameStatisticsFBUseCase,
    private val getWarStatisticFBUseCase: GetWarStatisticFBUseCase,
): ViewModel() {

    private val _listUsers = MutableStateFlow<List<Triple<UserInfo, Boolean, String>>>(emptyList())
    val listUsers: StateFlow<List<Triple<UserInfo, Boolean, String>>> = _listUsers

    private val _clickUserRequestPanel = MutableStateFlow(false)
    val clickUserRequestPanel: StateFlow<Boolean> = _clickUserRequestPanel

    private val _dynamicUserInfo = MutableStateFlow(Triple(UserInfo(uid = "123"), false, ""))
    val dynamicUserInfo: StateFlow<Triple<UserInfo, Boolean, String>> = _dynamicUserInfo


    init {
        loadUserRequestsDecorator()
    }

    // Таким образом к подгрузке данных будет прикреплена анимация мозга
    fun loadUserRequestsDecorator(){
        viewModelScope.launch {
            UniversalDecorator().executeAsync(
                mainFunc = {
                    loadUserRequests()
                    delay(400)
                },
                beforeActions = listOf(DecAction.Execute{ putScreenState("animBrainLoadState", true) }),
                afterActions = listOf(DecAction.Execute{ putScreenState("animBrainLoadState", false) })
            )
        }
    }

    private fun loadUserRequests(){
        viewModelScope.launch {

            val listUserRequest = getUserRequestsUseCase.invoke()
            if (listUserRequest != null) {
                val users = listUserRequest.mapNotNull { user ->
                    when {
                        user.answerState && !user.friendState -> {
                            deleteUserRequestFBUseCase.invoke(user.uid)
                            null
                        }
                        user.answerState && user.friendState -> {
                            getUserInfoFBUseCase.invoke(uid = user.uid)?.let { userInfo ->
                                addFriend(userInfo, user.chatId)
                            }
                            null
                        }
                        else -> {
                            getUserInfoFBUseCase.invoke(uid = user.uid)?.let { userInfo ->
                                Triple(userInfo, user.sender, user.chatId)
                            }
                        }
                    }
                }
                _listUsers.value = users
            }else {
                _listUsers.value = emptyList()
            }

        }
    }

    fun onUserRequestPanelClick(userInfo: Triple<UserInfo, Boolean, String>) {
        _dynamicUserInfo.value = userInfo
        _clickUserRequestPanel.value = true
    }

    fun onUserRequestPanelDismiss() {
        _clickUserRequestPanel.value = false
    }

    private fun addFriend(userInfo: UserInfo, chatId: String) {
        viewModelScope.launch {
            addFriendUseCase.invoke(
                Friend(
                    uid = userInfo.uid,
                    ownerUid = Firebase.auth.uid ?: "123", //TODO
                    name = userInfo.name,
                    email = userInfo.email,
                    avatar = null, // TODO
                    country = userInfo.country,
                    chatInfo = ChatInfo(userInfo.uid, chatId),
                    gameStatistic = getGameStatisticsFBUseCase.invoke(userInfo.uid),
                    warStatistics = getWarStatisticFBUseCase.invoke(userInfo.uid)
                )
            )
        }
    }
}








