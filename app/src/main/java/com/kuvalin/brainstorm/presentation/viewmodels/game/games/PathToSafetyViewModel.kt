package com.kuvalin.brainstorm.presentation.viewmodels.game.games

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PathToSafetyViewModel @Inject constructor(

): ViewModel() {

    // Не стал заносить пока в базу (по хорошему у обоих игроков должны быть один поля)
    //region listOfLists
    // Локальный список игровых полей
    private val listOfLists: List<List<Int>> = listOf(
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


    //region ################ Состояние игрового поля
    private val _listPlayingFields = MutableStateFlow(listOfLists.toMutableList())

    private val _listIndexFirst = MutableStateFlow(getRandomElement(_listPlayingFields.value))
    val listIndexFirst: StateFlow<List<Int>> = _listIndexFirst

    private val _listIndexSecond = MutableStateFlow(_listIndexFirst.value.map { if (it == 3 || it == 2) 0 else it })
    val listIndexSecond: StateFlow<List<Int>> = _listIndexSecond

    fun updateGameField(){
        _listIndexFirst.value = getRandomElement(_listPlayingFields.value)
        _listIndexSecond.value = _listIndexFirst.value.map { if (it == 3 || it == 2) 0 else it }
    }
    //endregion


    //region ################ Состояние игры
    private val _startStage = MutableStateFlow(false)
    val startStage: StateFlow<Boolean> = _startStage

    fun updateGameStage(start: Boolean) { _startStage.value = start }
    //endregion


    //region ################ Начало перемещения
    private val _movingState = MutableStateFlow(false)
    val movingState: StateFlow<Boolean> = _movingState

    fun updateMovingState(moving: Boolean) { _movingState.value = moving }
    //endregion


    //region ################ Координаты ячеек
    private val _cellBounds = MutableStateFlow<Map<Int, Rect>>(emptyMap())
    val cellBounds: StateFlow<Map<Int, Rect>> = _cellBounds

    fun updateBounds(newBounds: Map<Int, Rect>) { _cellBounds.value = newBounds }


    private val _touchPosition = MutableStateFlow(Offset.Zero)
    val touchPosition: StateFlow<Offset> = _touchPosition

    fun updateTouchPosition(newPosition: Offset) { _touchPosition.value = newPosition }


    private val _isInCell = MutableStateFlow(false)
    val isInCell: StateFlow<Boolean> = _isInCell

    fun updateIsInCell(isInCell: Boolean) { _isInCell.value = isInCell }
    //endregion


    //region ################ Результаты
    private val _resultList = MutableStateFlow(mutableListOf<Int>())
    val resultList: StateFlow<MutableList<Int>> = _resultList

    fun updateResults(result: Int) { _resultList.value.add(result) }


    private val _countCorrect = mutableStateOf(0)
    val countCorrect: State<Int> = _countCorrect
    private val _countIncorrect = mutableStateOf(0)
    val countIncorrect: State<Int> = _countIncorrect

    fun increaseCorrect() { _countCorrect.value++ }
    fun increaseIncorrect() { _countIncorrect.value++ }
    fun getInternalAccuracy(): Float {
        return (countCorrect.value.toFloat()/resultList.value.size.toFloat())
    }
    //endregion


    //region ################ Закрашенные ячейки
    private val _currentCell = MutableStateFlow(-1)
    val currentCell: StateFlow<Int> = _currentCell

    fun updateCurrentCell(newCell: Int) { _currentCell.value = newCell }


    private val _listCell = MutableStateFlow(mutableListOf<Int>())
    val listCell: StateFlow<MutableList<Int>> = _listCell

    fun addToListCell(position: Int) { _listCell.value.add(position) }
    fun resetFromListCell() { _listCell.value = mutableListOf() }
    fun removeFromListCell(index: Int) { _listCell.value.removeAt(index) }


    private val _listClickableIndexes = MutableStateFlow(mutableListOf<Int>())
    val listClickableIndexes: StateFlow<MutableList<Int>> = _listClickableIndexes

    fun addToListClickableIndexes(index: Int) { _listClickableIndexes.value.add(index) }
    fun resetFromListClickableIndexes() { _listClickableIndexes.value = mutableListOf() }
    fun removeFromListClickableIndexes(index: Int) { _listClickableIndexes.value.removeAt(index) }

    //endregion


    //region ################ Таймер
    private val _countTimer = MutableStateFlow(1)
    val countTimer: StateFlow<Int> = _countTimer

    fun updateTimer() { _countTimer.value++ }
    //endregion


    //region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
    fun resetGame() {
        _resultList.value.clear()
        _countCorrect.value = 0
        _countIncorrect.value = 0
        _countTimer.value = 1

        _listPlayingFields.value = listOfLists.toMutableList()
        resetFromListClickableIndexes()
        resetFromListCell()

        // Генерация нового поля
        updateGameField()
    }

    private fun getRandomElement(list: MutableList<List<Int>>): List<Int> {
        val randomIndex = list.indices.random()
        return list.removeAt(randomIndex)
    }
    //endregion ################################################################################## */

}