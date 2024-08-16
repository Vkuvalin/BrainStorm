import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update



//region Документация класса
/**
 * ErrorHandler - класс для универсальной обработки и логирования ошибок в приложении.
 *
 * @property logTag Основной тег, который будет использоваться для всех логов, создаваемых этим классом.
 * @property handleAllExceptions Флаг, включающий или отключающий автоматическую обработку всех исключений. По умолчанию `true`.
 *
 * Основные функции:
 * - `handle`: Универсальная функция для обработки ошибок в обычных функциях, с поддержкой логирования и повторных попыток выполнения.
 * - `handleSuspend`: Специальная версия функции для обработки ошибок в корутинах (`suspend`), поддерживающая логирование и повторные попытки.
 * - `handleComposable`: Специальная версия функции для использования в композируемых функциях (`@Composable`), с ожиданием завершения корутины и логированием.
 * - `setCriticalErrorHandler`: Установка специального обработчика критических ошибок, которые могут вызвать краш приложения, если не будут обработаны.
 *
 * Примеры использования:
 *
 * Пример 1: Простой вызов с обработкой ошибок
 * ```
 * errorHandler.handle {
 *     // Ваш код здесь
 * }
 * ```
 *
 * Пример 2: Вызов с настройкой уровня логирования и контекста
 * ```
 * errorHandler.handle(
 *     logLevel = ErrorHandler.LogLevel.WARN,
 *     context = LogContext(userName = "JohnDoe", screenName = "MainScreen")
 * ) {
 *     // Ваш код здесь
 * }
 * ```
 *
 * Пример 3: Вызов suspend функции с обработкой ошибок и повторными попытками
 * ```
 * val result = errorHandler.handleSuspend(
 *     logLevel = ErrorHandler.LogLevel.ERROR,
 *     context = LogContext(userName = "JaneDoe", screenName = "SettingsScreen"),
 *     maxRetries = 3
 * ) {
 *     // Ваш suspend код здесь
 * }
 * ```
 *
 * Пример 4: Вызов в composable функции с обработкой ошибок и ожиданием завершения корутины
 * ```
 * val result = errorHandler.handleComposable(
 *     logLevel = ErrorHandler.LogLevel.ERROR,
 *     context = LogContext(screenName = "MyComposableScreen"),
 *     maxRetries = 3
 * ) {
 *     // Ваш suspend код в композируемой функции
 * }
 * ```
 *
 * Пример 5: Установка обработчика критических ошибок
 * ```
 * errorHandler.setCriticalErrorHandler(object : ErrorHandler.CriticalErrorHandler {
 *     override fun handleCriticalError(e: Exception): Boolean {
 *         if (e is ArithmeticException) {
 *             Log.e("CriticalError", "Произошло деление на ноль!")
 *             return true // Ошибка обработана
 *         }
 *         return false // Ошибка не обработана, передаем ее дальше
 *     }
 * })
 * ```
 *
 * @constructor Создает экземпляр ErrorHandler с указанным лог-тегом и параметром автоматической обработки исключений.
 */
//endregion

