package com.kuvalin.brainstorm.navigation.mainmenu.war

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class WarScreenState() {

    object PreparingForTheGame : WarScreenState()
    object WarGameOne : WarScreenState()
    object WarGameTwo : WarScreenState()
    object WarGameThree : WarScreenState()
    object WarGameResults : WarScreenState()

    companion object {

        private val _warScreenState = MutableStateFlow<WarScreenState>(PreparingForTheGame)
        val warScreenState = _warScreenState.asStateFlow()

        fun putWarScreenState(warScreenState: WarScreenState) {
            _warScreenState.value = warScreenState
        }
    }
}