package com.kuvalin.brainstorm.presentation.viewmodels.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.usecase.AddAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.AuthorizationCheckUseCase
import com.kuvalin.brainstorm.domain.usecase.GetAppSettingsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.ResetPasswordUseCase
import com.kuvalin.brainstorm.domain.usecase.SingInUseCase
import com.kuvalin.brainstorm.domain.usecase.SingOutFBUseCase
import com.kuvalin.brainstorm.domain.usecase.SingUpUseCase
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MenuViewModel @Inject constructor(
    private val addAppSettingsUseCase: AddAppSettingsUseCase,
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val singInUseCase: SingInUseCase,
    private val singUpUseCase: SingUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val authorizationCheckUseCase: AuthorizationCheckUseCase,
    private val singOutFBUseCase: SingOutFBUseCase
): ViewModel() {


    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    private val _appSettings = MutableStateFlow(AppSettings(musicState = true, vibrateState = true))
    val appSettings: StateFlow<AppSettings> = _appSettings

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail

    private val _userPassword = MutableStateFlow("")
    val userPassword: StateFlow<String> = _userPassword

    private val _authState = MutableStateFlow(false)
    val authState: StateFlow<Boolean> = _authState
    /* ########################################################################################## */



    /* ###################################### ИНИЦИАЛИЗАЦИЯ ##################################### */
    init {
        viewModelScope.launch {
            loadAppSettings()
            checkAuthorization()
        }
    }

    private suspend fun loadAppSettings() {
        val settings = getAppSettingsUseCase.invoke()
        _appSettings.value = settings
    }

    private suspend fun checkAuthorization() {
        _authState.value = authorizationCheckUseCase.invoke()
        if (_authState.value) {
            _userName.value = getUserInfoUseCase.invoke()?.name ?: ""
        }
    }
    /* ########################################################################################## */



    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */

    // Settings
    fun updateAppSettings(
        musicState: Boolean = appSettings.value.musicState,
        vibrationState: Boolean = appSettings.value.vibrateState
    ) {
        AppSettings(id = GlobalConstVal.UNDEFINED_ID,musicState = musicState,vibrateState = vibrationState).also {
            _appSettings.value = it
            viewModelScope.launch { addAppSettingsUseCase.invoke(it) }
        }
    }



    fun signIn(context: Context) {
        viewModelScope.launch {
            val result = singInUseCase.invoke(userEmail.value, userPassword.value)
            _authState.value = result.first
            withContext(Dispatchers.Main) {
                Toast.makeText(context, result.second, Toast.LENGTH_LONG).show()
            }
            if (authState.value) { _userName.value = getUserInfoUseCase.invoke()?.name ?: "" }
        }
    }

    fun signUp(context: Context) {
        viewModelScope.launch {

            val result = singUpUseCase.invoke(userEmail.value, userPassword.value)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, result.second, Toast.LENGTH_LONG).show()
            }
            if (result.first) { signIn(context) }
        }
    }

    fun resetPassword(context: Context) {
        viewModelScope.launch {
            val result = resetPasswordUseCase.invoke(userEmail.value)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, result.second, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            singOutFBUseCase.invoke()
            _userName.value = ""
            _authState.value = false
        }
    }

    /* ########################################################################################## */



    /* ############# 🟡 ################ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############# 🟡 ############### */
    fun updateUserEmail(email: String) { _userEmail.value = email }
    fun updateUserPassword(password: String) { _userPassword.value = password }

    fun playChoiceClickSound(context: Context) {
        viewModelScope.launch(Dispatchers.Default) {
            MusicPlayer(context = context).playChoiceClick()
        }
    }
    /* ########################################################################################## */

}





