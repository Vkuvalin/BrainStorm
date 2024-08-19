package com.kuvalin.brainstorm.presentation.screens.game.games

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Т.к. я сделал, что можно перемещаться между клетками, то можно и шафл вернуть! :)
//region listOfLists
val listOfLists: List<List<Int>> = listOf(
    listOf(2, 0, 3, 0, 1, 3, 0, 3, 0, 3, 3, 0, 0, 0, 1, 0),
    listOf(3, 1, 0, 3, 0, 3, 0, 0, 3, 1, 2, 3, 0, 0, 0, 0),
    listOf(0, 0, 0, 1, 1, 3, 3, 3, 0, 0, 3, 0, 0, 2, 3, 0),
    listOf(3, 0, 1, 0, 3, 3, 0, 3, 0, 0, 1, 0, 2, 3, 0, 0),
    listOf(0, 0, 2, 0, 3, 0, 3, 0, 3, 3, 1, 0, 3, 1, 0, 0),
    listOf(0, 0, 0, 3, 0, 2, 1, 3, 0, 0, 3, 0, 0, 1, 3, 3),
    listOf(1, 0, 3, 1, 0, 0, 0, 2, 3, 3, 0, 3, 0, 3, 0, 0),
    listOf(0, 0, 3, 0, 2, 1, 0, 0, 3, 3, 0, 0, 3, 3, 0, 1),
    listOf(2, 0, 3, 3, 1, 3, 0, 1, 0, 0, 0, 0, 0, 3, 3, 0),
    listOf(0, 0, 3, 0, 3, 2, 3, 3, 0, 0, 1, 0, 3, 0, 0, 1),
    listOf(0, 0, 0, 0, 0, 3, 3, 1, 2, 0, 3, 0, 3, 0, 1, 3),
    listOf(3, 1, 0, 3, 0, 0, 3, 3, 0, 0, 1, 3, 0, 2, 0, 0),
    listOf(1, 1, 0, 0, 0, 0, 3, 3, 0, 0, 0, 3, 2, 3, 0, 3),
    listOf(3, 0, 0, 0, 0, 0, 0, 3, 1, 0, 1, 2, 0, 3, 3, 3),
    listOf(3, 3, 0, 3, 0, 1, 0, 0, 0, 0, 3, 3, 0, 0, 2, 1),
    listOf(2, 0, 0, 3, 1, 0, 3, 0, 0, 0, 0, 1, 3, 3, 3, 0),
    listOf(3, 0, 1, 3, 0, 0, 3, 3, 0, 3, 0, 0, 2, 0, 1, 0),
    listOf(0, 3, 0, 3, 3, 2, 3, 1, 3, 0, 0, 0, 0, 0, 1, 0),
    listOf(0, 3, 3, 0, 1, 0, 3, 0, 0, 0, 0, 2, 3, 1, 0, 3),
    listOf(3, 3, 0, 0, 2, 1, 0, 3, 3, 0, 3, 1, 0, 0, 0, 0),
    listOf(1, 0, 3, 0, 0, 3, 1, 0, 0, 0, 0, 0, 2, 3, 3, 3),
    listOf(3, 2, 0, 0, 1, 0, 0, 3, 0, 0, 3, 3, 3, 0, 1, 0),
    listOf(1, 0, 3, 0, 0, 2, 0, 3, 3, 0, 0, 0, 1, 0, 3, 3),
    listOf(0, 0, 2, 1, 3, 3, 0, 3, 0, 3, 0, 0, 0, 3, 1, 0),
    listOf(0, 0, 0, 1, 3, 0, 0, 2, 0, 0, 3, 3, 3, 1, 0, 3),
    listOf(0, 1, 0, 0, 0, 0, 3, 3, 3, 0, 0, 3, 3, 0, 1, 2),
    listOf(3, 0, 2, 1, 0, 3, 0, 0, 3, 0, 0, 3, 0, 1, 3, 0),
    listOf(2, 0, 0, 0, 3, 0, 0, 1, 3, 3, 0, 3, 3, 1, 0, 0),
    listOf(0, 0, 0, 0, 3, 1, 3, 0, 3, 3, 3, 0, 2, 0, 0, 1),
    listOf(0, 0, 1, 0, 2, 0, 0, 0, 3, 3, 1, 3, 3, 0, 3, 0),
    listOf(3, 3, 3, 0, 0, 1, 0, 0, 0, 3, 0, 2, 1, 0, 0, 3),
    listOf(3, 1, 0, 3, 0, 3, 0, 1, 3, 0, 0, 2, 0, 0, 3, 0),
    listOf(3, 0, 3, 0, 3, 1, 0, 0, 0, 0, 3, 0, 3, 0, 2, 1),
    listOf(3, 3, 0, 0, 2, 3, 0, 0, 0, 0, 0, 1, 0, 1, 3, 3),
    listOf(3, 1, 0, 1, 0, 0, 3, 0, 2, 3, 3, 0, 0, 0, 3, 0)
)
//endregion


