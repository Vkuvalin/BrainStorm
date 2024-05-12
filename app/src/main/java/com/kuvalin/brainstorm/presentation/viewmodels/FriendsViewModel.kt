package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserRequestsUseCase
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val getUserRequestsUseCase: GetUserRequestsUseCase,
    private val getUserInfoFBUseCase: GetUserInfoFBUseCase
): ViewModel() {

    val getUserRequests = getUserRequestsUseCase
    val getUserInfoFB = getUserInfoFBUseCase
}