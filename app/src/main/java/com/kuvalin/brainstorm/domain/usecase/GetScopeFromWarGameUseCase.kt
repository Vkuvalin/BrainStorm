package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetScopeFromWarGameUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
){
    suspend operator fun invoke(sessionId: String, gameName: String, type: String): Int {
        return brainStormRepository.getScopeFromWarGame(sessionId, gameName, type)
    }
}