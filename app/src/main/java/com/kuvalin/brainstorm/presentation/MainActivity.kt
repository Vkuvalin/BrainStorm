package com.kuvalin.brainstorm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kuvalin.brainstorm.presentation.screens.mainmenu.MainScreen
import com.kuvalin.brainstorm.presentation.screens.menu.MenuScreen
import com.kuvalin.brainstorm.presentation.screens.welcome.WelcomeScreen
import com.kuvalin.brainstorm.ui.theme.BrainStormTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BrainStormTheme {

                var runMainMenu by remember { mutableStateOf(true) }

//                BrainLoading()
                MainScreen()
//                MainMenuScreen()

//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                ) {
//                    AnimatedContent(
//                        targetState = runMainMenu,
//                        transitionSpec = {
//                            fadeIn(tween(durationMillis = 2000)) with fadeOut(tween(durationMillis = 2000))
//                        }, label = ""
//                    )
//                    { shouldLaunchFirstScreen ->
//
//                        if (shouldLaunchFirstScreen) {
//
//                            // Дальше сюда привязать загрузку
//                            WelcomeScreen(delayMilsLoading = 5000) { runMainMenu = !runMainMenu }
//
//                        } else {
//
//                            MainScreen()
//
//                        }
//
//                    }
//                }

            }
        }

    }
}
