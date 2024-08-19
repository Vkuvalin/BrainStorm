package com.kuvalin.brainstorm.presentation.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.domain.usecase.GetListGamesStatisticsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val getWarStatisticUseCase: GetWarStatisticUseCase,
    private val getListGamesStatisticsUseCase: GetListGamesStatisticsUseCase,
    private val getFriendsListUseCase: GetFriendsListUseCase
): ViewModel() {

    val getWarStatistic = getWarStatisticUseCase
    val getListGamesStatistics = getListGamesStatisticsUseCase
    val getFriendList = getFriendsListUseCase

    init {
        viewModelScope.launch {
            loadFriendList()
        }
    }

    private val _friendList = MutableStateFlow<List<Friend>>(emptyList())
    val friendList: StateFlow<List<Friend>> = _friendList

    private suspend fun loadFriendList() {
        val friendList = getFriendsListUseCase.invoke()
        if (friendList != null){ _friendList.value = friendList }
    }

}








