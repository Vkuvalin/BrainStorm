package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class UpdateUserRequestFBUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uidFriend: String, friendState: Boolean) {
        brainStormRepository.updateUserRequestFB(uidFriend, friendState)
    }
}