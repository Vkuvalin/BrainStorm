package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetWarStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend operator fun invoke(uid: String = ""): WarStatistics? {
        val currentUid = uid.ifEmpty { getUserUidUseCase.invoke() }
        return brainStormRepository.getWarStatistic(currentUid)
    }
}