package com.kuvalin.brainstorm.globalClasses.presentation


import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.kuvalin.brainstorm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MusicPlayer(context: Context) {

    private val errorInGame: MediaPlayer = MediaPlayer.create(context, R.raw.error_game)
    private val successInGame: MediaPlayer = MediaPlayer.create(context, R.raw.success_game)
    private val timer: MediaPlayer = MediaPlayer.create(context, R.raw.timer)
    private val endOfTheGame: MediaPlayer = MediaPlayer.create(context, R.raw.end_of_the_game)
    private val choiceStartGame: MediaPlayer = MediaPlayer.create(context, R.raw.choice_start_game)
    private val changeNavigation: MediaPlayer = MediaPlayer.create(context, R.raw.change_navigation)
    private val choiceClick: MediaPlayer = MediaPlayer.create(context, R.raw.choice_click)

    fun release() {
        errorInGame.release()
        successInGame.release()
        timer.release()
        endOfTheGame.release()
        choiceStartGame.release()
        changeNavigation.release()
        choiceClick.release()
    }

    fun playErrorInGame() { CoroutineScope(Dispatchers.Default).launch {errorInGame.start() } }
    fun playSuccessInGame() { CoroutineScope(Dispatchers.Default).launch {successInGame.start() } }
    fun playTimer() { CoroutineScope(Dispatchers.Default).launch {timer.start() } }
    fun playEndOfTheGame() { CoroutineScope(Dispatchers.Default).launch {endOfTheGame.start() } }
    fun playChoiceStartGame() { CoroutineScope(Dispatchers.Default).launch {choiceStartGame.start() } }
    fun playChangeNavigation() { CoroutineScope(Dispatchers.Default).launch {changeNavigation.start() } }
    fun playChoiceClick() { CoroutineScope(Dispatchers.Default).launch {choiceClick.start() } }

    //region Старые версии
    //    fun playErrorInGame(): Boolean { return !(coroutineScope.async {errorInGame.start() }.isCompleted) }
//    fun playSuccessInGame(): Boolean { return !(coroutineScope.async {successInGame.start() }.isCompleted) }
//    fun playTimer(): Boolean { return !(coroutineScope.async {timer.start() }.isCompleted) }
//    fun playEndOfTheGame(): Boolean { return !(coroutineScope.async {endOfTheGame.start() }.isCompleted) }
//    fun playChoiceStartGame(): Boolean { return !(coroutineScope.async {choiceStartGame.start() }.isCompleted) }
//    fun playChangeNavigation(): Boolean { return !(coroutineScope.async {changeNavigation.start() }.isCompleted) }
//    fun playStartApp(): Boolean { return !(coroutineScope.async {startApp.start() }.isCompleted) }
//    fun playChoiceClick(): Boolean { return !(coroutineScope.async {choiceClick.start() }.isCompleted) }

//    fun playErrorInGame() {errorInGame.start()}
//    fun playSuccessInGame() {successInGame.start()}
//    fun playTimer() {timer.start()}
//    fun playEndOfTheGame() {endOfTheGame.start()}
//    fun playChoiceStartGame() {choiceStartGame.start()}
//    fun playChangeNavigation() {changeNavigation.start()}
//    fun playStartApp() {startApp.start()}
//    fun playChoiceClick() {choiceClick.start()}
    //endregion

}


//@Composable
//fun rememberMusicPlayer(): MusicPlayer {
//    val context = LocalContext.current
////    val scope = rememberCoroutineScope()
//    val musicPlayer = remember(context) { MusicPlayer(context) }
//    DisposableEffect(key1 = Unit) { onDispose { musicPlayer.release() } }
//    return musicPlayer
//}