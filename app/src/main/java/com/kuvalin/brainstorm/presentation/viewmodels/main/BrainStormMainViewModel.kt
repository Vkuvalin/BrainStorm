package com.kuvalin.brainstorm.presentation.viewmodels.main

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class BrainStormMainViewModel @Inject constructor() : ViewModel() {


    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    // Состояния экрана (кнопок верхнего TopAppBar)
    private val _clickOnShareState = MutableStateFlow(false)
    val clickOnShareState: StateFlow<Boolean> = _clickOnShareState

    private val _clickOnAddFriendsButton = MutableStateFlow(false)
    val clickOnAddFriendsButton: StateFlow<Boolean> = _clickOnAddFriendsButton

    private val _clickOnAddQuestionButton = MutableStateFlow(false)
    val clickOnAddQuestionButton: StateFlow<Boolean> = _clickOnAddQuestionButton

    private val _clickOnGameSettingsButton = MutableStateFlow(false)
    val clickOnGameSettingsButton: StateFlow<Boolean> = _clickOnGameSettingsButton


    // Параметры иконок (в dp)
    val sizeIcon = 35
    val paddingBottomIcon = 3
    val paddingTopIcon = 3
    val strokeWidthIcon = 3
    val correctionValueHeightBorder = 1

    // Параметры разделителей
    val separatorHeight = (sizeIcon * 0.8).toInt()
    val separatorColor = Color.Gray
    val separatorWidth = 1

    /* ########################################################################################## */



    /* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */

    // ###################### Функции для изменения состояний
    fun toggleShareState(value: Boolean) { _clickOnShareState.value = value }
    fun toggleAddFriendsButton(value: Boolean) { _clickOnAddFriendsButton.value = value }
    fun toggleAddQuestionButton(value: Boolean) { _clickOnAddQuestionButton.value = value }
    fun toggleGameSettingsButton(value: Boolean) { _clickOnGameSettingsButton.value = value }
    // ######################

    /* ########################################################################################## */

}