package com.kuvalin.brainstorm.globalClasses

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import okio.IOException

/*
Данный файл является зачатком библиотеки-инструментария для кодинга "в моём стиле".
Потихонечку будут добавляться универсальные и многофункциональные шаблоны для различных
элементов и их видов.

Реализации различных удобных фич для работы с андроид студией и тп, и тд
*/


// ###################### АКТИВНЫЕ ######################

// ###################### Общие
//region Dp.toPx() - перевод в пиксели
@Composable
fun Dp.toPx() = with(LocalDensity.current) {
    this@toPx.toPx()
}
//endregion

//region Динамический (адаптивный) размер текста
@Composable
fun dynamicFontSize(screenWidth: Int, desiredFontSize: Float): TextUnit {
    // Высчитываем коэффициент на основе желаемого результата
    val coefficient = screenWidth / desiredFontSize
    // Возвращаем желаемый размер шрифта, деленный на этот коэффициент
    return (screenWidth / coefficient).sp
}
//endregion

/* Развить потом идею
@Composable
private fun AdaptiveBoxContent(scale: Float, content: @Composable () -> Unit) {
    Box(modifier = Modifier.scale(scale)) {content()}
}
*/
// ######################

// ###################### Assets (дописать сохранение в базу)
//region AssetImage
@Composable
fun AssetImage(fileName: String, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val assetManager: AssetManager = context.assets
    val inputStream = assetManager.open(
        findAssetFiles(context, fileName)[fileName]?.get(0) ?: "Файл не найден"
    )
    val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()

    Image(
        bitmap = bitmap,
        contentDescription = null,
        modifier = modifier
    )
}
//endregion
//region GetAssetBitMap
@Composable
fun GetAssetBitmap(fileName: String): ImageBitmap {

    val context = LocalContext.current
    val assetManager: AssetManager = context.assets
    val inputStream = assetManager.open(
        findAssetFiles(context, fileName)[fileName]?.get(0) ?: "Файл не найден"
    )

    return BitmapFactory.decodeStream(inputStream).asImageBitmap()
}
//endregion

