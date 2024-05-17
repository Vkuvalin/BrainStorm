package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class DeleteUserRequestFBUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uidFriend: String) {
        brainStormRepository.deleteUserRequestFB(uidFriend)
    }
}