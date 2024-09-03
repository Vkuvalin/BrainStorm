package com.kuvalin.brainstorm.presentation.viewmodels.statistics

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.usecase.GetListGamesStatisticsUseCase
import com.kuvalin.brainstorm.globalClasses.DecAction
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class GamesStatisticsViewModel @Inject constructor(
    private val getListGamesStatisticsUseCase: GetListGamesStatisticsUseCase
): ViewModel() {

    private val _gamesStatistics = MutableStateFlow<List<GameStatistic>>(emptyList())

    suspend fun getListGamesStatistics(userUid: String) {
        UniversalDecorator().executeAsync(
            mainFunc = {
                _gamesStatistics.value = getListGamesStatisticsUseCase.invoke(userUid)
                delay(800)
            },
            beforeActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", true) }),
            afterActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", false) })
        )
    }

    fun getGameStatistic(gameName: String): GameStatistic? {
        return _gamesStatistics.value.firstOrNull { it.gameName == gameName }
    }

}









