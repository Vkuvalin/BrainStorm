package com.kuvalin.brainstorm.presentation.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class FriendsStatisticsViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase
): ViewModel() {

    init {
        viewModelScope.launch {
            loadFriendsList()
        }
    }

    private val _friendList = MutableStateFlow<List<Friend>>(emptyList())
    val friendList: StateFlow<List<Friend>> = _friendList

    // По идеи ещё нужно в обновление сунуть эту функцию
    fun loadFriendsList() {
        viewModelScope.launch {
            _friendList.value = getFriendsListUseCase.invoke() ?: emptyList()
        }
    }

    // Рассчитываем WinRate для друга
    fun calculateWinRate(friend: Friend): Float {
        val wins = friend.warStatistics?.wins ?: 0
        val losses = friend.warStatistics?.losses ?: 0
        return if (wins + losses > 0) wins / (wins + losses).toFloat() else 0f
    }

}









