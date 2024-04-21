package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.UserINTERNET
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class AddFriendUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(userINTERNET: UserINTERNET) = brainStormRepository.addFriend(userINTERNET)
}