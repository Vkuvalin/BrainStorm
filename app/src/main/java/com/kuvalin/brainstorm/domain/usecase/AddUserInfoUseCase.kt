package com.kuvalin.brainstorm.domain.usecase


import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class AddUserInfoUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(userInfo: UserInfo) {
        return brainStormRepository.addUserInfo(userInfo)
    }
}