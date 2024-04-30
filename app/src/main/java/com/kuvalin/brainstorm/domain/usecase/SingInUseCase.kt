package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class SingInUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
){
    suspend operator fun invoke(email: String, password: String): Pair<Boolean, String> {
        return brainStormRepository.singIn(email, password)
    }
}