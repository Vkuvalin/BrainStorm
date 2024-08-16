package com.kuvalin.brainstorm.presentation.viewmodels.main

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.AddAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.AuthorizationCheckUseCase
import com.kuvalin.brainstorm.domain.usecase.GetAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.ResetPasswordUseCase
import com.kuvalin.brainstorm.domain.usecase.SingInUseCase
import com.kuvalin.brainstorm.domain.usecase.SingUpUseCase
import javax.inject.Inject

/**
 * Устарел, но пока не удаляю.
 */
class MainMenuViewModel @Inject constructor(
    private val addAppSettingsUseCase: AddAppSettingsUseCase,
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,

    private val singInUseCase: SingInUseCase,
    private val singUpUseCase: SingUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val authorizationCheckUseCase: AuthorizationCheckUseCase

): ViewModel() {

    val addAppSettings = addAppSettingsUseCase
    val getAppSettings = getAppSettingsUseCase
    val getUserInfo = getUserInfoUseCase

    val singIn = singInUseCase
    val singUp = singUpUseCase
    val resetPassword = resetPasswordUseCase
    val authorizationCheck = authorizationCheckUseCase

}



