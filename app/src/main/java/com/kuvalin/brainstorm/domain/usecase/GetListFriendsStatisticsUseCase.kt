package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository

class GetListFriendsStatisticsUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(): List<GameStatistic> = brainStormRepository.getListFriendsStatistics()
}