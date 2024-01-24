package com.kuvalin.brainstorm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kuvalin.brainstorm.navigation.MainScreen
import com.kuvalin.brainstorm.ui.theme.BrainStormTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BrainStormTheme {

                var runMainMenu by remember { mutableStateOf(true) }
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
