package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.usecase.AddGameResultUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticUseCase
import javax.inject.Inject

class GamesViewModel @Inject constructor(
    private val addGameResultUseCase: AddGameResultUseCase,
    private val getGameStatisticUseCase: GetGameStatisticUseCase
): ViewModel() {

    val addGameResult = addGameResultUseCase
    val getGameStatistic = getGameStatisticUseCase

}