package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

data class AddMessageToFBUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend operator fun invoke(message: String, chatId: String){
        val currentUid = getUserUidUseCase.invoke()
        val newMessage = Message( senderUid = currentUid, text = message, timestamp = System.currentTimeMillis()  )
        brainStormRepository.sendMessageToFirestore(newMessage, chatId)
    }
}