package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class AddAppCurrencyUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(appCurrency: AppCurrency) = brainStormRepository.addAppCurrency(appCurrency)
}