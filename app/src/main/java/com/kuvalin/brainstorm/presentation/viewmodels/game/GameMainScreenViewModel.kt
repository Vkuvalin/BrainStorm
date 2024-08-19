package com.kuvalin.brainstorm.presentation.viewmodels.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameMainScreenViewModel @Inject constructor(): ViewModel() {

    // StateFlow для управления состоянием экрана
    private val _runGameScreenState = MutableStateFlow(false)
    val runGameScreenState: StateFlow<Boolean> = _runGameScreenState

    // StateFlow для анимации загрузки
    private val _animLoadState = MutableStateFlow(false)
    val animLoadState: StateFlow<Boolean> = _animLoadState


    // Функция для выполнения навигации с задержкой
    fun navigateWithDelay(navigationState: NavigationState, route: String) {
        viewModelScope.launch(Dispatchers.Main) {
            delay(75)
            navigationState.navigateTo(route)
        }
    }


}