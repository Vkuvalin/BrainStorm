package com.kuvalin.brainstorm.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetWarStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String = ""): WarStatistics? {
        val currentUid = uid.ifEmpty { Firebase.auth.uid ?: "zero_user_uid" }
        return brainStormRepository.getWarStatistic(currentUid)
    }
}