class ErrorHandler(
    private val logTag: String = "ErrorHandler",  // Основной тег для логов
    private val handleAllExceptions: Boolean = true  // Флаг для включения обработки всех исключений
) {

    enum class LogLevel {
        DEBUG, INFO, WARN, ERROR
    }


    // Встроенный критический обработчик по умолчанию
    private var contextForCritical = LogContext()
    private val defaultCriticalErrorHandler = object : CriticalErrorHandler {
        override fun handleCriticalError(e: Exception): Boolean {
            if (handleAllExceptions) {
                logError(e, LogLevel.ERROR, contextForCritical, "CriticalError")
                return true
            }
            return false
        }
    }


    private var criticalErrorHandler: CriticalErrorHandler = defaultCriticalErrorHandler
    /**
     * CriticalErrorHandler - интерфейс для обработки критических ошибок.
     *
     * Если критическая ошибка возникает, она обрабатывается с помощью данного интерфейса.
     * Метод `handleCriticalError` должен вернуть `true`, если ошибка обработана, и `false`, если ошибка должна быть передана дальше.
     */
    interface CriticalErrorHandler {
        fun handleCriticalError(e: Exception): Boolean
    }


    /**
     * setCriticalErrorHandler - функция для установки специального обработчика критических ошибок.
     *
     * @param handler Экземпляр интерфейса `CriticalErrorHandler`, который будет использоваться для обработки критических ошибок.
     */
    fun setCriticalErrorHandler(handler: CriticalErrorHandler) {
        this.criticalErrorHandler = handler
    }


    /**
     * handleSuspend - основная функция для обработки ошибок в корутинах (`suspend`).
     *
     * @param logLevel Уровень логирования, используемый для записи сообщений в лог (по умолчанию ERROR).
     * @param context Объект `LogContext`, содержащий дополнительные сведения о контексте выполнения (имя пользователя, экран и т.д.).
     * @param maxRetries Количество повторных попыток выполнения блока кода в случае ошибки (по умолчанию 0).
     * @param subLogTag Дополнительный лог-тег, который будет добавлен к основному через дефис.
     * @param retryDelayMillis Пауза между повторными попытками при необходимости.
     * @param block Лямбда-выражение или блок кода, который будет выполнен внутри функции с обработкой ошибок.
     *
     * @return Результат выполнения блока кода, если он был выполнен успешно, иначе `null`.
     *
     * Пример использования:
     * ```
     * val result = errorHandler.handleSuspend(
     *     logLevel = ErrorHandler.LogLevel.ERROR,
     *     context = LogContext(userName = "JaneDoe", screenName = "SettingsScreen"),
     *     maxRetries = 3
     * ) {
     *     // Ваш suspend код здесь
     * }
     * ```
     */
    //region handleSuspend
    internal suspend inline fun <T> handleSuspend(
        logLevel: LogLevel = LogLevel.ERROR,
        context: LogContext? = null,
        maxRetries: Int = 0,
        subLogTag: String? = null,
        retryDelayMillis: Long = 0L,
        crossinline block: suspend () -> T
    ): T? {
        var retries = 0
        if (context != null) { contextForCritical = context } // Critical обрабатываются иначе

        while (true) {
            return try {
                block()
            } catch (e: Exception) {
                if (criticalErrorHandler.handleCriticalError(e)) {
                    return null // Если критическая ошибка обработана, возвращаем null и не продолжаем
                }
                logError(e, logLevel, context, subLogTag)
                if (retries < maxRetries) {
                    retries++
                    if (retryDelayMillis > 0L) { delay(retryDelayMillis) }
                    continue
                } else {
                    null
                }
            }
        }
    }
    //endregion


    /**
     * handleComposable - функция для обработки ошибок в контексте композируемых функций (`@Composable`).
     * Использует `Flow` для ожидания завершения корутины и возвращения результата после завершения.
     *
     * @param logLevel Уровень логирования, используемый для записи сообщений в лог (по умолчанию ERROR).
     * @param context Объект `LogContext`, содержащий дополнительные сведения о контексте выполнения (имя пользователя, экран и т.д.).
     * @param maxRetries Количество повторных попыток выполнения блока кода в случае ошибки (по умолчанию 0).
     * @param subLogTag Дополнительный лог-тег, который будет добавлен к основному через дефис.
     * @param retryDelayMillis Пауза между повторными попытками при необходимости.
     * @param block Лямбда-выражение или блок кода, который будет выполнен внутри функции с обработкой ошибок.
     *
     * @return Результат выполнения блока кода, если он был выполнен успешно, иначе `null`.
     *
     * Пример использования:
     * ```
     * val result = errorHandler.handleComposable(
     *     logLevel = ErrorHandler.LogLevel.ERROR,
     *     context = LogContext(screenName = "MyComposableScreen"),
     *     maxRetries = 3
     * ) {
     *     // Ваш suspend код в композируемой функции
     * }
     * ```
     */
    //region handleComposable
    @Composable
    fun <T> handleComposable(
        logLevel: LogLevel = LogLevel.ERROR,
        context: LogContext? = null,
        maxRetries: Int = 0,
        subLogTag: String? = null,
        retryDelayMillis: Long = 0L,
        block: suspend () -> T
    ): T? {
        val resultFlow = remember { MutableStateFlow<T?>(null) }
        val result by resultFlow.collectAsState()
        if (context != null) { contextForCritical = context } // Critical обрабатываются иначе

        LaunchedEffect(Unit) {
            var retries = 0
            while (true) {
                try {
                    val res = block()
                    resultFlow.update { res }
                    break
                } catch (e: Exception) {
                    if (criticalErrorHandler.handleCriticalError(e)) {
                        resultFlow.update { null }
                        break
                    }
                    logError(e, logLevel, context, subLogTag)
                    if (retries < maxRetries) {
                        retries++
                        if (retryDelayMillis > 0L) { delay(retryDelayMillis) }
                    } else {
                        resultFlow.update { null }
                        break
                    }
                }
            }
        }

        return result
    }
    //endregion


    /**
     * handle - основная функция для обработки ошибок в обычных функциях.
     *
     * @param logLevel Уровень логирования, используемый для записи сообщений в лог (по умолчанию ERROR).
     * @param context Объект `LogContext`, содержащий дополнительные сведения о контексте выполнения (имя пользователя, экран и т.д.).
     * @param maxRetries Количество повторных попыток выполнения блока кода в случае ошибки (по умолчанию 0).
     * @param subLogTag Дополнительный лог-тег, который будет добавлен к основному через дефис.
     * @param block Лямбда-выражение или блок кода, который будет выполнен внутри функции с обработкой ошибок.
     *
     * @return Результат выполнения блока кода, если он был выполнен успешно, иначе `null`.
     *
     * Пример использования:
     * ```
     * val result = errorHandler.handle(
     *     logLevel = ErrorHandler.LogLevel.WARN,
     *     context = LogContext(userName = "JohnDoe", screenName = "MainScreen")
     * ) {
     *     // Ваш код здесь
     * }
     * ```
     */
    //region handle
    fun <T> handle(
        logLevel: LogLevel = LogLevel.ERROR,
        context: LogContext? = null,
        maxRetries: Int = 0,
        subLogTag: String? = null,
        block: () -> T
    ): T? {
        var retries = 0
        if (context != null) { contextForCritical = context } // Critical обрабатываются иначе

        while (true) {
            return try {
                block()
            } catch (e: Exception) {
                if (criticalErrorHandler.handleCriticalError(e)) {
                    return null
                }
                logError(e, logLevel, context, subLogTag)
                if (retries < maxRetries) {
                    retries++
                    continue
                } else {
                    null
                }
            }
        }
    }
    //endregion


    /**
     * logError - функция для записи ошибок в лог с учетом уровня логирования и контекста.
     *
     * @param e Исключение, которое было выброшено.
     * @param logLevel Уровень логирования для данной ошибки.
     * @param context Объект `LogContext`, содержащий дополнительные сведения о контексте выполнения.
     * @param subLogTag Дополнительный лог-тег, который будет добавлен к основному через дефис.
     *
     * Пример использования:
     * ```
     * logError(e, LogLevel.ERROR, context, "Database")
     * ```
     */
    //region logError
    internal fun logError(
        e: Exception,
        logLevel: LogLevel,
        context: LogContext?,
        subLogTag: String?
    ) {
        val fullLogTag = buildString {
            append(logTag)
            subLogTag?.let { append("-$it") }
        }

        val message = buildString {
            append("Error: ${e.message}")
            context?.let {
                it.userName?.let { append(" | User: $it") }
                it.screenName?.let { append(" | Screen: $it") }
                it.additionalInfo?.let { append(" | Info: $it") }
            }
        }

        when (logLevel) {
            LogLevel.DEBUG -> Log.d(fullLogTag, message, e)
            LogLevel.INFO -> Log.i(fullLogTag, message, e)
            LogLevel.WARN -> Log.w(fullLogTag, message, e)
            LogLevel.ERROR -> Log.e(fullLogTag, message, e)
        }
    }
    //endregion

}


/**
 * LogContext - класс для хранения дополнительной информации о контексте выполнения.
 *
 * @property userName Имя пользователя, при котором возникла ошибка (может быть `null`).
 * @property screenName Имя экрана или активности, на котором возникла ошибка (может быть `null`).
 * @property additionalInfo Дополнительная информация, которая может быть полезна при анализе ошибки (может быть `null`).
 *
 * Пример использования:
 * ```
 * val context = LogContext(userName = "JohnDoe", screenName = "LoginScreen")
 * ```
 */
data class LogContext(
    val userName: String? = null,
    val screenName: String? = null,
    val additionalInfo: String? = null
)
