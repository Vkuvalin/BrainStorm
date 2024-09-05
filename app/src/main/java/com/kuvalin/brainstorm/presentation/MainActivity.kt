package com.kuvalin.brainstorm.presentation

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kuvalin.brainstorm.R
import com.kuvalin.brainstorm.globalClasses.DecAction
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.populateResultPaths
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.resultPaths
import com.kuvalin.brainstorm.presentation.animation.BrainLoading
import com.kuvalin.brainstorm.presentation.animation.WelcomeScreen
import com.kuvalin.brainstorm.presentation.screens.BrainStormMainScreen
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.BrainStormTheme
import kotlinx.coroutines.delay



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */

            // Общие
            val context = rememberUpdatedState(LocalContext.current)

            // Прогружена ли анимация
            var animateLoadingEnd by remember { mutableStateOf(false) } // FALSE


            val animBrainLoadState by GlobalStates.animBrainLoadState.collectAsState() // Мини-мозг
            val runGameScreenState by GlobalStates.runGameScreenState.collectAsState() // Пауза в музыке


            //region Музыка
            var playerState by remember { mutableStateOf(true) }
            var resumedApp by remember { mutableStateOf(false) }
            val backgroundMusic = MediaPlayer.create(context.value, R.raw.background_music)
            val observer = remember {
                LifecycleEventObserver { owner, _ ->
                    when (owner.lifecycle.currentState) {
                        Lifecycle.State.RESUMED -> {
                            if (animateLoadingEnd){
                                backgroundMusic.start()
                                resumedApp = false
                            }
                        }
                        Lifecycle.State.CREATED -> {
                            backgroundMusic.pause()
                            resumedApp = true
                        }
                        else -> {}
                    }
                }
            }
            //endregion
            //endregion ################################################################################# */


            /* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ #################### 🌈 ############# */
            // Наполняет resultPaths при загрузке приложения (т.е. подгружает все фотографии/иконки)
            LaunchedEffect(Unit) {
                UniversalDecorator().execute(
                    mainFunc = { populateResultPaths(context.value) },
                    afterActions = listOf(DecAction.Log("$resultPaths")),
                    subLogTag = "Main"
                )
            }
            //endregion ################################################################################# */



            //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
            BrainStormTheme {

                //region Запуск музыки
//                val lifecycle = LocalLifecycleOwner.current.lifecycle
//                DisposableEffect(lifecycle) {
//                    lifecycle.addObserver(observer)
//                    onDispose {
//                        lifecycle.removeObserver(observer)
//                        backgroundMusic.release()
//                    }
//                }
//
//                LaunchedEffect(animateLoadingEnd){
//                    delay(1000)
//                    if (animateLoadingEnd){
//                        while (true) {
//                            if (resumedApp) { delay(1000) }
//                            else {
//                                if (runGameScreenState) {
//                                    if (backgroundMusic.isPlaying) { backgroundMusic.pause() }
//                                }
//                                else {
//                                    if (playerState) {
//                                        backgroundMusic.start()
//                                        playerState = false
//                                    }
//                                    if (!backgroundMusic.isPlaying) { playerState = true }
//                                }
//                            }
//                            delay(1000)
//                        }
//                    }
//                    if (backgroundMusic.isPlaying) { backgroundMusic.pause() }
//                }
                //endregion


//                Column(modifier = Modifier.fillMaxSize()) {
//                    AnimatedContent(
//                        targetState = animateLoadingEnd,
//                        transitionSpec = {
//                            fadeIn(tween(durationMillis = 2000)) with fadeOut(tween(durationMillis = 2000))
//                        }, label = ""
//                    )
//                    { shouldLaunchFirstScreen ->
//
//                        if (!shouldLaunchFirstScreen) {
//                            WelcomeScreen(delayMilsLoading = 5000) { animateLoadingEnd = true }
//                        } else {
//                            BrainStormMainScreen()
//                        }
//
//                    }
//                }

                // Бля, что-то не могу понять, почему теперь она работает лишь за пределами
//                if (animBrainLoadState){ BrainLoading() }


                // Mini version
                Column(modifier = Modifier.fillMaxSize().background(color = BackgroundAppColor)) {
                    BrainStormMainScreen()
                }
                if (animBrainLoadState){ BrainLoading() } // Анимация мозга при загрузке данных

            }
            //endregion ################################################################################# */
        }

    }
}








