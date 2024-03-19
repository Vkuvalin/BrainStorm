package com.kuvalin.brainstorm.domain.usecase

import android.net.Uri
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class AddUserPhotoUseCase constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke() = brainStormRepository.addUserPhotoUseCase()
}