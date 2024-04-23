package com.kuvalin.brainstorm.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetGameStatisticUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend fun invoke(uid: String = "", gameName: String): GameStatistic {
        val currentUid = uid.ifEmpty { Firebase.auth.uid ?: "zero_user_uid" }
        return brainStormRepository.getGameStatistic(currentUid, gameName)
    }
}