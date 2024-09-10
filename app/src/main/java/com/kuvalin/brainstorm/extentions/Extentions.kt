package com.kuvalin.brainstorm.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull



// ==================================== Extensions для Flow ==================================


// Это расширение позволяет слепить 2 потока
fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}

// Это расширение позволяет сохранить последнее значение, которое Flow отдает, полезно при отладке или тестировании.
fun <T> Flow<T>.latestValue(): T? {
    var value: T? = null
    runBlocking {
        collect {
            value = it
        }
    }
    return value
}


// Получение первого значения из Flow
suspend fun <T> Flow<T>.firstValue(): T = first()


// Возвращает Flow, который завершается по истечении времени.
suspend fun <T> Flow<T>.withTimeoutFlow(timeoutMillis: Long): Flow<T> = withTimeoutOrNull(timeoutMillis) {
    this@withTimeoutFlow
} ?: flowOf()


// Создает Flow, который отправляет данные через заданный интервал времени.
fun <T> periodicFlow(periodMillis: Long, value: T): Flow<T> = flow {
    while (true) {
        emit(value)
        delay(periodMillis)
    }
}


// Простое использование дебаунса для уменьшения частоты эмиссии данных.
fun <T> Flow<T>.debounceFlow(delayMillis: Long): Flow<T> = debounce(delayMillis)



// ============== Extensions для Jetpack Compose ==============

/*
 Отправка события при сворачивании клавиатуры.
 Расширение для Modifier, которое вызывает функцию при скрытии клавиатуры,
 что может быть полезно для обработки определенных UI-сценариев.
*/
//fun Modifier.onKeyboardHidden(onHidden: () -> Unit): Modifier = composed {
//    val context = LocalContext.current
//    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    val isOpen = remember { mutableStateOf(false) }
//
//    DisposableEffect(Unit) {
//        val listener = object : InputMethodManager.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                val isKeyboardOpen = imm.isAcceptingText
//                if (isOpen.value && !isKeyboardOpen) {
//                    onHidden()
//                }
//                isOpen.value = isKeyboardOpen
//                return true
//            }
//        }
//        imm.addOnPreDrawListener(listener)
//        onDispose { imm.removeOnPreDrawListener(listener) }
//    }
//    this
//}


// Расширение для анимирования изменения размера элемента в Compose.
@Composable
fun Modifier.animateSize(newSize: Dp): Modifier = this.then(
    Modifier.size( animateDpAsState(newSize, label = "").value )
)


// Это расширение добавляет элементу тень при клике.
fun Modifier.clickWithShadow(
    onClick: () -> Unit,
    shadowColor: Color = Color.Black,
    shadowRadius: Dp = 5.dp
): Modifier = this
    .shadow(shadowRadius, shape = RoundedCornerShape(8.dp), clip = false)
    .clickable(onClick = onClick)


// Этот модификатор позволяет элементу исчезать после клика с постепенной анимацией.

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.fadeOutOnClick(
    fadeDuration: Int = 300,
    onFadeComplete: () -> Unit
): Modifier = composed {
    var isVisible by remember { mutableStateOf(true) }
    if (isVisible) {
        Modifier
            .clickable { isVisible = false }
            .alpha(
                animateFloatAsState(
                    targetValue = if (isVisible) 1f else 0f,
                    animationSpec = tween(fadeDuration),label = "").value
            )
            .onGloballyPositioned { if (!isVisible) onFadeComplete() }
    } else Modifier
}


// Простой и постоянный индикатор загрузки для ситуаций, когда процесс длительный.
@Composable
fun InfiniteProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(48.dp).padding(8.dp), color = Color.Blue, strokeWidth = 4.dp
    )
}



// ============== Универсальные утилиты для коллекций и строк ==============

// Позволяет безопасно получить элемент списка без исключений, если индекс выходит за пределы.
fun <T> List<T>.safeGet(index: Int): T? = if (index in indices) this[index] else null


// Быстрая проверка, состоит ли строка только из цифр.
fun String.isDigitsOnly(): Boolean = all { it.isDigit() }


// Удобное расширение для заглавной буквы в строке.
fun String.capitalizeFirstLetter(): String = this.replaceFirstChar { it.uppercase() }


// Разбивает список на части указанного размера
fun <T> List<T>.chunkedWithLimit(chunkSize: Int): List<List<T>> {
    if (chunkSize <= 0) return listOf(this)
    return this.chunked(chunkSize)
}


// Позволяет найти максимальный элемент в списке по заданному критерию.
fun <T, R : Comparable<R>> List<T>.findMaxBy(selector: (T) -> R): T? = maxByOrNull(selector)


// Создает новый перемешанный список из текущего.
fun <T> List<T>.shuffledList(): List<T> = this.shuffled()


// Позволяет конвертировать строку в число, возвращая 0 при невозможности конвертации.
fun String.toIntOrZero(): Int = this.toIntOrNull() ?: 0


// Объединяет список в строку с заданным разделителем.
fun <T> List<T>.joinToStringWithSeparator(separator: String = ", "): String = joinToString(separator)



// ============== Утилиты для работы с контекстом и безопасными вызовами ==============

// Удобное расширение для получения строки по идентификатору ресурса в контексте.
fun Context.getStringResource(@StringRes resId: Int): String = this.getString(resId)


// Расширение для безопасной работы с Context в Compose.
@Composable
fun <T> safeContext(block: (Context) -> T): T? {
    val context = LocalContext.current
    return try { block(context) }
    catch (e: Exception) { null }
}


// Позволяет быстро проверить, есть ли подключение к сети.
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}


// Чтение текстового файла из папки raw с минимальными усилиями.
fun Context.readTextFromRaw(@RawRes resId: Int): String {
    return resources.openRawResource(resId).bufferedReader().use { it.readText() }
}


// Удобное расширение для показа Toast с минимальными усилиями.
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


// Утилита для выполнения вызова с обработкой ошибок.
inline fun <T> safeCall(action: () -> T, onError: (Exception) -> Unit): T? {
    return try { action() }
    catch (e: Exception) {
        onError(e)
        null
    }
}


// Асинхронный вызов с обработкой результата и ошибок через CoroutineScope.
fun <T> asyncCall(
    scope: CoroutineScope,
    onSuccess: (T) -> Unit,
    onError: (Exception) -> Unit = {}
): (suspend () -> T) -> Unit = { task ->
    scope.launch {
        try { onSuccess(task()) }
        catch (e: Exception) { onError(e) }
    }
}


// Утилита для получения цвета из ресурсов.
fun Context.getColorFromResources(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}
