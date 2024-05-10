package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class UpdateUserScopeInWarGameUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
){
    suspend operator fun invoke(sessionId: String, gameName: String, scope: Int) {
        return brainStormRepository.updateUserScopeInWarGame(sessionId, gameName, scope)
    }
}