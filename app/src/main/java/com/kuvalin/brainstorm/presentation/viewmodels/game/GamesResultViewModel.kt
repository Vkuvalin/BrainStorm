package com.kuvalin.brainstorm.presentation.viewmodels.game

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.AddGameResultUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticUseCase
import javax.inject.Inject

class GamesResultViewModel @Inject constructor(
    private val addGameResultUseCase: AddGameResultUseCase,
    private val getGameStatisticUseCase: GetGameStatisticUseCase
): ViewModel() {

    val addGameResult = addGameResultUseCase
    val getGameStatistic = getGameStatisticUseCase

}