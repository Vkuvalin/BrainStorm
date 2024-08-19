package com.kuvalin.brainstorm.globalClasses.presentation


import android.content.Context
import android.media.MediaPlayer
import com.kuvalin.brainstorm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Класс MusicPlayer предоставляет функциональность для управления воспроизведением различных звуков.
 * Звуковые файлы инициализируются лениво и освобождаются автоматически после завершения воспроизведения.
 *
 * @param context Контекст, необходимый для создания объектов MediaPlayer.
 */
class MusicPlayer(context: Context) {

    // Ленивое создание MediaPlayer для каждого звука
    private val errorInGame by lazy { MediaPlayer.create(context, R.raw.error_game) }
    private val successInGame by lazy { MediaPlayer.create(context, R.raw.success_game) }
    private val timer by lazy { MediaPlayer.create(context, R.raw.timer) }
    private val endOfTheGame by lazy { MediaPlayer.create(context, R.raw.end_of_the_game) }
    private val choiceStartGame by lazy { MediaPlayer.create(context, R.raw.choice_start_game) }
    private val changeNavigation by lazy { MediaPlayer.create(context, R.raw.change_navigation) }
    private val choiceClick by lazy { MediaPlayer.create(context, R.raw.choice_click) }

    /**
     * Воспроизводит звук и автоматически освобождает ресурсы MediaPlayer после завершения воспроизведения.
     *
     * @param mediaPlayer Объект MediaPlayer, который должен воспроизвести звук.
     */
    private fun playSoundWithRelease(mediaPlayer: MediaPlayer) {
        mediaPlayer.setOnCompletionListener { it.release() } // Освобождаем ресурсы после завершения
        mediaPlayer.start()
    }

    // Методы воспроизведения звуков
    fun playErrorInGame() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(errorInGame) } }
    fun playSuccessInGame() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(successInGame) } }
    fun playTimer() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(timer) } }
    fun playEndOfTheGame() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(endOfTheGame) } }
    fun playChoiceStartGame() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(choiceStartGame) } }
    fun playChangeNavigation() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(changeNavigation) } }
    fun playChoiceClick() { CoroutineScope(Dispatchers.Default).launch { playSoundWithRelease(choiceClick) } }

}





