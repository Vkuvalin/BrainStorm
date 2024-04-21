package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class GetListGamesStatisticsUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String = ""): List<GameStatistic> {
        val currentUid = uid.ifEmpty { GlobalStates.userUid.first() }
        return brainStormRepository.getListGamesStatistics(currentUid)
    }

}