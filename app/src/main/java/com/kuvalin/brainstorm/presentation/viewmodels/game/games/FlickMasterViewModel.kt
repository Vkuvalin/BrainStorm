package com.kuvalin.brainstorm.presentation.viewmodels.game.games

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FlickMasterViewModel @Inject constructor(

): ViewModel() {

    // Timer
    private val _countTimer = MutableStateFlow(1)
    val countTimer: StateFlow<Int> = _countTimer
    fun updateTimer() { _countTimer.value++ }


    // Результаты игры
    private val _countCorrect = mutableStateOf(0)
    val countCorrect: State<Int> = _countCorrect
    private val _countIncorrect = mutableStateOf(0)
    val countIncorrect: State<Int> = _countIncorrect

    fun increaseIncorrect() { _countIncorrect.value++ }
    fun increaseCorrect() { _countCorrect.value++ }
    fun getInternalAccuracy(): Float {
        return (countCorrect.value.toFloat()/(countCorrect.value + countIncorrect.value).toFloat())
    }


    // Логика для сброса данных, после завершения раунда
    fun resetGame() {
        _countCorrect.value = 0
        _countIncorrect.value = 0
        _countTimer.value = 1
    }

}