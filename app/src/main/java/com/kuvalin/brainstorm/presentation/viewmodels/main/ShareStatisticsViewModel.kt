package com.kuvalin.brainstorm.presentation.viewmodels.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoUseCase
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



class ShareStatisticsViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    init {
        viewModelScope.launch {
            getUserInfoUseCase.invoke()?.name?.let { _userName.value = it }
        }
    }


    fun playChoiceClickSound(context: Context) {
        viewModelScope.launch(Dispatchers.Default) {
            MusicPlayer(context = context).run {
                playChoiceClick()
                delay(3000)
                release()
            }
        }
    }

}
