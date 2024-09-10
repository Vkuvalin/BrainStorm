package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend operator fun invoke(): UserInfo? =
        brainStormRepository.getUserInfo(getUserUidUseCase.invoke())
}