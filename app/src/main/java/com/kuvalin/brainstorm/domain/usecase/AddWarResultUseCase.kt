package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class AddWarResultUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(warResult: WarResult) = brainStormRepository.addWarResult(warResult)
}