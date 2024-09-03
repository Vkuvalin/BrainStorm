package com.kuvalin.brainstorm.presentation.viewmodels.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.usecase.AddGameResultUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserUidUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class GameScreenViewModel @Inject constructor(
    private val getUserUidUseCase: GetUserUidUseCase,
    private val addGameResultUseCase: AddGameResultUseCase,
    private val getGameStatisticUseCase: GetGameStatisticUseCase
): ViewModel() {

    private var userUid = ""
    init {
        viewModelScope.launch {
            userUid = getUserUidUseCase.invoke()
        }
    }

    //region ---------------------------------- GameScreen -----------------------------------------
    // Вводные игровые данные
    private val _gameName = MutableStateFlow("")
    val gameName: StateFlow<String> = _gameName
    private val _gameInstructionImage = MutableStateFlow("")
    val gameInstructionImage: StateFlow<String> = _gameInstructionImage
    private val _gameDescription = MutableStateFlow("")
    val gameDescription: StateFlow<String> = _gameDescription
    private val _miniatureGameImage = MutableStateFlow("")
    val miniatureGameImage: StateFlow<String> = _miniatureGameImage

    fun updateGameData(
        gameName: String,
        gameDescription: String,
        miniatureGameImage:  String,
        gameInstructionImage: String,
        loadFinish: Boolean = true
    ) {
        _gameName.value = gameName
        _gameDescription.value = gameDescription
        _miniatureGameImage.value = miniatureGameImage
        _gameInstructionImage.value = gameInstructionImage
        _loadFinish.value = loadFinish
    }



    // Результаты игры
    private val _correct = MutableStateFlow(0)
    val correct: StateFlow<Int> = _correct
    private val _incorrect = MutableStateFlow(0)
    val incorrect: StateFlow<Int> = _incorrect
    private val _accuracy = MutableStateFlow(0f)
    val accuracy: StateFlow<Float> = _accuracy
    private val _scope = MutableStateFlow(0)
    val scope: StateFlow<Int> = _scope

    fun updateGameResult(correct:Int, incorrect: Int, accuracy: Float, scope: Int) {
        _correct.value = correct
        _incorrect.value = incorrect
        _accuracy.value = accuracy
        _scope.value = scope
    }



    // Стейты анимации и течения игры
    private val _loadFinish = MutableStateFlow(false)
    val loadFinish: StateFlow<Boolean> = _loadFinish
    private val _startGameState = MutableStateFlow(false)
    val startGameState: StateFlow<Boolean> = _startGameState
    private val _endGameState = MutableStateFlow(false)
    val endGameState: StateFlow<Boolean> = _endGameState

    fun updateLoadFinish(loadFinish: Boolean) { _loadFinish.value = loadFinish }
    fun updateStartGameState(startGameState: Boolean) { _startGameState.value = startGameState }
    fun updateEndGameState(endGameState: Boolean) { _endGameState.value = endGameState }
    //endregion ------------------------------------------------------------------------------------

    //region ----------------------------------- GameResult ----------------------------------------
    private val _highestValue = MutableStateFlow(0)
    val highestValue: StateFlow<Int> = _highestValue
    private val _averageValue = MutableStateFlow(0)
    val averageValue: StateFlow<Int> = _averageValue
    private val _finalAccuracy = MutableStateFlow(0f)
    val finalAccuracy: StateFlow<Float> = _finalAccuracy
    private val _finalScope = MutableStateFlow(0)
    val finalScope: StateFlow<Int> = _finalScope


    suspend fun addGameResult() {
        // Подсчет статистики
        val calculatedAccuracy = (accuracy.value * 1000).roundToInt() / 10.0f
        val calculatedScope = if (scope.value == 0) ((correct.value * 53) - (incorrect.value * 22))
        else (scope.value * 53) - (scope.value * 22)

        _finalAccuracy.value = calculatedAccuracy
        _finalScope.value = calculatedScope

        addGameResultUseCase.invoke(
            GameResult(
                uid = userUid,
                gameName = gameName.value,
                scope = calculatedScope,
                accuracy = calculatedAccuracy
            )
        )
    }



    suspend fun loadGameStatistic(){
        val gameStatistic = getGameStatisticUseCase.invoke(uid = userUid, gameName = gameName.value)
        _highestValue.value = gameStatistic.maxGameScore
        _averageValue.value = gameStatistic.avgGameScore
    }
    //endregion ------------------------------------------------------------------------------------

}