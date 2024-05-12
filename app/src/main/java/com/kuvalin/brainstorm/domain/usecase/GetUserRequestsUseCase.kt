package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetUserRequestsUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(): List<UserRequest>? = brainStormRepository.getUserRequests()
}