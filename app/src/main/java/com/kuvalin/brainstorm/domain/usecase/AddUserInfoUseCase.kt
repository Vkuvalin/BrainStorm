package com.kuvalin.brainstorm.domain.usecase


import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class AddUserInfoUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(userInfo: UserInfo) {
        return brainStormRepository.addUserInfo(userInfo)
    }
}