@SuppressLint("MutableCollectionMutableState")
@Composable
fun PathToSafety(
    topBarHeight: Int, // Костыль,
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
    val coroutineScope = rememberCoroutineScope()

    // Генерация игрового поля
    var listIndexFirst by remember { mutableStateOf(listOfLists.random().toMutableList()) }
    var listIndexSecond by remember { mutableStateOf(listIndexFirst.map { if (it == 3 || it == 2) 0 else it }) }


    // Контроль этапами игры
    var startStage by remember { mutableStateOf(false) }
    var movingState by remember { mutableStateOf(false) }


    // Отслеживание координат ячеек и перемещения пальца
    var cellBounds by remember { mutableStateOf<Map<Int, Rect>>(emptyMap()) }
    var touchPosition by remember { mutableStateOf(Offset.Zero) }
    var isInCell by remember { mutableStateOf(false) }
    var currentCell by remember { mutableIntStateOf(-1) }

    var listCell by remember { mutableStateOf(mutableListOf<Int>()) }
    var listClickableIndexes by remember { mutableStateOf(mutableListOf<Int>()) }

    val isDraggingMap = remember { mutableMapOf<Int, Boolean>()}


    // Подсчет результатов
    val resultsList by remember { mutableStateOf(mutableListOf<Int>()) }
    var countCorrect = 0
    var countIncorrect = 0

    // Для проигрывания звуков
    val context = LocalContext.current
    var countTimer = 1
    /* ########################################################################################## */


    //region checkTouchPosition
    fun checkTouchPosition(boundsMap: Map<Int, Rect>) {
        if (startStage) {
            for ((index, bounds) in boundsMap) {
                if (touchPosition.x in bounds.left..bounds.right &&
                    touchPosition.y in bounds.top..bounds.bottom
                ) {
                    isInCell = true
                    currentCell = index
                    return
                }
            }
            isInCell = false
            currentCell = -1
        }
    }
    //endregion


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor)
    ) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .weight(3f)
                .wrapContentHeight(Alignment.Bottom)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 40.dp)
                .padding(bottom = 40.dp)
        ) {
            items(listIndexFirst.size) { position ->
                //region GameCell
                GameCell(
                    currentCell = currentCell,
                    movingState = movingState,
                    draggingMap = isDraggingMap,
                    listCell = listCell,
                    listClickableIndexes = listClickableIndexes,
                    listIndexFirst = listIndexFirst,
                    position = position,
                    index = if(!startStage) listIndexFirst[position] else listIndexSecond[position],
                    onLayoutChanged = {layoutCoordinates ->
                        val topLeft = layoutCoordinates.positionInRoot()
                        val bottomRight = Offset(topLeft.x + layoutCoordinates.size.width, topLeft.y + layoutCoordinates.size.height)

                        val bounds = Rect(
                            topLeft.x,
                            topLeft.y,
                            bottomRight.x,
                            bottomRight.y
                        )
                        cellBounds = cellBounds + (position to bounds)

                    }
                )
                //endregion
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Имитация LazyVerticalGrid
    }


    // Отслеживание перемещения
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-topBarHeight).dp) // Костыль
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        movingState = true
                    },
                    //region onDragEnd
                    onDragEnd = {

                        if (listClickableIndexes.count { it == 1 } < 2 && startStage) {
                            resultsList.add(-1)
                            MusicPlayer(context = context).playErrorInGame()

                            movingState = false
                            currentCell = -1
                            listCell = mutableListOf()
                            listClickableIndexes = mutableListOf()

                            listIndexFirst = listOfLists
                                .random()
                                .toMutableList()
                            listIndexSecond =
                                listIndexFirst.map { if (it == 3 || it == 2) 0 else it }

                            startStage = false
                        }
                    },
                    //endregion
                    //region onDrag
                    onDrag = { change, dragAmount ->
                        if (startStage) {
                            val newPosition = change.position
                            touchPosition = Offset(newPosition.x, newPosition.y)
                            coroutineScope.launch {
                                checkTouchPosition(cellBounds)
                            }

                            if (listClickableIndexes.count { it == 1 } >= 2) {
                                if (3 in listClickableIndexes) {
                                    MusicPlayer(context = context).playErrorInGame()
                                    resultsList.add(-1)
                                } else if (2 in listClickableIndexes) {
                                    MusicPlayer(context = context).playSuccessInGame()
                                    resultsList.add(3)
                                } else {
                                    MusicPlayer(context = context).playSuccessInGame()
                                    resultsList.add(2)
                                }

                                movingState = false
                                currentCell = -1
                                listCell = mutableListOf()
                                listClickableIndexes = mutableListOf()

                                listIndexFirst = listOfLists
                                    .random()
                                    .toMutableList()
                                listIndexSecond =
                                    listIndexFirst.map { if (it == 3 || it == 2) 0 else it }

                                startStage = false
                            }
                        }
                    }
                    //endregion
                )
            }
    ) {
        if (startStage){
            LaunchedEffect(cellBounds, touchPosition) {
                checkTouchPosition(cellBounds)
            }
        }

        //region Box - под мышкой. Если находится в нужной области красится в красный
        //        Box(
        //            modifier = Modifier
        //                .zIndex(5f)
        //                .size(10.dp)
        //                .drawWithContent {
        //                    val position = touchPosition
        //                    this.drawContent()
        //                    val boxSize = 10.dp.toPx() // размеры Box
        //                    val topLeftX = position.x - (boxSize / 2) // центрирование по X
        //                    val topLeftY = position.y - (boxSize / 2) // центрирование по Y
        //                    drawRect(
        //                        color = if (isInCell) Color.Red else Color.Green,
        //                        topLeft = Offset(topLeftX, topLeftY),
        //                        size = Size(boxSize, boxSize)
        //                    )
        //                }
        //        )
        //endregion
        Spacer(modifier = Modifier.weight(3f)) // Имитация LazyVerticalGrid
        //region Кнопка "Запомнил"
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .offset(y = (topBarHeight).dp) // Костыль
        ){
            if (!startStage) {
                LaunchedEffect(Unit) {
                    if (resultsList.size != 0){
                        putActualScope(if (resultsList.last() > 0) 53 else -22) // TODO Пока костыль для экономии времени ТОЧКА-2
                    }
                }
                StringButton(color = Color(0xFFFF7700)){
//                    Log.d("DEBUG-11", "------------ $isDraggingMap -----------isDraggingMap-1")
//                    Log.d("DEBUG-11", "------------ $listCell -----------listCell-1")
//                    Log.d("DEBUG-11", "------------ $listClickableIndexes -----------listClickableIndexes-1")
                    startStage = true
                }
            }
        }
        //endregion
    }


    //region Подсчет результатов игры
    LaunchedEffect(Unit) {
        delay(10000)
        while (countTimer <= 10){
            MusicPlayer(context = context).playTimer()
            delay(1000)
            countTimer++
        }
        MusicPlayer(context = context).playEndOfTheGame()

        resultsList.forEach {
            if (it > 0){
                countCorrect += 1
            }else{
                countIncorrect += 1
            }
        }
        val scopeGame = resultsList.sum()
        putGameResult(countCorrect, countIncorrect, if (scopeGame < 0) 0 else scopeGame, (countCorrect.toFloat()/resultsList.size.toFloat()))
    }
    //endregion

}


