package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetGameStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend fun invoke(uid: String = "", gameName: String): GameStatistic {
        val currentUid = uid.ifEmpty { getUserUidUseCase.invoke() }
        return brainStormRepository.getGameStatistic(currentUid, gameName)
    }
}