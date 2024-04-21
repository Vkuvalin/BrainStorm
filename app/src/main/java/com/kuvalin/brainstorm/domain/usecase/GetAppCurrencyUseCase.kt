package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class GetAppCurrencyUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke() = brainStormRepository.getAppCurrency()
}



