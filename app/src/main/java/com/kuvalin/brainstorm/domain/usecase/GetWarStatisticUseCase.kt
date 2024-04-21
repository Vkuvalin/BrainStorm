package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class GetWarStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String = ""): WarStatistics {
        val currentUid = uid.ifEmpty { GlobalStates.userUid.first() }
        return brainStormRepository.getWarStatistic(currentUid)
    }
}