package com.kuvalin.brainstorm.presentation.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.globalClasses.DecAction
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class FriendsStatisticsViewModel @Inject constructor(
    private val getFriendsListUseCase: GetFriendsListUseCase
): ViewModel() {

    private val _friendList = MutableStateFlow<List<Friend>>(emptyList())
    val friendList: StateFlow<List<Friend>> = _friendList

    init {
        viewModelScope.launch {
            loadFriendsDecorator()
        }
    }

    // Таким образом к подгрузке данных будет прикреплена анимация мозга
    private fun loadFriendsDecorator(){
        viewModelScope.launch {
            UniversalDecorator().executeAsync(
                mainFunc = {
                    loadFriendsList()
                    delay(600)
                },
                beforeActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", true) }),
                afterActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", false) })
            )
        }
    }

    // По идеи ещё нужно в обновление сунуть эту функцию
    private fun loadFriendsList() {
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