//region GameCell
@SuppressLint("MutableCollectionMutableState")
@Composable
private fun GameCell(
    currentCell: Int,
    movingState: Boolean,
    draggingMap: MutableMap<Int, Boolean>,
    listCell: MutableList<Int>,
    listClickableIndexes: MutableList<Int>,
    listIndexFirst: List<Int>,
    position: Int,
    index: Int,
    onLayoutChanged: (LayoutCoordinates) -> Unit
){

    if (currentCell == position && movingState) {
        if (position !in listCell){
            listCell.add(position)
            listClickableIndexes.add(listIndexFirst[position])
        }

        if (listCell.size > 1 && position == listCell[listCell.size - 2]){
            listCell.removeAt(listCell.size - 1)
            if (index != 1) {
                listClickableIndexes.removeAt(listCell.size - 1)
            }
        }
    }
    draggingMap[position] = position in listCell


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .size(60.dp)
            .clip(RoundedCornerShape(8))
            .background(if (draggingMap[position] ?: false) Color(0xB22AD2FC) else Color.White)
            .onGloballyPositioned { layoutCoordinates ->
                onLayoutChanged(layoutCoordinates)
            }
    ){
        when(index){
            0 -> {}
            1 -> {AssetImage(fileName = "ic_finish_flag.png", modifier = Modifier
                .fillMaxSize()
                .padding(10.dp))}
            2 -> {AssetImage(fileName = "ic_star.png", modifier = Modifier
                .fillMaxSize()
                .padding(10.dp))}
            3 -> {AssetImage(fileName = "ic_bomb.png", modifier = Modifier
                .fillMaxSize()
                .padding(10.dp))}
        }
    }
}
//endregion
//region StringButton
@Composable
private fun StringButton(
    color: Color,
    onButtonClick: () -> Unit
){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(14))
            .background(color = color)
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onButtonClick() }
    ) {
        Text(
            text = "Запомнил!",
            fontSize = 24.sp,
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
    }
}
//endregion

//region generateGameFieldMode2
private fun generateGameFieldMode2(): Pair<List<Int>, List<Int>>{
    val indexes = listOf(2, 0, 3, 0, 1, 3, 0, 3, 0, 3, 3, 0, 0, 0, 1, 0)
    val listIndexFirst = indexes.shuffled()
    val listIndexSecond = listIndexFirst.map { if (it == 3 || it == 2) 0 else it }
    return Pair(listIndexFirst, listIndexSecond)
}
//endregion

