package com.kuvalin.brainstorm.globalClasses

import android.util.Log
import kotlinx.coroutines.delay



//region Принципы работы с классом UniversalDecorator
/*

1. Осмысленный выбор уровня логирования:
При использовании UniversalDecorator важно осознанно выбирать уровень логирования для каждого действия (DEBUG, INFO, WARN).
Уровень DEBUG следует применять для детализированной отладки, INFO — для стандартных сообщений, а WARN — для фиксации предупреждений.
🚨 Рекомендация: Избегайте использования DEBUG для рутинных задач, чтобы не засорять логи излишней информацией.

2. Контроль за объемом логов:
Уровни логирования DEBUG и WARN могут генерировать большое количество информации.
Важно следить за тем, чтобы эти логи не перегружали Logcat, особенно в производственной среде.
📊 Рекомендация: При необходимости временно отключайте DEBUG или фильтруйте его, чтобы сосредоточиться на важной информации.

3. Минимализм и фокус на целях:
Логирование должно быть минималистичным и фокусироваться на важных событиях и действиях.
Каждый лог должен приносить ценность, будь то отладочная информация, статус выполнения или предупреждение.
✂️ Рекомендация: Старайтесь избегать избыточного логирования.
Если информация не приносит пользы при анализе, её лучше исключить.

4. Соответствие уровней логирования контексту:
Используйте уровни логирования в зависимости от контекста выполнения кода.
Если действие критично или может вызвать проблемы, применяйте WARN.
Для общего статуса выполнения или успеха действия достаточно INFO.
🔍 Рекомендация: Оцените важность действия перед тем, как выбрать уровень логирования, чтобы лог был точным и соответствующим.

5. Адаптация под различные среды:
В зависимости от среды (разработка, тестирование, продакшен) может потребоваться изменение стратегии логирования.
Например, в продакшене лучше минимизировать использование DEBUG и WARN.
⚙️ Рекомендация: Настройте логирование так, чтобы оно подходило под конкретную среду, и при необходимости
используйте конфигурационные файлы для управления уровнями логов.

6. Оценка полезности логов:
Периодически пересматривайте логирование в приложении, чтобы убедиться, что оно по-прежнему приносит ценность и не стало избыточным.
📈 Рекомендация: Анализируйте логи и, если определенные уровни или сообщения стали неактуальными,
убирайте их из декоратора или переводите на более низкий уровень.
Эти принципы помогут обеспечить эффективное и целенаправленное использование UniversalDecorator,
минимизируя риски перегрузки логов и поддерживая высокое качество мониторинга и отладки приложения.
*/
//endregion


/**
 * Универсальный декоратор для выполнения различных действий перед и после основной функции.
 *
 * @param baseLogTag Базовый лог-тег для всех сообщений.
 * @param measureMainFunctionTime Замер времени основной функции.
 * @param measureAllActionsTime Замер времени всех действий (включая before и after).
 *
 * @see [com.kuvalin.brainstorm.globalClasses.DecAction]
 */
class UniversalDecorator(
    private val baseLogTag: String = "DecLog",
    private val measureMainFunctionTime: Boolean = false,
    private val measureAllActionsTime: Boolean = false
) {

    /**
     * Уровни логирования.
     */
    enum class LogLevel {
        DEBUG, INFO, WARN
    }


    fun logOutput(
        action: DecAction.Log,
        subLogTag: String? = null
    ){
        val fullLogTag = buildString {
            append(baseLogTag)
            subLogTag?.let { append("-$it") }
        }
        logMessage(fullLogTag, action.message, action.logLevel)
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
        beforeActions: List<DecAction>? = null,
        afterActions: List<DecAction>? = null,
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
            executeSyncAction(action, fullLogTag)
        }

        // Замер времени основной функции
        val startTime = if (measureMainFunctionTime) System.currentTimeMillis() else 0L

        // Выполнение основной функции
        val result = mainFunc()

        // Логирование времени выполнения основной функции
        if (measureMainFunctionTime) {
            val endTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Main function execution time: ${endTime - startTime} ms", LogLevel.INFO)
        }

        // Выполнение всех действий после вызова основной функции
        afterActions?.forEach { action ->
            executeSyncAction(action, fullLogTag)
        }

        // Логирование общего времени выполнения всех действий
        if (measureAllActionsTime) {
            val totalEndTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Total execution time (all actions): ${totalEndTime - totalStartTime} ms", LogLevel.INFO)
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
        beforeActions: List<DecAction>? = null,
        afterActions: List<DecAction>? = null,
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
            executeAsyncAction(action, fullLogTag)
        }

        // Замер времени основной функции
        val startTime = if (measureMainFunctionTime) System.currentTimeMillis() else 0L

        // Выполнение основной функции
        val result = mainFunc()

        // Логирование времени выполнения основной функции
        if (measureMainFunctionTime) {
            val endTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Main function execution time: ${endTime - startTime} ms", LogLevel.INFO)
        }

        // Выполнение всех действий после вызова основной функции
        afterActions?.forEach { action ->
            executeAsyncAction(action, fullLogTag)
        }

        // Логирование общего времени выполнения всех действий
        if (measureAllActionsTime) {
            val totalEndTime = System.currentTimeMillis()
            logMessage(fullLogTag, "Total execution time (all actions): ${totalEndTime - totalStartTime} ms", LogLevel.INFO)
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
    private fun executeSyncAction(action: DecAction, logTag: String) {
        when (action) {
            is DecAction.Log -> logMessage(logTag, action.message, action.logLevel)
            is DecAction.Execute -> action.function(logTag)
            is DecAction.MeasureTime -> Unit // Замер времени выполняется в основном коде
            is DecAction.Delay -> {} // Delay здесь не поддерживается
        }
    }


    /**
     * Вспомогательная функция для выполнения действия (асинхронная версия).
     *
     * @param action Действие, которое нужно выполнить.
     * @param logTag Тег для логирования.
     * @param logLevel Уровень логирования.
     */
    private suspend fun executeAsyncAction(action: DecAction, logTag: String) {
        when (action) {
            is DecAction.Log -> logMessage(logTag, action.message, action.logLevel)
            is DecAction.Execute -> action.function(logTag)
            is DecAction.MeasureTime -> Unit // Замер времени выполняется в основном коде
            is DecAction.Delay -> delay(action.millis)
        }
    }

}

/**
 * Sealed class, представляющий возможные действия для декоратора.
 */
sealed class DecAction {
    /**
     * Логирование сообщения.
     *
     * @param message Сообщение, которое нужно залогировать.
     * @param logLevel Уровень логирования (DEBUG, INFO, WARN).
     */
    class Log(
        val message: String,
        val logLevel: UniversalDecorator.LogLevel = UniversalDecorator.LogLevel.INFO
    ) : DecAction()


    /**
     * Выполнение дополнительной функции.
     *
     * @param function Функция, которую нужно выполнить.
     */
    class Execute(val function: (String) -> Unit) : DecAction()


    /**
     * Замер времени выполнения основной функции.
     */
    object MeasureTime : DecAction()


    /**
     * Задержка перед выполнением.
     *
     * @param millis Задержка в миллисекундах.
     */
    class Delay(val millis: Long) : DecAction()
}
