package com.kuvalin.brainstorm.presentation.viewmodels.game

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.screens.game.SettingsButtonCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GameSettingsViewModel @Inject constructor() : ViewModel() {

    // Должно перекачевать в базу
    private val _selectSButtonState = MutableStateFlow(false)
    val selectSButtonState: StateFlow<Boolean> = _selectSButtonState

    private val _selectAButtonState = MutableStateFlow(true)
    val selectAButtonState: StateFlow<Boolean> = _selectAButtonState

    private val _selectBButtonState = MutableStateFlow(false)
    val selectBButtonState: StateFlow<Boolean> = _selectBButtonState

    private val _selectCButtonState = MutableStateFlow(false)
    val selectCButtonState: StateFlow<Boolean> = _selectCButtonState



    fun selectButtonCategory(category: SettingsButtonCategory, context: Context) {
        MusicPlayer(context = context).playChoiceClick()
        _selectSButtonState.value = category == SettingsButtonCategory.S
        _selectAButtonState.value = category == SettingsButtonCategory.A
        _selectBButtonState.value = category == SettingsButtonCategory.B
        _selectCButtonState.value = category == SettingsButtonCategory.C
    }
}