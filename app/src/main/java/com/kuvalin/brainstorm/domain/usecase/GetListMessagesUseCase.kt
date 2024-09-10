package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


data class GetListMessagesUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(chatId: String): StateFlow<List<Message>> =
        brainStormRepository.getListMessages(chatId)
}