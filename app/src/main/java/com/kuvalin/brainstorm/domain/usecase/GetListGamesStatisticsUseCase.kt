package com.kuvalin.brainstorm.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetListGamesStatisticsUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String = ""): List<GameStatistic> {
        val currentUid = uid.ifEmpty { Firebase.auth.uid ?: "zero_user_uid" }
        return brainStormRepository.getListGamesStatistics(currentUid)
    }

}