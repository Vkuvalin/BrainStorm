package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetFriendUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String) = brainStormRepository.getFriend(uid)
}