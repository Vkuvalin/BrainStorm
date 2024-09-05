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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.ANIMATION_DURATION_350
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.game.games.PathToSafetyViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.FlickMasterFieldBackground
import com.kuvalin.brainstorm.ui.theme.OrangeAppColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("MutableCollectionMutableState")
@Composable
fun PathToSafety(
    topBarHeight: Int, // Костыль,
    onBackButtonClick: () -> Unit,
    putActualScope: (gameScope: Int) -> Unit, // TODO Пока костыль для экономии времени ТОЧКА-1
    putGameResult: (countCorrect: Int, countIncorrect: Int, gameScope: Int, internalAccuracy: Float) -> Unit
){

    //region ############# 🔄 ################## BackHandler ################## 🔄 ############## */
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(ANIMATION_DURATION_350){ clickNavigation = false } }

    BackHandler {
        clickNavigation = true
        onBackButtonClick()
    }
    //endregion ################################################################################# */


    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    val viewModel: PathToSafetyViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
    val coroutineScope = rememberCoroutineScope()

    // Генерация игрового поля
    val listIndexFirst by viewModel.listIndexFirst.collectAsState()
    val listIndexSecond by viewModel.listIndexSecond.collectAsState()

    // Контроль этапами игры
    val startStage by viewModel.startStage.collectAsState()
    val movingState by viewModel.movingState.collectAsState()

    // Отслеживание координат ячеек и перемещения пальца
    val cellBounds by viewModel.cellBounds.collectAsState()
    val touchPosition by viewModel.touchPosition.collectAsState()
    val isInCell by viewModel.isInCell.collectAsState()

    // Закрашенные ячейки
    val currentCell by viewModel.currentCell.collectAsState()
    val listClickableIndexes by viewModel.listClickableIndexes.collectAsState()
    val listCell by viewModel.listCell.collectAsState()
    val isDraggingMap = remember { mutableMapOf<Int, Boolean>()}


    // Для проигрывания звуков
    val context = rememberUpdatedState(LocalContext.current)
    val countTimer by viewModel.countTimer.collectAsState()
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */

    //region checkTouchPosition
    fun checkTouchPosition(boundsMap: Map<Int, Rect>) {
        if (startStage) {
            for ((index, bounds) in boundsMap) {
                if (touchPosition.x in bounds.left..bounds.right &&
                    touchPosition.y in bounds.top..bounds.bottom
                ) {
                    viewModel.updateIsInCell(true)
                    viewModel.updateCurrentCell(index)
                    return
                }
            }
            viewModel.updateIsInCell(false)
            viewModel.updateCurrentCell(-1)
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
                    viewModel = viewModel,
                    startStage = startStage,
                    currentCell = currentCell,
                    movingState = movingState,
                    draggingMap = isDraggingMap,
                    listCell = listCell,
                    listIndexFirst = listIndexFirst,
                    position = position,
                    index = if(!startStage) listIndexFirst[position] else listIndexSecond[position],
                    onLayoutChanged = {layoutCoordinates ->
                        val topLeft = layoutCoordinates.positionInRoot()
                        val bottomRight = Offset(topLeft.x + layoutCoordinates.size.width, topLeft.y + layoutCoordinates.size.height)

                        val bounds = Rect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y)
                        viewModel.updateBounds(viewModel.cellBounds.value + (position to bounds))

                    }
                )
                //endregion
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Имитация LazyVerticalGrid
    }


    //region Отслеживание перемещения
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (-topBarHeight).dp) // Костыль
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        viewModel.updateMovingState(true)
                    },
                    //region onDragEnd
                    onDragEnd = {

                        if (listClickableIndexes.count { it == 1 } < 2 && startStage) {
                            viewModel.updateResults(-1)
                            MusicPlayer(context = context.value).playErrorInGame()

                            viewModel.updateMovingState(false)
                            viewModel.updateCurrentCell(-1)
                            viewModel.resetFromListCell()
                            viewModel.resetFromListClickableIndexes()

                            viewModel.updateGameField()
                            viewModel.updateGameStage(false)
                        }
                    },
                    //endregion
                    //region onDrag
                    onDrag = { change, dragAmount ->
                        if (startStage) {
                            val newPosition = change.position
                            viewModel.updateTouchPosition(Offset(newPosition.x, newPosition.y))

                            coroutineScope.launch { checkTouchPosition(cellBounds) }

                            if (listClickableIndexes.count { it == 1 } >= 2) {
                                if (3 in listClickableIndexes) {
                                    MusicPlayer(context = context.value).playErrorInGame()
                                    viewModel.updateResults(-1)
                                } else if (2 in listClickableIndexes) {
                                    MusicPlayer(context = context.value).playSuccessInGame()
                                    viewModel.updateResults(3)
                                } else {
                                    MusicPlayer(context = context.value).playSuccessInGame()
                                    viewModel.updateResults(2)
                                }

                                viewModel.updateMovingState(false)
                                viewModel.updateCurrentCell(-1)
                                viewModel.resetFromListCell()
                                viewModel.resetFromListClickableIndexes()

                                viewModel.updateGameField()
                                viewModel.updateGameStage(false)
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

        Spacer(modifier = Modifier.weight(3f)) // Имитация LazyVerticalGrid
        //region Кнопка "Запомнил"
        // Располагается тут, чтобы быть поверх "отслеживания перемещения"
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .offset(y = (topBarHeight).dp) // Костыль
        ){
            if (!startStage) {
                LaunchedEffect(Unit) {
                    val resultList = viewModel.resultList.value
                    if (resultList.size != 0){
                        putActualScope(if (resultList.last() > 0) 53 else -22) // TODO Пока костыль для экономии времени ТОЧКА-2
                    }
                }
                StringButton(color = OrangeAppColor){ viewModel.updateGameStage(true) }
            }
        }
        //endregion
    }
    //endregion

    //region Подсчет результатов игры
    LaunchedEffect(Unit) {
        delay(20000)
        while (countTimer <= 10) { // TODO забыл про repeat(10) { }
            MusicPlayer(context = context.value).playTimer()
            delay(1000)
            viewModel.updateTimer()
        }
        MusicPlayer(context = context.value).playEndOfTheGame()

        viewModel.resultList.value.forEach {
            if (it > 0) viewModel.increaseCorrect()
            else viewModel.increaseIncorrect()
        }

        // Отправка результатов
        val scopeGame = viewModel.resultList.value.sum()
        putGameResult(
            viewModel.countCorrect.value,
            viewModel.countIncorrect.value,
            if (scopeGame < 0) 0 else scopeGame,
            viewModel.getInternalAccuracy()
        )
        viewModel.resetGame()
    }
    //endregion

    //endregion ################################################################################## */
}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region GameCell
@SuppressLint("MutableCollectionMutableState")
@Composable
private fun GameCell(
    viewModel: PathToSafetyViewModel,
    startStage: Boolean,
    currentCell: Int,
    movingState: Boolean,
    draggingMap: MutableMap<Int, Boolean>,
    listCell: MutableList<Int>,
    listIndexFirst: List<Int>,
    position: Int,
    index: Int,
    onLayoutChanged: (LayoutCoordinates) -> Unit
){

    if (currentCell == position && movingState) {
        if (position !in listCell){
            viewModel.addToListCell(position)
            viewModel.addToListClickableIndexes(listIndexFirst[position])
        }

        if (listCell.size > 1 && position == listCell[listCell.size - 2]){
            viewModel.removeFromListCell(listCell.size - 1)
            if (index != 1) {
                viewModel.removeFromListClickableIndexes(listCell.size - 1)
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
            .background(
                if (draggingMap[position] ?: false) FlickMasterFieldBackground else Color.White
            )
            .onGloballyPositioned { layoutCoordinates ->
                onLayoutChanged(layoutCoordinates)
            }
    ){
        when(index){
            0 -> {}
            1 -> {
                AssetImage(
                    fileName = "ic_finish_flag.png",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .then(if (!startStage) Modifier.alpha(0f) else Modifier)
                )
            }
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
//endregion ################################################################################## */


