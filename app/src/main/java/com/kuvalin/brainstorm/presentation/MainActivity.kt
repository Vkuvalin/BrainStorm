package com.kuvalin.brainstorm.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.kuvalin.brainstorm.presentation.animation.BrainLoading
import com.kuvalin.brainstorm.presentation.screens.mainmenu.MainScreen
import com.kuvalin.brainstorm.presentation.screens.mainmenu.profile.ProfileScreenContent
import com.kuvalin.brainstorm.ui.theme.BrainStormTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var runMainMenu by remember { mutableStateOf(true) }

            // Loading
            val scope = CoroutineScope(Dispatchers.IO)
            var refreshState by remember { mutableStateOf(false) }

            BrainStormTheme {

                MainScreen(){
                    refreshState = true
                }


                if (refreshState){
                    BrainLoading()
                    scope.launch {
                        delay(3000)
                        refreshState = false
                    }
                }


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
