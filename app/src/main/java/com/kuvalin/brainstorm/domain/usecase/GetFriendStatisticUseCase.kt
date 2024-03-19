package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository

class GetFriendStatisticUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke() = brainStormRepository.getFriendStatisticUseCase()
}