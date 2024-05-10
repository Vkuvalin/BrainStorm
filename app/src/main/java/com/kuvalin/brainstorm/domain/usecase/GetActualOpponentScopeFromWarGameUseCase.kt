package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetActualOpponentScopeFromWarGameUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
){
    suspend operator fun invoke(sessionId: String, gameName: String): StateFlow<Int> {
        return brainStormRepository.getActualOpponentScopeFromWarGame(sessionId, gameName)
    }
}