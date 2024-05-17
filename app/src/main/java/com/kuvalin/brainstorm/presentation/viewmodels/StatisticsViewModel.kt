package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.domain.usecase.GetListGamesStatisticsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticUseCase
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val getWarStatisticUseCase: GetWarStatisticUseCase,
    private val getListGamesStatisticsUseCase: GetListGamesStatisticsUseCase,
    private val getFriendsListUseCase: GetFriendsListUseCase
): ViewModel() {

    val getWarStatistic = getWarStatisticUseCase
    val getListGamesStatistics = getListGamesStatisticsUseCase
    val getFriendsList = getFriendsListUseCase

}








