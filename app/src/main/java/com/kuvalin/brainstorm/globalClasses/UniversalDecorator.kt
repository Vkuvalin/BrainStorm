package com.kuvalin.brainstorm.globalClasses

import android.util.Log
import kotlinx.coroutines.delay






/**
 * Универсальный декоратор для выполнения различных действий перед и после основной функции.
 *
 * @param baseLogTag Базовый лог-тег для всех сообщений.
 * @param measureMainFunctionTime Замер времени основной функции.
 * @param measureAllActionsTime Замер времени всех действий (включая before и after).
 *
 * @see [com.kuvalin.brainstorm.globalClasses.Action]
 */
class UniversalDecorator(
    private val baseLogTag: String = "UniversalDecorator",
    private val measureMainFunctionTime: Boolean = false,
    private val measureAllActionsTime: Boolean = false
) {

    /**
     * Уровни логирования.
     */
    enum class LogLevel {
        DEBUG, INFO, WARN
    }


    /**
     * Метод для выполнения различных действий перед и после основной функции.
     *
     * @param mainFunc Основная функция, которую нужно обернуть. Она может возвращать значение любого типа T.
     * @param beforeActions Список действий, которые будут выполнены перед вызовом основной функции.
     * @param afterActions Список действий, которые будут выполнены после вызова основной функции.
     * @param logLevel Уровень логирования (DEBUG, INFO, WARN).
     * @param subLogTag Дополнительный лог-тег, который будет добавлен к базовому.
     * @return Возвращает результат выполнения основной функции.
     */
    //region fun <T> execute
    fun <T> execute(
        mainFunc: () -> T,
        beforeActions: List<Action>? = null,
        afterActions: List<Action>? = null,
        logLevel: LogLevel = LogLevel.INFO,
        subLogTag: String? = null
    ): T {
        val fullLogTag = buildString {
            append(baseLogTag)
            subLogTag?.let { append("-$it") }
        }

        // Замер времени для всех действий, если включен
        val totalStartTime = if (measureAllActionsTime) System.currentTimeMillis() else 0L

        // Выполнение всех действий до вызова основной функции
        beforeActions?.forEach { action ->
            executeSyncAction(action, fullLogTag, logLevel)
        }

        // Замер времени основной функции
        val startTime = if (measureMainFunctionTime) System.currentTimeMillis() else 0L

        // Выполнение основной функции
        val result = mainFunc()

        // Логирование времени выполнения основной функции
        if (measureMainFunctionTime) {
            val endTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Main function execution time: ${endTime - startTime} ms", logLevel)
        }

        // Выполнение всех действий после вызова основной функции
        afterActions?.forEach { action ->
            executeSyncAction(action, fullLogTag, logLevel)
        }

        // Логирование общего времени выполнения всех действий
        if (measureAllActionsTime) {
            val totalEndTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Total execution time (all actions): ${totalEndTime - totalStartTime} ms", logLevel)
        }

        return result
    }
    //endregion


    /**
     * Асинхронный метод для выполнения различных действий перед и после основной функции.
     *
     * @param mainFunc Основная функция, которую нужно обернуть. Она может возвращать значение любого типа T.
     * @param beforeActions Список действий, которые будут выполнены перед вызовом основной функции.
     * @param afterActions Список действий, которые будут выполнены после вызова основной функции.
     * @param logLevel Уровень логирования (DEBUG, INFO, WARN).
     * @param subLogTag Дополнительный лог-тег, который будет добавлен к базовому.
     * @return Возвращает результат выполнения основной функции.
     */
    //region suspend fun <T> executeAsync
    suspend fun <T> executeAsync(
        mainFunc: suspend () -> T,
        beforeActions: List<Action>? = null,
        afterActions: List<Action>? = null,
        logLevel: LogLevel = LogLevel.INFO,
        subLogTag: String? = null
    ): T {
        val fullLogTag = buildString {
            append(baseLogTag)
            subLogTag?.let { append("-$it") }
        }

        // Замер времени для всех действий, если включен
        val totalStartTime = if (measureAllActionsTime) System.currentTimeMillis() else 0L

        // Выполнение всех действий до вызова основной функции
        beforeActions?.forEach { action ->
            executeAsyncAction(action, fullLogTag, logLevel)
        }

        // Замер времени основной функции
        val startTime = if (measureMainFunctionTime) System.currentTimeMillis() else 0L

        // Выполнение основной функции
        val result = mainFunc()

        // Логирование времени выполнения основной функции
        if (measureMainFunctionTime) {
            val endTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Main function execution time: ${endTime - startTime} ms", logLevel)
        }

        // Выполнение всех действий после вызова основной функции
        afterActions?.forEach { action ->
            executeAsyncAction(action, fullLogTag, logLevel)
        }

        // Логирование общего времени выполнения всех действий
        if (measureAllActionsTime) {
            val totalEndTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Total execution time (all actions): ${totalEndTime - totalStartTime} ms", logLevel)
        }

        return result
    }
    //endregion


    /**
     * Вспомогательная функция для логирования сообщений в Logcat.
     *
     * @param logTag Тег для логирования.
     * @param message Сообщение для логирования.
     * @param logLevel Уровень логирования (DEBUG, INFO, WARN).
     */
    private fun logMessage(logTag: String, message: String, logLevel: LogLevel) {
        when (logLevel) {
            LogLevel.DEBUG -> Log.d(logTag, message)
            LogLevel.INFO -> Log.i(logTag, message)
            LogLevel.WARN -> Log.w(logTag, message)
        }
    }


    /**
     * Вспомогательная функция для выполнения действия (синхронная версия).
     *
     * @param action Действие, которое нужно выполнить.
     * @param logTag Тег для логирования.
     * @param logLevel Уровень логирования.
     */
    private fun executeSyncAction(action: Action, logTag: String, logLevel: LogLevel) {
        when (action) {
            is Action.Log -> logMessage(logTag, action.message, logLevel)
            is Action.Execute -> action.function(logTag)
            is Action.MeasureTime -> Unit // Замер времени выполняется в основном коде
            is Action.Delay -> {} // Delay здесь не поддерживается
        }
    }


    /**
     * Вспомогательная функция для выполнения действия (асинхронная версия).
     *
     * @param action Действие, которое нужно выполнить.
     * @param logTag Тег для логирования.
     * @param logLevel Уровень логирования.
     */
    private suspend fun executeAsyncAction(action: Action, logTag: String, logLevel: LogLevel) {
        when (action) {
            is Action.Log -> logMessage(logTag, action.message, logLevel)
            is Action.Execute -> action.function(logTag)
            is Action.MeasureTime -> Unit // Замер времени выполняется в основном коде
            is Action.Delay -> delay(action.millis)
        }
    }

}

/**
 * Sealed class, представляющий возможные действия для декоратора.
 */
sealed class Action {
    /**
     * Логирование сообщения.
     *
     * @param message Сообщение, которое нужно залогировать.
     * @param logLevel Уровень логирования (DEBUG, INFO, WARN).
     */
    class Log(val message: String, val logLevel: UniversalDecorator.LogLevel = UniversalDecorator.LogLevel.INFO) : Action()


    /**
     * Выполнение дополнительной функции.
     *
     * @param function Функция, которую нужно выполнить.
     */
    class Execute(val function: (String) -> Unit) : Action()


    /**
     * Замер времени выполнения основной функции.
     */
    object MeasureTime : Action()


    /**
     * Задержка перед выполнением.
     *
     * @param millis Задержка в миллисекундах.
     */
    class Delay(val millis: Long) : Action()
}
