package com.kuvalin.brainstorm.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(): UserInfo? = brainStormRepository.getUserInfo(Firebase.auth.uid.toString())
}