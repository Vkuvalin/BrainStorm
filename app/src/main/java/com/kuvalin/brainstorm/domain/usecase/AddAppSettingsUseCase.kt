package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository

class AddAppSettingsUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(
        appSettings: AppSettings
    ) = brainStormRepository.addAppSettings(appSettings)
}