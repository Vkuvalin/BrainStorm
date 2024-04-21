package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetAppSettingsUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(): AppSettings = brainStormRepository.getAppSettings()
}