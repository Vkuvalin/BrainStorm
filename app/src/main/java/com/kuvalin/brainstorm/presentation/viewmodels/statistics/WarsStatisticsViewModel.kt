package com.kuvalin.brainstorm.presentation.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.domain.usecase.GetListGamesStatisticsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserUidUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticUseCase
import com.kuvalin.brainstorm.globalClasses.DecAction
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



class WarsStatisticsViewModel @Inject constructor(
    private val getWarStatisticUseCase: GetWarStatisticUseCase,
    private val getUserUidUseCase: GetUserUidUseCase
): ViewModel() {

    val getUserUid = getUserUidUseCase

    private val _wins = MutableStateFlow(0)
    val wins: StateFlow<Int> get() = _wins

    private val _losses = MutableStateFlow(0)
    val losses: StateFlow<Int> get() = _losses

    private val _draws = MutableStateFlow(0)
    val draws: StateFlow<Int> get() = _draws

    private val _highestScore = MutableStateFlow(0)
    val highestScore: StateFlow<Int> get() = _highestScore

    private val _winRate = MutableStateFlow(0f)
    val winRate: StateFlow<Float> get() = _winRate


    fun loadWarStatistics(currentUserUid: String) {
        viewModelScope.launch {

            UniversalDecorator().executeAsync(
                mainFunc = {
                    getWarStatisticUseCase.invoke(currentUserUid)?.let { warStatistics ->
                        _wins.value = warStatistics.wins
                        _losses.value = warStatistics.losses
                        _draws.value = warStatistics.draws
                        _winRate.value = calculateWinRate(warStatistics.wins, warStatistics.losses)
                        _highestScore.value = warStatistics.highestScore
                    }
                    delay(600)
                },
                beforeActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", true) }),
                afterActions = listOf(DecAction.Execute{ GlobalStates.putScreenState("animBrainLoadState", false) })
            )

        }
    }

    private fun calculateWinRate(wins: Int, losses: Int): Float {
        return if (wins + losses > 0) wins / (wins + losses).toFloat() else 0f
    }

}