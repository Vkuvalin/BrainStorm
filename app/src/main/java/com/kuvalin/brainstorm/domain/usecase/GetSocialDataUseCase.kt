package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetSocialDataUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend operator fun invoke(): SocialData? =
        brainStormRepository.getSocialData(getUserUidUseCase.invoke())
}



