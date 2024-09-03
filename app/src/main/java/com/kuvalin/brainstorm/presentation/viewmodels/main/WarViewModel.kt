package com.kuvalin.brainstorm.presentation.viewmodels.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.AddFriendInGameUseCase
import com.kuvalin.brainstorm.domain.usecase.AddGameResultUseCase
import com.kuvalin.brainstorm.domain.usecase.AddWarResultUseCase
import com.kuvalin.brainstorm.domain.usecase.FindTheGameUseCase
import com.kuvalin.brainstorm.domain.usecase.GetActualOpponentScopeFromWarGameUseCase
import com.kuvalin.brainstorm.domain.usecase.GetScopeFromWarGameUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.UpdateUserScopeInWarGameUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class WarViewModel @Inject constructor(

    /* -------------------------------- warSearchScreen ------------------------------------------*/
    private val findTheGameUseCase: FindTheGameUseCase,
    private val getScopeFromWarGameUseCase: GetScopeFromWarGameUseCase,
    private val addGameResultUseCase: AddGameResultUseCase,
    private val addWarResultUseCase: AddWarResultUseCase,
    private val addFriendInGameUseCase: AddFriendInGameUseCase,
    /* -------------------------------------------------------------------------------------------*/

    private val getUserInfoUseCase: GetUserInfoUseCase,

    /* ---------------------------------- warGameScreen ------------------------------------------*/
    private val getUserInfoFBUseCase: GetUserInfoFBUseCase,
    private val updateUserScopeInWarGameUseCase: UpdateUserScopeInWarGameUseCase,
    private val getActualOpponentScopeFromWarGameUseCase: GetActualOpponentScopeFromWarGameUseCase,
    /* -------------------------------------------------------------------------------------------*/

): ViewModel() {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */

    val getScopeFromWarGame = getScopeFromWarGameUseCase
    val addGameResult = addGameResultUseCase
    val addWarResult = addWarResultUseCase
    val addFriendInGame = addFriendInGameUseCase

    /* -------------------------------- warSearchScreen ------------------------------------------*/
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    private val _opponentUserName = MutableStateFlow("")
    val opponentUserName: StateFlow<String> get() = _opponentUserName

    private val _waitOpponent = MutableStateFlow(true)
    val waitOpponent: StateFlow<Boolean> get() = _waitOpponent
    /* -------------------------------------------------------------------------------------------*/


    /* ---------------------------------- warGameScreen ------------------------------------------*/
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    private val _userInfoOpponent = MutableStateFlow<UserInfo?>(null)
    val userInfoOpponent: StateFlow<UserInfo?> = _userInfoOpponent

    private val _scopeCyanPlayer = MutableStateFlow(0)
    val scopeCyanPlayer: StateFlow<Int> = _scopeCyanPlayer

    private val _scopePinkPlayer = MutableStateFlow(0)
    val scopePinkPlayer: StateFlow<Int> = _scopePinkPlayer

    private val _round = MutableStateFlow(1)
    val round: StateFlow<Int> = _round

    private val _timer = MutableStateFlow(10)
    val timer: StateFlow<Int> = _timer

    private val _gameState = MutableStateFlow(false)
    val gameState: StateFlow<Boolean> = _gameState
    /* -------------------------------------------------------------------------------------------*/

    //endregion ################################################################################# */




    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */

    /* -------------------------------- warSearchScreen ------------------------------------------*/
    fun fetchUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.invoke()?.name?.let { _userName.value = it }
        }
    }

    fun findAndNavigateToGame(onNavigateToWar: (String, String) -> Unit) { // TODO Вытащить сюда uid
        viewModelScope.launch {
            val result = findTheGameUseCase.invoke()

            if (result.first) {
                getUserInfoFBUseCase.invoke(result.third)?.name?.let { _opponentUserName.value = it }
                _waitOpponent.value = false
                delay(2000)
                onNavigateToWar(result.second, result.third)
            }
        }
    }
    /* -------------------------------------------------------------------------------------------*/


    /* ---------------------------------- warGameScreen ------------------------------------------*/
    fun loadUserInfo(opponentUid: String) {
        viewModelScope.launch {
            getUserInfoUseCase.invoke()?.let { _userInfo.value = it }
            getUserInfoFBUseCase.invoke(opponentUid)?.let { _userInfoOpponent.value = it }
        }
    }

    // Динамическая функция ОТПРАВКИ актуального scope ПОЛЬЗОВАТЕЛЯ
    fun updateUserScope(sessionId: String, gameName: String, points: Int) {
        viewModelScope.launch {
            val newScope = (_scopeCyanPlayer.value + points).coerceAtLeast(0)
            _scopeCyanPlayer.value = newScope
            updateUserScopeInWarGameUseCase(sessionId, gameName, newScope)
        }
    }

    // Динамическая функция ЗАГРУЗКИ актуального scope ОППОНЕНТА
    fun observeOpponentScope(sessionId: String, gameName: String) {
        viewModelScope.launch {
            Log.d("DEBUG-1", "-------------- 5 --------------")
            getActualOpponentScopeFromWarGameUseCase(sessionId, gameName).collect { scope ->
                _scopePinkPlayer.value = scope
                if (_scopePinkPlayer.value < 0) _scopePinkPlayer.value = 0
            }
        }
    }
    /* -------------------------------------------------------------------------------------------*/

    //endregion ################################################################################# */




    //region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */

    // Специализированные функции для управления состояниями
    fun updateTimer(newTimer: Int) {
        _timer.value = newTimer
    }

    fun updateGameState(newGameState: Boolean) {
        _gameState.value = newGameState
    }

    fun updateRound(newRound: Int) {
        _round.value = newRound
    }

    fun updateScopeCyanPlayer(points: Int) {
        _scopeCyanPlayer.value += points
        if (_scopeCyanPlayer.value < 0) _scopeCyanPlayer.value = 0
    }

    fun resetScopePinkPlayer(points: Int) {
        _scopePinkPlayer.value = points
    }

    fun resetScopeCyanPlayer(points: Int) {
        _scopeCyanPlayer.value = points
    }

    //endregion ################################################################################# */

}






