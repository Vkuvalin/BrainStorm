package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetUserInfoFBUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String): UserInfo? = brainStormRepository.getUserInfoFB(uid)
}