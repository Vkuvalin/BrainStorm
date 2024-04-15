package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.AddAppCurrencyUseCase
import com.kuvalin.brainstorm.domain.usecase.AddAppSettingsUseCase
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(
    private val addAppCurrencyUseCase: AddAppCurrencyUseCase, // Хуй пока знает, где я
    private val addAppSettingsUseCase: AddAppSettingsUseCase,
    private val

): ViewModel() {
}