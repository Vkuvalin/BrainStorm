package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class GetGameStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend fun invoke(uid: String = "", gameName: String): GameStatistic {
        val currentUid = uid.ifEmpty { GlobalStates.userUid.first() }
        return brainStormRepository.getGameStatistic(currentUid, gameName)
    }
}