val resultPaths = mutableMapOf<String, List<String>>()
//region Find Asset Files - ищет файл в assets и кладет его в словарь, откуда потом достанет
fun findAssetFiles(context: Context, fileName: String): Map<String, List<String>> {
    val fileType = fileName.substringAfterLast(".")

    fun searchInDirectory(directory: String): List<String> {
        try {
            val assetManager = context.assets
            val list = assetManager.list(directory)

            if (list != null) {
                for (file in list) {
                    val fullPath = if (directory.isNotEmpty()) "$directory/$file" else file

                    if (file == fileName && fullPath.endsWith(fileType)) {
                        return listOf(fullPath)
                    }

                    if (assetManager.list(fullPath)?.isNotEmpty() == true) {
                        // Recursive call for subdirectories
                        val subDirectoryResult = searchInDirectory(fullPath)
                        if (subDirectoryResult.isNotEmpty()) {
                            return subDirectoryResult
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return emptyList()
    }

    val existingPaths = resultPaths[fileName]
    return if (existingPaths != null) {
        // Если путь уже найден, возвращаем его
        mapOf(fileName to existingPaths)
    } else {
        // Иначе ищем путь
        val foundPaths = searchInDirectory("")
        resultPaths[fileName] = foundPaths
        mapOf(fileName to foundPaths)
    }
}
//endregion
//region Populate Result Paths - наполняет resultPaths при загрузке приложения
fun populateResultPaths(context: Context, listFiles: List<String>? = null) {
    val assetManager = context.assets

    fun searchInDirectory(directory: String) {
        try {
            val list = assetManager.list(directory)

            if (list != null) {
                for (file in list) {
                    val fullPath = if (directory.isNotEmpty()) "$directory/$file" else file

                    if (listFiles != null) {
                        if (fullPath in listFiles) {
                            resultPaths[file] = listOf(fullPath)
                        }
                    } else {
                        resultPaths[file] = listOf(fullPath)
                    }

                    if (assetManager.list(fullPath)?.isNotEmpty() == true) {
                        // Recursive call for subdirectories
                        searchInDirectory(fullPath)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    if (listFiles != null) {
        listFiles.forEach { fileName ->
            searchInDirectory("")
        }
    } else {
        searchInDirectory("")
    }
}
//endregion
//region Find Asset Files (старая версия)
//fun findAssetFiles(context: Context, fileName: String): List<String> {
//    val resultPaths = mutableListOf<String>()
//    val fileType = fileName.substringAfterLast(".")
//
//    fun searchInDirectory(directory: String) {
//        try {
//            val assetManager = context.assets
//            val list = assetManager.list(directory)
//
//
//            if (list != null) {
//                for (file in list) {
//                    val fullPath = if (directory.isNotEmpty()) "$directory/$file" else file
//
//                    if (file == fileName && fullPath.endsWith(fileType)) {
//                        resultPaths.add("$fullPath")
//                    }
//
//
//                    if (assetManager.list(fullPath)?.isNotEmpty() == true) {
//                        // Recursive call for subdirectories
//                        searchInDirectory(fullPath)
//                    }
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    searchInDirectory("")
//    return resultPaths
//}
//endregion
// ######################


// ###################### Музыка
//region Play Sound
@SuppressLint("CoroutineCreationDuringComposition")
fun playSound(mMediaPlayer: MediaPlayer, scope: CoroutineScope): Boolean {
    return !(scope.async {mMediaPlayer.start() }.isCompleted)
}
//endregion
// ######################


// ###################### Анимация нажатия
//region Расширение Modifier для создания кликабельного элемента без волнового эффекта
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
//endregion
//region Убивает сраную пульсацию (НО, БЛЯТЬ, ТОЛЬКО НА ЭМУЛЯТОРЕ - ПРОВЕРЬ НА ДРУГИХ ТЕЛЕФОНАХ)
object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}
//endregion
//region НА КО НЕЦ ТО     СУУУ КААА
class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions = MutableSharedFlow<Interaction>()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}
//endregion
// ######################



// ######################################################


//region Хрень, чтобы вычислить размеры родительского элемента
/*
modifier = Modifier
            .onGloballyPositioned { coordinates ->
                parentWidth = with(localDensity) {
                    coordinates.size.width.toDp().value.toInt()
                }
            }
*/
//endregion



// ##################### СОХРАНЯШКИ #####################

// ###################### DEBUG
//Log.d("DEBUG-1", "--------------START--------------")
//Log.d("DEBUG-1", "--------------END--------------")
//Log.d("DEBUG-1", "-------------- $userInfoOpponent -------------- userInfoOpponent")
//Log.d("DEBUG-1", "-------------- ${_userName.value} -------------- _userName.value")
//Log.d("DEBUG-1", "-------------- RECOMPOSITION --------------")


//Log.d("DEBUG-1", "-------------- 1 --------------")
//Log.d("DEBUG-1", "-------------- 2 --------------")
//Log.d("DEBUG-1", "-------------- 3 --------------")
//Log.d("DEBUG-1", "-------------- 3-1 --------------")
// ######################


//region Оформления документации
// ######################

/**
 * # Заголовок первого уровня
 * ## Заголовок второго уровня
 * ### Заголовок третьего уровня
 */

/**
 * Различные виды форматирования текста
 *
 * Порядковый список:
 * 1. Шаг первый
 * 2. Шаг второй
 * 3. Шаг третий
 *
 * Маркированный список:
 * - Функция первая
 * - Функция вторая
 * - Функция третья
 *
 * **Жирный шрифт**:
 * **Важная информация**
 *
 * *Курсив*:
 * *Пример выделения текста курсивом*
 *
 * > Цитата:
 * > Это цитата внутри комментария.
 *
 */

// Для вставки кода в документацию
/**
 * ```
 * fun example() {
 *     println("Пример кода")
 * }
 * ```
 */

/**
 * Параметры:
 * @param clickNavigation Стейт для обработки нажатий.
 * @param viewModel Вьюмодель для работы с состоянием экрана.
 */

/**
 * ### Свойства:
 * @property addAppSettingsUseCase Use case для добавления настроек.
 * @property getAppSettingsUseCase Use case для получения настроек.
 */

/**
 * Общие аннотации:
 * @param описание параметра функции или конструктора.
 * @property описание свойства класса.
 * @throws описание исключений, которые могут быть выброшены функцией.
 * @see ссылка на другой элемент кода.
 * @sample com.kuvalin.brainstorm.globalClasses.AssetImage
 *
 */

/**
 * ### Прочие аннотации:
 * @receiver описание объекта-получателя (для extension-функций).
 * @return описание возвращаемого значения функции.
 * @constructor описание конструктора класса.
 * @since версия, с которой данный элемент был добавлен.
 * @author автор элемента.
 * @suppress подавление определенных предупреждений.
 * @deprecated Помечает метод или класс как устаревший (Используйте новый метод `newMethod` вместо этого).
 */

// ДОПОЛНИТЕЛЬНО:
// Пример оформления ссылок на другие элементы кода или внешние ресурсы
/**
 * @see [com.example.MyClass]
 * @see <a href="http://example.com">Example</a>
 */

// Таблицы
/**
 * | Заголовок 1 | Заголовок 2 |
 * |-------------|-------------|
 * | Ячейка 1    | Ячейка 2    |
 */


// Способы разделения пространства
/**
 * Примеры:
 * 1. Пустые строки
 * 2. -------------------
 * 3. ➖➖➖➖➖➖➖➖➖➖
 * 4. Использование заголовков различных уровней:
 *    - 4.1 # Заголовок первого уровня
 *    - 4.2 ## Заголовок второго уровня
 *    - 4.3 ### Заголовок третьего уровня
 */



// Смайлики
/**
🔹 Общие и универсальные:
🔹, 📊, ✨, ❗️, ❌, 🌐, 🌀, 📍, ⚠️,
⚡️, 📚, ✅, 📱, 💡, 🔮, 🔔, 🔑, ❓,
✍️, 🔎, 📈, 👉, 👈, ❄️, 📋, 📆
💥, 💬, 📢, 📜, ✈️, 🚀, 🛸, ⏳, 💣

⚙️ Технические и инструментальные:
📱, 💻, ⚙️, 🧲, ☢️, ☯️, 🔎, 🔓, 🔒, 🔍

📅 Процесс и временные метки:
🕗, ⏳, 📆, ⏩, ⏪, ⤵️, ⤴️, ↩️, ↪️, 🔼, 🔽

🟢🟡🔴 Цветовые маркеры:
🔴, 🟠, 🟡, 🟢, 🔵, 🟣, ⚫️, ⚪️, 🟥,
🟧, 🟨, 🟩, 🟦, 🟪, ⬛️, ⬜️, 🟫,
🔳, 🔘, ⚪, ⚫, ⬛, 📗, 📘, 📙, 🛑,

🌐 Природа и элементы:
☀️, 🌙, ⭐️, 🌟, 🌈, 🌍, 🌏, 🌸, 💐, 💧, 🔥

🎉 Развлечения и достижения:
🎉, 🎁, 🏆, 💎

👨‍💻 Для программиста:
👨‍💻, 💾, 📂, 📝, 🔧, 📠, 🔐

🏯 Для архитектора:
🏢, 🏠, 🏯, 🕌, 📐, 📏,

🎨 Для художника:
🎨, 🎭, 🎬, ✏️, 📜, ✂️, 📸

🪛 Инженерные:
🧲, 🪛, 🧬, ⛏, 🧱, 🧪

🔢 Для математика:
🔢, ➕, ➖, ✖️, ➗, =, ≠, √, ∞, ∑, π, ∫, ∂, ∇, ∆, ⊕, ⊗, ⊖, ⊘, ∝, ∠, ⊆, ⊇, ⊂, ⊃, ∉, ∈, ∋, ∅

1️⃣ Для нумерованных списков:
1️⃣, 2️⃣, 3️⃣, 4️⃣, 5️⃣, 6️⃣, 7️⃣, 8️⃣, 9️⃣, 0️⃣, 🔟

🎨 Выразительные предметы и символы:
🔺, 🔻, 🔸, 🔹, ✨, 💥, ⚡️, ✅,
🔍, 🔎, 🔬, 🔭, 🔧, 🛒, 🔦, 🕗,
➡️, ⬅️, ⬆️, ⬇️, ↔️, ↕️, ↖️, ↗️,
↘️, ↙️, ↪️, ↩️, 🔄, 🔃, 🔂, 🔁,
🧮, 📌, 📍, 📊, 🗓️, 🧷, 🪟, 🗝️,
🧻, 🧿, 🧩, 🔫, 💣, 🕯️, 🧵, 💊,
🎯, ⚽️, 🏀, 🎁, 🏆, 💎, 🌐
*/


/** Полезные приемы для комментариев */
// TODO: Добавить проверку входных данных
// FIXME: Исправить потенциальную утечку памяти
// NOTE: Этот метод используется только для тестирования
// ######################
//endregion


// ############ Мини-блок
// ########################


/* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ #################### 🌈 ############# */
/* ########################################################################################## */

/* ############# 🔄 ###################### BackHandler #################### 🔄 ############## */
/* ########################################################################################## */

/* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */
/* ########################################################################################## */

/* ############# 🟢 ################## ОСНОВНЫЕ ФУНКЦИИ ################## 🟢 ############### */
/* ########################################################################################## */

/* ############# 🟡 ################ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############# 🟡 ############### */
/* ########################################################################################## */




/* -------------------------------- warSearchScreen ------------------------------------------*/
/* -------------------------------------------------------------------------------------------*/


// ######################################################



// ##################### РАЗЛИЧНАЯ ТЕОРИЯ #####################

//region 1. Log.d, Log,w и далее

/**
 * Log.d:

  * Этот метод используется для записи отладочных сообщений (debug).
  * Он предназначен для вывода информации, которая полезна при разработке и отладке приложения.
  * Сообщения, записываемые с помощью Log.d, обычно содержат информацию о состоянии приложения,
  * значения переменных, потоке выполнения и так далее. Обычно эти сообщения выводятся в лог,
  * когда приложение находится в режиме отладки.

 * -

 * Log.w:

  * Этот метод используется для записи предупреждений (warnings).
  * Он предназначен для сообщений об ошибках или неожиданных ситуациях, которые не приводят
  * к критическому сбою приложения, но требуют внимания разработчика. Такие сообщения обычно
  * указывают на потенциальные проблемы в коде или настройках приложения.
*/
//endregion

//region 2. Важные советы по оптимизации кода

/**

 1. Использование CoroutineScope в Composable: Использование CoroutineScope в Composable напрямую
 может привести к утечкам памяти. Используйте rememberCoroutineScope для создания корутин в Composable.

 */
//endregion

// ############################################################



/* ############# 🛑 ###################### ЧУЛАНЧИК ###################### 🛑 ############### */
//region Отслеживание перемещения
// Отслеживание перемещения
//Column(
//modifier = Modifier
//.fillMaxSize()
//.offset(y = (-topBarHeight).dp) // Костыль
//.pointerInput(Unit) {
//    detectDragGestures(
//        onDragStart = {
//            movingState = true
//        },
//        //region onDragEnd
//        onDragEnd = {
//
//            if (listClickableIndexes.count { it == 1 } < 2 && startStage) {
//                resultsList.add(-1)
//                MusicPlayer(context = context).playErrorInGame()
//
//                movingState = false
//                currentCell = -1
//                listCell = mutableListOf()
//                listClickableIndexes = mutableListOf()
//
//                listIndexFirst = listOfLists
//                    .random()
//                    .toMutableList()
//                listIndexSecond =
//                    listIndexFirst.map { if (it == 3 || it == 2) 0 else it }
//
//                startStage = false
//            }
//        },
//        //endregion
//        //region onDrag
//        onDrag = { change, dragAmount ->
//            if (startStage) {
//                val newPosition = change.position
//                touchPosition = Offset(newPosition.x, newPosition.y)
//                coroutineScope.launch {
//                    checkTouchPosition(cellBounds)
//                }
//
//                if (listClickableIndexes.count { it == 1 } >= 2) {
//                    if (3 in listClickableIndexes) {
//                        MusicPlayer(context = context).playErrorInGame()
//                        resultsList.add(-1)
//                    } else if (2 in listClickableIndexes) {
//                        MusicPlayer(context = context).playSuccessInGame()
//                        resultsList.add(3)
//                    } else {
//                        MusicPlayer(context = context).playSuccessInGame()
//                        resultsList.add(2)
//                    }
//
//                    movingState = false
//                    currentCell = -1
//                    listCell = mutableListOf()
//                    listClickableIndexes = mutableListOf()
//
//                    listIndexFirst = listOfLists
//                        .random()
//                        .toMutableList()
//                    listIndexSecond =
//                        listIndexFirst.map { if (it == 3 || it == 2) 0 else it }
//
//                    startStage = false
//                }
//            }
//        }
//        //endregion
//    )
//}
//) {
//    if (startStage){
//        LaunchedEffect(cellBounds, touchPosition) {
//            checkTouchPosition(cellBounds)
//        }
//    }
//
//    //region Box - под мышкой. Если находится в нужной области красится в красный
//    //        Box(
////            modifier = Modifier
////                .zIndex(5f)
////                .size(10.dp)
////                .drawWithContent {
////                    val position = touchPosition
////                    this.drawContent()
////                    val boxSize = 10.dp.toPx() // размеры Box
////                    val topLeftX = position.x - (boxSize / 2) // центрирование по X
////                    val topLeftY = position.y - (boxSize / 2) // центрирование по Y
////                    drawRect(
////                        color = if (isInCell) Color.Red else Color.Green,
////                        topLeft = Offset(topLeftX, topLeftY),
////                        size = Size(boxSize, boxSize)
////                    )
////                }
////        )
//    //endregion
//    Spacer(modifier = Modifier.weight(3f)) // Имитация LazyVerticalGrid
//    //region Кнопка "Запомнил"
//    Box(
//        modifier = Modifier
//            .weight(1f)
//            .align(Alignment.CenterHorizontally)
//            .offset(y = (topBarHeight).dp) // Костыль
//    ){
//        if (!startStage) {
//            StringButton(color = Color(0xFFFF7700)){
//                Log.d("DEBUG-11", "------------ $isDraggingMap -----------isDraggingMap-1")
//                Log.d("DEBUG-11", "------------ $listCell -----------listCell-1")
//                Log.d("DEBUG-11", "------------ $listClickableIndexes -----------listClickableIndexes-1")
//                startStage = true
//            }
//        }
//    }
//    //endregion
//}
//endregion
//region Шаблон Dialog

// ❗️❗️❗️ Потом подумать, допилить и реализовать ❗️❗️❗️

/*
private fun createCustomDialog(
    viewModel: MenuViewModel,
    context: Context,
    title: String,
    content: @Composable () -> Unit,
    onClickDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            viewModel.playChoiceClickSound(context)
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(color = BackgroundAppColor)
            ) {
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            viewModel.playChoiceClickSound(context)
                            onClickDismiss()
                        }
                )
                LabelText(title)
                content()
            }
        }
    )
}
*/

//endregion
/* ########################################################################################## */



/*
🛑 Подумать потом и раскидвать по приле, чтобы код сократить (у там есть большие блоки с различными звуками)

private fun playSoundAndDismiss(context: Context, onClickDismiss: () -> Unit) {
    CoroutineScope(Dispatchers.Default).launch {
        MusicPlayer(context = context).run {
            playChoiceClick()
            delay(3000)
            release()
        }
    }
    onClickDismiss()
}

*/






class Pystoi_klacc_dlya_paboti_docymmentation() {}









