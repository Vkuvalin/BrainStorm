package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class AddGameStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(gameStatistic: GameStatistic) = brainStormRepository.addGameStatistic(gameStatistic)
}