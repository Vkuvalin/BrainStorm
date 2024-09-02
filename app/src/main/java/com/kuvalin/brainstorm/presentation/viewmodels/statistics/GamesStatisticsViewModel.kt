package com.kuvalin.brainstorm.presentation.viewmodels.statistics

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.usecase.GetListGamesStatisticsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class GamesStatisticsViewModel @Inject constructor(
    private val getListGamesStatisticsUseCase: GetListGamesStatisticsUseCase
): ViewModel() {

    private val _gamesStatistics = MutableStateFlow<List<GameStatistic>>(emptyList())

    suspend fun getListGamesStatistics(userUid: String) {
        _gamesStatistics.value = getListGamesStatisticsUseCase.invoke(userUid)
    }

    fun getGameStatistic(gameName: String): GameStatistic? {
        return _gamesStatistics.value.firstOrNull { it.gameName == gameName }
    }

}









