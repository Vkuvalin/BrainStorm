package com.kuvalin.brainstorm.presentation.screens.game.games

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import kotlinx.coroutines.delay
import kotlin.math.abs


@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun FlickMaster(
    onBackButtonClick: () -> Unit,
    putActualScope: (gameScope: Int) -> Unit, // TODO Пока костыль для экономии времени ТОЧКА-1
    putGameResult: (countCorrect: Int, countIncorrect: Int, gameScope: Int, internalAccuracy: Float) -> Unit
){

    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(350){ clickNavigation = false } }

    BackHandler {
        clickNavigation = true
        onBackButtonClick()
    }

    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */
    // Настройки отображения стрелки
    var degrees by remember { mutableFloatStateOf(getRandomDegrees()) }
    var arrowFileName by remember { mutableStateOf(getRandomFileName()) }

    // Подсчет перемещения
    var previousOffset: Offset? = null
    val listOffsets = mutableListOf<Offset>()

    // Подсчет результатов
    var countCorrect = 0
    var countIncorrect = 0

    // Для проигрывания звуков
    val context = LocalContext.current
    var countTimer = 1

    /* ########################################################################################## */


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {

                        // Теперь обрабатываем результаты
                        val offsetToDegrees = getOffset(degrees.toInt())
                        val lastX = listOffsets.last().x
                        val lastY = listOffsets.last().y

                        val result = gameResult(degrees, arrowFileName, lastX, offsetToDegrees, lastY)

                        if (result) {
                            MusicPlayer(context = context).playSuccessInGame()
                            countCorrect++
                            putActualScope(53) // TODO Пока костыль для экономии времени ТОЧКА-2
                            // Происходит почему-то двойная рекомпозиция TODO - искать
                        }
                        else {
                            MusicPlayer(context = context).playErrorInGame()
                            countIncorrect++
                            putActualScope(-22) // TODO Пока костыль для экономии времени ТОЧКА-2
                        }

                        // Заверщающий этап (подчищаем/обновляем)
                        listOffsets.clear()
                        degrees = getRandomDegrees()
                        arrowFileName = getRandomFileName()
                        previousOffset = null
                    }
                ) { change, dragAmount ->

                    val absoluteOffset = previousOffset?.plus(dragAmount) ?: dragAmount
                    listOffsets.add(absoluteOffset)
                    previousOffset = absoluteOffset

                    change.consume() // Поглощаем все изменения, чтобы предотвратить прокрутку или другие жесты
                }
            }
            .wrapContentSize(align = Alignment.Center)
    ) {
        AssetImage(fileName = arrowFileName, modifier = Modifier.size(250.dp).rotate(degrees))

        LaunchedEffect(Unit) {
            delay(10000)
            while (countTimer <= 10){
                MusicPlayer(context = context).playTimer()
                delay(1000)
                countTimer++
            }
            MusicPlayer(context = context).playEndOfTheGame()

            putGameResult(
                countCorrect,
                countIncorrect,
                0,
                (countCorrect.toFloat()/(countCorrect + countIncorrect).toFloat())
            )
        }
    }
}



/* ################################# ФУНКЦИИ (вспомогательные) ################################## */
private fun getRandomFileName(): String {
    return listOf("img_blue_arrow.png", "img_red_arrow.png").random()
}

private fun getRandomDegrees(): Float {
    return listOf(0, 90, 180, 270).random().toFloat()
}

private fun getOffset(degrees: Int): List<Int> {
    val directions = mapOf( // Кроме направления тут определяется минимальная длина свайпа
        0 to listOf(-100, 0),
        90 to listOf(0, -100),
        180 to listOf(100, 0),
        270 to listOf(0, 100)
    )
    return directions[degrees]!!
}

//region gameResult - обработка и подсчет результатов
private fun gameResult(
    degrees: Float,
    arrowFileName: String,
    lastX: Float,
    offsetToDegrees: List<Int>,
    lastY: Float
) = when (degrees) {
    0f -> {
        if (arrowFileName == "img_blue_arrow.png") lastX < offsetToDegrees[0]
        else lastX > abs(offsetToDegrees[0])
    }

    90f -> {
        if (arrowFileName == "img_blue_arrow.png") lastY < offsetToDegrees[1]
//        else lastX > abs(offsetToDegrees[1])
        else lastY > abs(offsetToDegrees[1])
    }

    180f -> {
        if (arrowFileName == "img_blue_arrow.png") lastX > offsetToDegrees[0]
        else lastX < (-offsetToDegrees[0])
    }

    270f -> {
        if (arrowFileName == "img_blue_arrow.png") lastY > offsetToDegrees[1]
        else lastY < (-offsetToDegrees[1])
    }

    else -> false
}
//endregion
/* ############################################################################################## */

