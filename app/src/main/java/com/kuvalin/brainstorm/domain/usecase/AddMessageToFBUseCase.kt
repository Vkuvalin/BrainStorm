package com.kuvalin.brainstorm.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

data class AddMessageToFBUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(message: String, chatId: String){
        val currentUid = Firebase.auth.uid ?: "zero_user_uid" // TODO вообще потом продумать, как лучше обойтись с uid
        val newMessage = Message( senderUid = currentUid, text = message, timestamp = System.currentTimeMillis()  )
        brainStormRepository.sendMessageToFirestore(newMessage, chatId)
    }
}