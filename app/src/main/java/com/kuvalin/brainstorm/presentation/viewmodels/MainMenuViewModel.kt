package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.AddAppCurrencyUseCase
import com.kuvalin.brainstorm.domain.usecase.AddAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.AddSocialDataUseCase
import com.kuvalin.brainstorm.domain.usecase.AddUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.AuthorizationCheckUseCase
import com.kuvalin.brainstorm.domain.usecase.GetAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticUseCase
import com.kuvalin.brainstorm.domain.usecase.GetSocialDataUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.ResetPasswordUseCase
import com.kuvalin.brainstorm.domain.usecase.SingInUseCase
import com.kuvalin.brainstorm.domain.usecase.SingUpUseCase
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(
//    private val addAppCurrencyUseCase: AddAppCurrencyUseCase, // Хуй пока знает, где я это буду использовать
    private val addAppSettingsUseCase: AddAppSettingsUseCase,
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val addUserInfoUseCase: AddUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val addSocialDataUseCase: AddSocialDataUseCase,
    private val getSocialDataUseCase: GetSocialDataUseCase,

    private val singInUseCase: SingInUseCase,
    private val singUpUseCase: SingUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val authorizationCheckUseCase: AuthorizationCheckUseCase

    // Вместо этой херни нужно в data нужно делать подсчет какой-то и выдавать параметры для графика
//    private val getGameStatisticUseCase: GetGameStatisticUseCase

    // Потом буду подводить
//    private val getAppCurrencyUseCase: AddAppCurrencyUseCase


): ViewModel() {

    val addAppSettings = addAppSettingsUseCase
    val getAppSettings = getAppSettingsUseCase
    val addUserInfo = addUserInfoUseCase
    val getUserInfo = getUserInfoUseCase
    val addSocialData = addSocialDataUseCase
    val getSocialData = getSocialDataUseCase

    val singIn = singInUseCase
    val singUp = singUpUseCase
    val resetPassword = resetPasswordUseCase
    val authorizationCheck = authorizationCheckUseCase


}