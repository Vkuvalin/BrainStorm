package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class AddChatInfoUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {    suspend fun invoke(chatInfo: ChatInfo) {
        return brainStormRepository.addChatInfo(chatInfo)
    }
}