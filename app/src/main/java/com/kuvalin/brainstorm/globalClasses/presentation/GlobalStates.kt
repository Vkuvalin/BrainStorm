package com.kuvalin.brainstorm.globalClasses.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object GlobalStates {
    private val _runGameScreenState = MutableStateFlow(false)
    val runGameScreenState: StateFlow<Boolean> = _runGameScreenState

    private val _runGameState = MutableStateFlow(false)
    val runGameState: StateFlow<Boolean> = _runGameScreenState

    private val _userUid = MutableStateFlow(Firebase.auth.uid.toString()) // Если рега не пройдена, возв null (смотри логи)
    val userUid: StateFlow<String> = _userUid



    // Внизу
    private val _lifecycleCurrentState = MutableStateFlow(Lifecycle.State.INITIALIZED)
    val lifecycleCurrentState: StateFlow<Lifecycle.State> = _lifecycleCurrentState


//region Примеры
//    private val _soundEnabled = MutableStateFlow(true)
//    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()
//
//    private val _playerScore = MutableStateFlow(0)
//    val playerScore: StateFlow<Int> = _playerScore.asStateFlow()
//
//    private val _currentLevel = MutableStateFlow(1)
//    val currentLevel: StateFlow<Int> = _currentLevel.asStateFlow()
//
//    private val _playerName = MutableStateFlow("Player1")
//    val playerName: StateFlow<String> = _playerName.asStateFlow()
//
//    private val _currentLocation = MutableStateFlow("Start")
//    val currentLocation: StateFlow<String> = _currentLocation.asStateFlow()
//
//    private val _inventoryItems = MutableStateFlow(emptyList<String>())
//    val inventoryItems: StateFlow<List<String>> = _inventoryItems.asStateFlow()
//
//    private val _completedLevels = MutableStateFlow(emptyList<Int>())
//    val completedLevels: StateFlow<List<Int>> = _completedLevels.asStateFlow()
//
//    private val _playerStats = MutableStateFlow(emptyMap<String, Int>())
//    val playerStats: StateFlow<Map<String, Int>> = _playerStats.asStateFlow()
//
//    private val _visitedLocations = MutableStateFlow(emptySet<String>())
//    val visitedLocations: StateFlow<Set<String>> = _visitedLocations.asStateFlow()
//
//    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
//    val currentTime: StateFlow<Long> = _currentTime.asStateFlow()
//
//    private val _temperature = MutableStateFlow(25.0)
//    val temperature: StateFlow<Double> = _temperature.asStateFlow()
//
//    private val _currentSelection = MutableStateFlow<Any>(Any())
//    val currentSelection: StateFlow<Any> = _currentSelection.asStateFlow()
//endregion

    fun putScreenState(key: String, value: Any) {
        when (key) {
            "runGameScreenState" -> _runGameScreenState.value = value as Boolean
            "runGameState" -> _runGameState.value = value as Boolean
            "lifecycleCurrentState" -> _lifecycleCurrentState.value = value as Lifecycle.State
            "userUid" -> _userUid.value = value as String
//region Примеры
//            "soundEnabled" -> _soundEnabled.value = value as Boolean
//            "playerScore" -> _playerScore.value = value as Int
//            "currentLevel" -> _currentLevel.value = value as Int
//            "playerName" -> _playerName.value = value as String
//            "currentLocation" -> _currentLocation.value = value as String
//            "inventoryItems" -> _inventoryItems.value = value as List<String>
//            "completedLevels" -> _completedLevels.value = value as List<Int>
//            "playerStats" -> _playerStats.value = value as Map<String, Int>
//            "visitedLocations" -> _visitedLocations.value = value as Set<String>
//            "currentTime" -> _currentTime.value = value as Long
//            "temperature" -> _temperature.value = value as Double
//            "currentSelection" -> _currentSelection.value = value
//endregion
            else -> throw IllegalArgumentException("Unknown key: $key")
        }
    }

    // Отслеживает состояния прилы. Также в MainActivity есть пример observer
    @Composable
    fun ObserverLifecycleCurrentState() {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        val observer = remember {
            LifecycleEventObserver { owner, _ ->
                when (owner.lifecycle.currentState) {
                    Lifecycle.State.DESTROYED -> { putScreenState("lifecycleCurrentState", Lifecycle.State.DESTROYED) }
                    Lifecycle.State.INITIALIZED -> { putScreenState("lifecycleCurrentState", Lifecycle.State.INITIALIZED) }
                    Lifecycle.State.CREATED -> { putScreenState("lifecycleCurrentState", Lifecycle.State.CREATED) }
                    Lifecycle.State.STARTED -> { putScreenState("lifecycleCurrentState", Lifecycle.State.STARTED) }
                    Lifecycle.State.RESUMED -> { putScreenState("lifecycleCurrentState", Lifecycle.State.RESUMED) }
                }
            }
        }

        DisposableEffect(lifecycle) {
            lifecycle.addObserver(observer)
            onDispose {
                lifecycle.removeObserver(observer)
            }
        }
    }
}


// Следим за Lifecycle  (Пока не нужно) Это не отсюда. Вставить в нужном месте
// GlobalStates.ObserverLifecycleCurrentState() // Эту хуйню по сути всегда в инитАпп нужно сувать
// val currentLifecycleState = GlobalStates.lifecycleCurrentState.collectAsState().value