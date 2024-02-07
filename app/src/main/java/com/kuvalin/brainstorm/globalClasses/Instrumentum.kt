package com.kuvalin.brainstorm.globalClasses

import android.content.Context
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import okio.IOException


// ###################### АКТИВНЫЕ ######################

//region Dp.toPx() - перевод в пиксели
@Composable
fun Dp.toPx() = with(LocalDensity.current) {
    this@toPx.toPx()
}
//endregion
//region AssetImage
@Composable
fun AssetImage(fileName: String, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val assetManager: AssetManager = context.assets
    val inputStream = assetManager.open(
        findAssetFiles(context, fileName)[0]
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
        findAssetFiles(context, fileName)[0]
    )

    return BitmapFactory.decodeStream(inputStream).asImageBitmap()
}
//endregion


// Сделать в базе архивчик, в который складывать пути к известным файлам
//region Find Asset Files (если будет находить несколько файлов с одинаковым именем, будет выдавать все пути)
fun findAssetFiles(context: Context, fileName: String): List<String> {
    val resultPaths = mutableListOf<String>()
    val fileType = fileName.substringAfterLast(".")

    fun searchInDirectory(directory: String) {
        try {
            val assetManager = context.assets
            val list = assetManager.list(directory)


            if (list != null) {
                for (file in list) {
                    val fullPath = if (directory.isNotEmpty()) "$directory/$file" else file

                    if (file == fileName && fullPath.endsWith(fileType)) {
                        resultPaths.add("$fullPath")
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

    searchInDirectory("")
    return resultPaths
}
//endregion


//region Расширение Modifier для создания кликабельного элемента без волнового эффекта
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

// ######################################################








/* На память

2. Получение ширины экрана:

Вариант-1:
val configuration = LocalConfiguration.current
val screenHeight = configuration.screenHeightDp.dp.toPx()
val screenWidth = configuration.screenWidthDp.dp.toPx()


Вариант-2: (спорная хрень)

class Size {
    @Composable
    fun height(): Int {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp
    }
    @Composable
    fun width(): Int {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp
    }
}


val size = Size()
val screenHeight = size.height()

Box(modifier = Modifier.height((screenHeigh/2).dp)) {
    //content
}


// 3. Отключает анимацию у кнопок
// https://stackoverflow.com/questions/66703448/how-to-disable-ripple-effect-when-clicking-in-jetpack-compose
object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}

// 4. sortedSetOf - сортировка
// Под капотом он ссылается на TreeSet, а в лямба выражении указывается, по каким парам сортировать.
// Это короче метод сортиворки массива (потом когда-нибудь почитать)
//    private val shopList = sortedSetOf<ShopItem>(object : Comparator<ShopItem>{
//        override fun compare(p0: ShopItem?, p1: ShopItem?): Int {
//        }
//    })
//private val shopList = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id) })


5. Удобная штучка
    .then(
        if (selected)
            Modifier
                .border(0.dp, Color.Transparent, shape = CircleShape)
                .border(3.dp, Color.Red, shape = RectangleShape)
        else
            Modifier
    )

*/

// Видимо статья должна раскрыть, что это
/*
// https://stackoverflow.com/questions/66251718/scaling-button-animation-in-jetpack-compose
@Composable
fun AnimatedButton() {
    val boxHeight = animatedFloat(initVal = 50f)
    val relBoxWidth = animatedFloat(initVal = 1.0f)
    val fontSize = animatedFloat(initVal = 16f)

    fun animateDimensions() {
        boxHeight.animateTo(45f)
        relBoxWidth.animateTo(0.95f)
       // fontSize.animateTo(14f)
    }

    fun reverseAnimation() {
        boxHeight.animateTo(50f)
        relBoxWidth.animateTo(1.0f)
        //fontSize.animateTo(16f)
    }

        Box(
        modifier = Modifier
            .height(boxHeight.value.dp)
            .fillMaxWidth(fraction = relBoxWidth.value)

            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .clickable { }
            .pressIndicatorGestureFilter(
                onStart = {
                    animateDimensions()
                },
                onStop = {
                    reverseAnimation()
                },
                onCancel = {
                    reverseAnimation()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Explore Airbnb", fontSize = fontSize.value.sp, color = Color.White)
    }
}

*/
// Что-то не помню, где это применял. Нужно искать в FindTheParents
//region Backup TopAppBar
//TopAppBar(
//            modifier = Modifier
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(Color.Cyan, Color.Magenta),
//                        start = Offset(0.dp.toPx(), rotateColorAnimation2.dp.toPx()),
//                        end = Offset(rotateColorAnimation2.dp.toPx(), -rotateColorAnimation.dp.toPx()),
//                        tileMode = TileMode.Mirror,
//                    ),
//                    alpha = 0.1f
//                ),
//            title = {
//                Text(text = "Главное меню")
//            },
//            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent), // Убирает родной цвет
//            navigationIcon = {
//                IconButton(onClick = { onStopGameClick() }) {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = null
//                    )
//                }
//            }
//        )
//endregion
// Тоже пока не смекну, зачем сохранял
//region class FindTheParentsApplication
//class FindTheParentsApplication: Application() {
//
//    val component by lazy {
//        DaggerApplicationComponent.factory().create(this)
//    }
//
//}
//
//
//@Composable
//fun getApplicationComponent(): ApplicationComponent {
//    return (LocalContext.current.applicationContext as FindTheParentsApplication).component
//}
//endregion
// Прикольная функция для списков fold (вообще не ебу, что это)
/*

val items = listOf(1, 2, 3, 4, 5)

// Lambdas are code blocks enclosed in curly braces.
items.fold(0, {
    // When a lambda has parameters, they go first, followed by '->'
    acc: Int, i: Int ->
    print("acc = $acc, i = $i, ")
    val result = acc + i
    println("result = $result")
    // The last expression in a lambda is considered the return value:
    result
})

// Parameter types in a lambda are optional if they can be inferred:
val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// Function references can also be used for higher-order function calls:
val product = items.fold(1, Int::times)

acc = 0, i = 1, result = 1
acc = 1, i = 2, result = 3
acc = 3, i = 3, result = 6
acc = 6, i = 4, result = 10
acc = 10, i = 5, result = 15
joinedToString = Elements: 1 2 3 4 5
product = 120


Имена для лямбды!
typealias ClickHandler = (Button, ClickEvent) -> Unit


Во как ещё можно!
Обозначение стрелки правоассоциативно, (Int) -> (Int) -> Unit эквивалентно предыдущему примеру,
но не ((Int) -> (Int)) -> Unit


АНОНИМНАЯ ФУНКЦИЯ!
fun(s: String): Int { return s.toIntOrNull() ?: 0 }

*/



