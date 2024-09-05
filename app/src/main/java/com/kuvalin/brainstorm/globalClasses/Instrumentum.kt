package com.kuvalin.brainstorm.globalClasses

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

/*
Данный файл является зачатком библиотеки-инструментария для кодинга "в моём стиле".
Потихонечку будут добавляться универсальные и многофункциональные шаблоны для различных
элементов и их видов.

Реализации различных удобных фич для работы с Android Studio и тп, и тд
*/


/** ---- АКТИВНЫЕ ФУНКЦИИ ---- */
//region ########### 💎 ########### АКТИВНЫЕ ФУНКЦИИ ########### 💎 ###########

// ✨✨✨✨✨✨✨✨✨✨✨ РАЗМЕРЫ ✨✨✨✨✨✨✨✨✨✨✨
//region Dp.toPx() - перевод в пиксели
@Composable
fun Dp.toPx() = with(LocalDensity.current) {
    this@toPx.toPx()
}
//endregion
//region Динамические (адаптивные) размеры

// Параметр baseDimension (основное измерение) в функции, который будет определять,
// использовать ли ширину, высоту или среднее арифметическое для расчета коэффициента.
@Composable
fun DynamicFontSize(baseDimension: Int, desiredFontSize: Float): TextUnit {
    // Высчитываем коэффициент на основе желаемого результата
    val coefficient = baseDimension / desiredFontSize
    // Возвращаем желаемый размер шрифта, деленный на этот коэффициент
    return (baseDimension / coefficient).sp
}
@Composable
fun DynamicSize(baseDimension: Int, desiredSize: Float): Dp {
    // Высчитываем коэффициент на основе желаемого результата
    val coefficient = baseDimension / desiredSize
    // Возвращаем желаемый размер шрифта, деленный на этот коэффициент
    return (baseDimension / coefficient).dp
}
//endregion
// ✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨✨


