package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.usecase.FindTheGameUseCase
import javax.inject.Inject

class WarsViewModel @Inject constructor(
    private val findTheGameUseCase: FindTheGameUseCase
): ViewModel() {

    val findTheGame = findTheGameUseCase
}