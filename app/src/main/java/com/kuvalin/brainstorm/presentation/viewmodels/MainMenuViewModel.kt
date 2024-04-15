package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.AddAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.AddUserUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserUseCase
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(
//    private val addAppCurrencyUseCase: AddAppCurrencyUseCase, // Хуй пока знает, где я это буду использовать
    private val addAppSettingsUseCase: AddAppSettingsUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val getUserUseCase: GetUserUseCase,


): ViewModel() {

}