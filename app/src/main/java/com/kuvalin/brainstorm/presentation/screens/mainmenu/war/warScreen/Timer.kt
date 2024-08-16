package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kuvalin.brainstorm.presentation.viewmodels.main.WarViewModel
import kotlinx.coroutines.delay


@Composable
fun Timer(timer: Int, viewModel: WarViewModel){
    LaunchedEffect(Unit) {
        var seconds = timer
        while (seconds >= 0){
            viewModel.updateTimer(seconds--)
            delay(1000)
        }
    }
}