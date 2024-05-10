package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class AddFriendInGameUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(sessionId: String) = brainStormRepository.addFriendInGame(sessionId)
}