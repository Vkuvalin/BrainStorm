package com.kuvalin.brainstorm.presentation.viewmodels.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.globalClasses.Action
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class FriendsContentViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase
): ViewModel() {

    private val _listFriendsUserInfo = MutableStateFlow<List<UserInfo>>(emptyList())
    val listFriendsUserInfo: StateFlow<List<UserInfo>> = _listFriendsUserInfo

    private val _clickUserRequestPanel = MutableStateFlow(false)
    val clickUserRequestPanel: StateFlow<Boolean> = _clickUserRequestPanel

    private val _dynamicUserInfo = MutableStateFlow(UserInfo(uid = "123"))
    val dynamicUserInfo: StateFlow<UserInfo> = _dynamicUserInfo

    init {
        loadFriendsDecorator()
    }

    // Таким образом к подгрузке данных будет прикреплена анимация мозга
    private fun loadFriendsDecorator(){
        viewModelScope.launch {
            UniversalDecorator().executeAsync(
                mainFunc = {
                    loadFriendList()
                    delay(1500)
                },
                beforeActions = listOf(Action.Execute{ GlobalStates.putScreenState("animBrainLoadState", true) }),
                afterActions = listOf(Action.Execute{ GlobalStates.putScreenState("animBrainLoadState", false) })
            )
        }
    }

    private fun loadFriendList(){
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
            }
        }
    }

    fun onUserRequestPanelClick(userInfo: UserInfo) {
        _dynamicUserInfo.value = userInfo
        _clickUserRequestPanel.value = true
    }

    fun onUserRequestPanelDismiss() {
        _clickUserRequestPanel.value = false
    }
}