/** ---- ASSETS ---- */
// ###################### Assets (дописать сохранение в базу)
val imageCache = mutableMapOf<String, ImageBitmap>()
//region AssetImage
@Composable
fun AssetImage(fileName: String, modifier: Modifier = Modifier) {
    val context = rememberUpdatedState(LocalContext.current)
    var bitmapState by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(fileName) {
        withContext(Dispatchers.IO) {

            // Проверка на наличие изображения в кэше
            val cachedBitmap = imageCache[fileName]
            if (cachedBitmap != null) { bitmapState = cachedBitmap }
            else {
                val fullPath = findAssetFiles(context.value, fileName)
                    ?: throw IllegalArgumentException("File not found: $fileName")

                val assetManager: AssetManager = context.value.assets
                val inputStream = assetManager.open(fullPath)
                val bitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap()
                imageCache[fileName] = bitmap
                bitmapState = bitmap
            }
        }
    }

    bitmapState?.let { bitmap ->
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = modifier
        )
    }
}
//endregion
//region GetAssetBitMap
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GetAssetBitmap(fileName: String): ImageBitmap {
    val context = rememberUpdatedState(LocalContext.current)

    val cachedBitmap = imageCache[fileName]
    return if (cachedBitmap != null) {
        cachedBitmap
    } else {
        val fullPath = findAssetFiles(context.value, fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")
        val assetManager: AssetManager = context.value.assets
        val inputStream = assetManager.open(fullPath)
        BitmapFactory.decodeStream(inputStream).asImageBitmap()
    }

}
//endregion

val resultPaths = mutableMapOf<String, String>()
//region Find Asset Files - ищет файл в assets и кладет его в словарь, откуда потом достанет
fun findAssetFiles(context: Context, fileName: String): String? {
    // Проверка на наличие пути в кэше
    val existingPath = resultPaths[fileName]
    if (existingPath != null) {
        return existingPath
    }

    // Очередь для обхода всех директорий
    val assetManager: AssetManager = context.assets
    val directories = ArrayDeque<String>().apply { add("") }

    // Итеративный поиск файла в директориях assets
    while (directories.isNotEmpty()) {
        val directory = directories.removeFirst()
        val fileList = assetManager.list(directory) ?: continue

        for (file in fileList) {
            val fullPath = "$directory/$file".trimStart('/')

            if (file == fileName) {
                resultPaths[fileName] = fullPath
                return fullPath
            }

            // Добавляем поддиректории в очередь для поиска
            if (assetManager.list(fullPath)?.isNotEmpty() == true) {
                directories.add(fullPath)
            }
        }
    }

    return null // Файл не найден
}
//endregion
//region Populate Result Paths - наполняет resultPaths при загрузке приложения
fun populateResultPaths(context: Context) {
    val assetManager: AssetManager = context.assets

    fun searchInDirectory(directory: String) {
        val fileList = assetManager.list(directory) ?: return

        for (file in fileList) {
            val fullPath = "$directory/$file".trimStart('/')

            resultPaths[file] = fullPath

            // Добавляем поддиректории в очередь для поиска
            if (assetManager.list(fullPath)?.isNotEmpty() == true) {
                searchInDirectory(fullPath)
            }
        }
    }

    searchInDirectory("")
}
//endregion
// ######################


/** ---- МУЗЫКА ---- */
// ###################### Музыка
// ######################


/** ---- НАЖАТИЯ ---- */
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
//endregion ####################################################################


/** ---- СОХРАНЯШКИ ---- */
//region ########## 🔒 ########### СОХРАНЯШКИ ########## 🔒 ###########

/** 1 */
//region ####### ⚙️ ######### ⚙️ ####### DEBUG
//Log.d("DEBUG-1", "--------------START--------------")
//Log.d("DEBUG-1", "--------------END--------------")
//Log.d("DEBUG-1", "-------------- $userInfoOpponent -------------- userInfoOpponent")
//Log.d("DEBUG-1", "-------------- ${_userName.value} -------------- _userName.value")
//Log.d("DEBUG-1", "-------------- RECOMPOSITION --------------")


//Log.d("DEBUG-1", "-------------- 1 --------------")
//Log.d("DEBUG-1", "-------------- 2 --------------")
//Log.d("DEBUG-1", "-------------- 3 --------------")
//Log.d("DEBUG-1", "-------------- 3-1 --------------")
//endregion ###### ⚙️ ######## ⚙️ ######

/** 2 */
//region ❌❌❌❌❌❌❌❌❌❌❌❌ >>> Примеры оформления документации

/** Документация */
//region 🔹🔹📋🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹
//region Заголовки
/**
 * # Заголовок первого уровня
 * ## Заголовок второго уровня
 * ### Заголовок третьего уровня
 */
//endregion
//region Различные виды форматирования текста
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
//endregion
//region Для вставки кода в документацию
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
//endregion

//region ДОПОЛНИТЕЛЬНО:
// Пример оформления ссылок на другие элементы кода или внешние ресурсы
/**
 * @see [com.example.MyClass]
 * @see <a href="http://example.com">Example</a>
 */

//region Таблицы
/**
 * | Заголовок 1 | Заголовок 2 |
 * |-------------|-------------|
 * | Ячейка 1    | Ячейка 2    |
 */
//endregion
//region Способы разделения пространства
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
//endregion
//endregion


//endregion 🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹📋🔹🔹🔹

/** Смайлики */
//region ➖➖➖➖☢️➖➖➖➖☢️➖➖➖➖☢️➖➖➖➖☢️➖➖➖➖
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
❶ ❷ ❸ ❹ ❺ ❻ ❼ ❽ ❾ ❿

🎨 Выразительные предметы и символы:
🔺, 🔻, 🔸, 🔹, ✨, 💥, ⚡️, ✅,
🔍, 🔎, 🔬, 🔭, 🔧, 🛒, 🔦, 🕗,
➡️, ⬅️, ⬆️, ⬇️, ↔️, ↕️, ↖️, ↗️,
↘️, ↙️, ↪️, ↩️, 🔄, 🔃, 🔂, 🔁,
🧮, 📌, 📍, 📊, 🗓️, 🧷, 🪟, 🗝️,
🧻, 🧿, 🧩, 🔫, 💣, 🕯️, 🧵, 💊,
🎯, ⚽️, 🏀, 🎁, 🏆, 💎, 🌐
*/
//endregion ➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖

/** ТУДУХИ */
//region ✏️ TODO ✏️ FIXME ✏️ FIXME
// TODO: Добавить проверку входных данных
// FIXME: Исправить потенциальную утечку памяти
// NOTE: Этот метод используется только для тестирования
// Какие ещё бывают?
//endregion #######################

//endregion ❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌

/** 3 */
//region 🔻🔻🔻🔻🔻🔻🔻🔻 ОФОРМЛЕНИЕ ФАЙЛА 🔻🔻🔻🔻🔻🔻🔻🔻🔻

// ############ Мини-блок
// ########################


/* ############# 🌈 ##################### ИНИЦИАЛИЗАЦИЯ ##################### 🌈 ############# */
/* ########################################################################################### */
//region ############# 🌈 ################# ИНИЦИАЛИЗАЦИЯ ################## 🌈 ############# */
//endregion ################################################################################## */


/* ############# 🔄 ###################### BackHandler #################### 🔄 ############## */
/* ########################################################################################## */
//region ############# 🔄 ################## BackHandler ################## 🔄 ############## */
//endregion ################################################################################# */


/* ################ 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ################## */
/* ########################################################################################## */
//region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
//endregion ################################################################################# */


/* ################ 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ################ */
/* ########################################################################################## */
//region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
//endregion ################################################################################## */


/* ################ 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ################# */
/* ########################################################################################## */
//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//endregion ################################################################################## */

/* -------------------------------- warSearchScreen ------------------------------------------*/
/* -------------------------------------------------------------------------------------------*/

//endregion 🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻🔻

//endregion ############################################################


/** ---- РАЗЛИЧНАЯ ТЕОРИЯ ---- */
//region ############################################################
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
//endregion ############################################################

 
/** ---- ЧУЛАНЧИК ---- */
/*region ############# 🛑 ######################### 🛑 ######################## 🛑 ############### */
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
//region Развить потом идею - AdaptiveBoxContent
/*
@Composable
private fun AdaptiveBoxContent(scale: Float, content: @Composable () -> Unit) {
    Box(modifier = Modifier.scale(scale)) {content()}
}
*/
//endregion
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
/*endregion ########################################################################################## */



class Pystoi_klacc_dlya_paboti_docymmentation() {}









