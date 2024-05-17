package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetFriendsListUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(): List<Friend>? = brainStormRepository.getFriendList()
}