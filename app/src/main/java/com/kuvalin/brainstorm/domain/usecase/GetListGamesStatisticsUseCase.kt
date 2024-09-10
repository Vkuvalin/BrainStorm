package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetListGamesStatisticsUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend operator fun invoke(uid: String = ""): List<GameStatistic> {
        val currentUid = uid.ifEmpty { getUserUidUseCase.invoke() }
        return brainStormRepository.getListGamesStatistics(currentUid)
    }

}