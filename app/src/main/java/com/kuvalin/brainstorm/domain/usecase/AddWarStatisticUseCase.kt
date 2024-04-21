package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class AddWarStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(warStatistics: WarStatistics) = brainStormRepository.addWarStatistic(warStatistics)
}