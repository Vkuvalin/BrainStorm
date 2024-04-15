package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.User
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository

class AddUserUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(user: User) = brainStormRepository.addUser(user)
}