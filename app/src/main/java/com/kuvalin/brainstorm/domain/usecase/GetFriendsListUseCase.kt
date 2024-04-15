package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.User
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository


class GetFriendsListUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(): List<User> = brainStormRepository.getFriendList()
}