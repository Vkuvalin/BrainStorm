package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.AddFriendInGameUseCase
import com.kuvalin.brainstorm.domain.usecase.AddGameResultUseCase
import com.kuvalin.brainstorm.domain.usecase.AddWarResultUseCase
import com.kuvalin.brainstorm.domain.usecase.FindTheGameUseCase
import com.kuvalin.brainstorm.domain.usecase.GetActualOpponentScopeFromWarGameUseCase
import com.kuvalin.brainstorm.domain.usecase.GetScopeFromWarGameUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import com.kuvalin.brainstorm.domain.usecase.UpdateUserScopeInWarGameUseCase
import javax.inject.Inject

class WarViewModel @Inject constructor(
    private val findTheGameUseCase: FindTheGameUseCase,
    private val updateUserScopeInWarGameUseCase: UpdateUserScopeInWarGameUseCase,
    private val getActualOpponentScopeFromWarGameUseCase: GetActualOpponentScopeFromWarGameUseCase,
    private val getScopeFromWarGameUseCase: GetScopeFromWarGameUseCase,

    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserInfoFBUseCase: GetUserInfoFBUseCase,

    private val addGameResultUseCase: AddGameResultUseCase,
    private val addWarResultUseCase: AddWarResultUseCase,

    private val addFriendInGameUseCase: AddFriendInGameUseCase
): ViewModel() {

    val findTheGame = findTheGameUseCase
    val updateUserScope = updateUserScopeInWarGameUseCase
    val getActualOpponentScope = getActualOpponentScopeFromWarGameUseCase
    val getScopeFromWarGame = getScopeFromWarGameUseCase

    val getUserInfo = getUserInfoUseCase
    val getUserInfoFB = getUserInfoFBUseCase

    val addGameResult = addGameResultUseCase
    val addWarResult = addWarResultUseCase

    val addFriendInGame = addFriendInGameUseCase

}