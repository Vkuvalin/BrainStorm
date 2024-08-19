package com.kuvalin.brainstorm.presentation.screens.game.games

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kuvalin.brainstorm.presentation.animation.BrainLoading


@Composable
fun ColorSwitch(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BrainLoading()
    }
}


