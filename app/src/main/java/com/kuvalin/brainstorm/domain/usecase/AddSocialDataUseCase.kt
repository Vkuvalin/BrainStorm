package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

data class AddSocialDataUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(socialData: SocialData) = brainStormRepository.addSocialData(socialData)
}
