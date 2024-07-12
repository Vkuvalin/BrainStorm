package com.kuvalin.brainstorm.presentation.viewmodels

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.AddSocialDataUseCase
import com.kuvalin.brainstorm.domain.usecase.AddUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.GetSocialDataUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val addUserInfoUseCase: AddUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val addSocialDataUseCase: AddSocialDataUseCase,
    private val getSocialDataUseCase: GetSocialDataUseCase
): ViewModel() {


    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail

    private val _userCountry = MutableStateFlow("")
    val userCountry: StateFlow<String> = _userCountry

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private val _twitter = MutableStateFlow("")
    val twitter: StateFlow<String> = _twitter

    private val _facebookConnectState = MutableStateFlow(false)
    val facebookConnectState: StateFlow<Boolean> = _facebookConnectState

    // Плохая практика вообще uid и аналогичные данные на ui слой вытаскивать
    // Полагаю, лучше работать только на отдачу собственного uid, с подкапотной архитектурой
    private val userUid = Firebase.auth.uid ?: "zero_user_uid"  // TODO не забыть поменять/спрятать
    /* ########################################################################################## */



    /* #################################### ОСНОВНЫЕ ФУНКЦИИ #################################### */
    // Первичная загрузка данных
    init { viewModelScope.launch { loadUserInfo() } }
    private suspend fun loadUserInfo() {
        val userInfo = getUserInfoUseCase.invoke()
        val socialData = getSocialDataUseCase.invoke()

        _userName.value = userInfo?.name ?: ""
        _userEmail.value = userInfo?.email ?: ""
        _userCountry.value = userInfo?.country ?: ""
        _selectedImageUri.value = userInfo?.avatar

        _twitter.value = socialData?.twitter ?: ""
        _facebookConnectState.value = socialData?.facebookConnect ?: false
    }

    // Состояние полей
    fun updateUserName(name: String) { _userName.value = name }
    fun updateUserEmail(email: String) { _userEmail.value = email }
    fun updateUserCountry(country: String) { _userCountry.value = country }
    fun updateTwitter(twitterHandle: String) { _twitter.value = twitterHandle }
    fun updateFacebookConnectState(state: Boolean) { _facebookConnectState.value = state }
    fun updateSelectedImageUri(uri: Uri) { _selectedImageUri.value = uri }


    // Сохранение в базу данных с выводом тоста
    fun updateUserInfoInDatabase(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserInfoUseCase.invoke(
                UserInfo(
                    uid = userUid,
                    name = _userName.value,
                    email = _userEmail.value,
                    avatar = _selectedImageUri.value,
                    country = _userCountry.value
                )
            )
            addSocialDataUseCase.invoke(
                SocialData(
                    uid = userUid,
                    twitter = _twitter.value,
                    facebookConnect = _facebookConnectState.value
                )
            )
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Данные сохранены.", Toast.LENGTH_LONG).show()
            }
        }
    }
    /* ########################################################################################## */


}