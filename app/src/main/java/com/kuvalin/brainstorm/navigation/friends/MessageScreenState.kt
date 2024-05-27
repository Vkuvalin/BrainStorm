package com.kuvalin.brainstorm.navigation.friends

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class MessageScreenState() {

    object PreparingForTheGame : MessageScreenState()
    object WarGameOne : MessageScreenState()
    object WarGameTwo : MessageScreenState()
    object WarGameThree : MessageScreenState()
    object WarGameResults : MessageScreenState()

    companion object {

        private val _warScreenState = MutableStateFlow<MessageScreenState>(PreparingForTheGame)
        val warScreenState = _warScreenState.asStateFlow()

        fun putWarScreenState(warScreenState: MessageScreenState) {
            _warScreenState.value = warScreenState
        }
    }
}