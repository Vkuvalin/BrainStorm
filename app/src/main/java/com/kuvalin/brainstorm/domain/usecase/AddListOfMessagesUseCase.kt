package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class AddListOfMessagesUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {    suspend fun invoke(listOfMessages: ListOfMessages) {
        return brainStormRepository.addListOfMessages(listOfMessages)
    }
}