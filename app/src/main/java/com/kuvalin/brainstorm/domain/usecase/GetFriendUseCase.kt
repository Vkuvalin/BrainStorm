package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository

class GetFriendUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String) = brainStormRepository.getFriendUseCase(uid)
}