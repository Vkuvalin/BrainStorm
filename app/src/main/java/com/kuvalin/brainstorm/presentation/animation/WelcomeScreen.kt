package com.kuvalin.brainstorm.presentation.animation


import android.graphics.Paint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import com.kuvalin.brainstorm.ui.theme.WelcomeScreenBeige
import com.kuvalin.brainstorm.ui.theme.WelcomeScreenBlue
import com.kuvalin.brainstorm.ui.theme.WelcomeScreenLightOrange
import com.kuvalin.brainstorm.ui.theme.WelcomeScreenOrange
import com.kuvalin.brainstorm.ui.theme.WelcomeScreenRed
import com.kuvalin.brainstorm.ui.theme.WelcomeScreenYellow
import com.kuvalin.brainstorm.ui.theme.WhiteAppBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ## Управляющая функция запуска и смены анимации
 *
 * delayMilsLoading - заглушка, имитирующая загрузку:
 * - фоток;
 * - экранов;
 * - прочей информации из интернета;
 * - ПЕРЕЧЕСЛЕНИЕ = НАПОМИНАНИЕ --> TODO сделать позже
 */
//region WelcomeScreen
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreen(delayMilsLoading: Long, onStartMainMenuClick: () -> Unit) {



    // Триггер для смены анимации
    var isBrainAnimationLaunched by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {

        AnimatedContent(
            targetState = isBrainAnimationLaunched,
            transitionSpec = {
                fadeIn(tween(durationMillis = 2000)) with fadeOut(tween(durationMillis = 2000))
            }, label = ""
        )
        { shouldLaunchFirstScreen ->
            if (shouldLaunchFirstScreen) {
                CompanyScreenAnimation{ isBrainAnimationLaunched = !isBrainAnimationLaunched }
            } else {
                BrainScreenAnimation(delayMilsLoading, onStartMainMenuClick)
            }
        }
    }

}
//endregion



/**
 *  CompanyScreenAnimation. Первая анимация: "КСЮ"
*/
//region CompanyScreenAnimation
@Composable
fun CompanyScreenAnimation(onStartBrainAnimation: () -> Unit) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    //region Animation Letter
    // Letter K
    var isFirstTranslateLetterK by remember { mutableStateOf(true) }
    val firstTranslateCoordinateLetterK = LetterAnimationFirst(
        isFirstTranslateLetterK, 500, 4000)

    var isSecondTranslateLetterK by remember { mutableStateOf(true) }
    val secondTranslateCoordinatesLetterK = LetterAnimationSecond(
        isSecondTranslateLetterK, 500, 4000)


    // Letter S
    var isFirstTranslateLetterS by remember { mutableStateOf(true) }
    val firstTranslateCoordinateLetterS = LetterAnimationFirst(
        isFirstTranslateLetterS, 500, 4000)

    var isSecondTranslateLetterS by remember { mutableStateOf(true) }
    val secondTranslateCoordinateLetterS = LetterAnimationSecond(
        isSecondTranslateLetterS, 500, 4000)


    // Letter U
    var isFirstTranslateLetterU by remember { mutableStateOf(true) }
    val firstTranslateCoordinateLetterU = LetterAnimationFirst(
        isFirstTranslateLetterU, 500, 4000)

    var isSecondTranslateLetterU by remember { mutableStateOf(true) }
    val secondTranslateCoordinateLetterU = LetterAnimationSecond(
        isSecondTranslateLetterU, 500, 4000)
    //endregion
    //region Animation GAMES
    var isIncreasedGames by remember { mutableStateOf(true) }
    val sizeAnimationGames by animateIntAsState(
        targetValue = if (isIncreasedGames) 0 else 32,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    var infiniteGames by remember { mutableStateOf(true) }
    val alphaAnimationGames by animateFloatAsState(
        targetValue = if (infiniteGames) 1f else 0f,
        animationSpec = tween(durationMillis = 4000),
        label = ""
    )
    //endregion
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier
            .padding(top = 50.dp)
            .weight(4f))
        Box(modifier = Modifier.weight(1f)) {
            LetterU(
                firstTranslateCoordinatesAnimation = firstTranslateCoordinateLetterU.value,
                secondTranslateCoordinateAnimation = secondTranslateCoordinateLetterU.value
            )
            LetterS(
                firstTranslateCoordinatesAnimation = firstTranslateCoordinateLetterS.value,
                secondTranslateCoordinatesAnimation = secondTranslateCoordinateLetterS.value
            )
            LetterK(
                firstTranslateCoordinatesAnimation = firstTranslateCoordinateLetterK.value,
                secondTranslateCoordinateAnimation = secondTranslateCoordinatesLetterK.value
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .alpha(alphaAnimationGames),
            text = "GAMES",
            fontSize = sizeAnimationGames.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Red
        )
        Spacer(modifier = Modifier
            .padding(top = 50.dp)
            .weight(3f))
    }
    //endregion ################################################################################## */


    // ############ Временная карта анимации
    LaunchedEffect(Unit) {
        isFirstTranslateLetterU = !isFirstTranslateLetterU
        delay(500)
        isFirstTranslateLetterS = !isFirstTranslateLetterS
        delay(500)
        isFirstTranslateLetterK = !isFirstTranslateLetterK
        delay(2000)
        isIncreasedGames = !isIncreasedGames
        delay(2000)
        infiniteGames = !infiniteGames
        isSecondTranslateLetterU = !isSecondTranslateLetterU
        delay(500)
        isSecondTranslateLetterS = !isSecondTranslateLetterS
        delay(500)
        isSecondTranslateLetterK = !isSecondTranslateLetterK
        delay(2500)
        onStartBrainAnimation()
    }
    // ########################

}
//endregion



/**
 *  BrainScreenAnimation. Вторая анимация: Мозг/название/пульсация/вход
 */
//region BrainScreenAnimation
@Composable
fun BrainScreenAnimation(delayMilsLoading: Long, onStartMainMenuClick: () -> Unit) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    var isBrainAnimationEnd by remember { mutableStateOf(false) }
    var loadingHasStarted by remember { mutableStateOf(false) }
    val scope = CoroutineScope(Dispatchers.Default)
    val context = rememberUpdatedState(LocalContext.current)

    //region Brain animation
    // RedPart
    var isIncreasedRedPart by remember { mutableStateOf(true) }
    val sizeAnimationRedPart = BrainPathAnimationFloat(isIncreasedRedPart, 0f, 1.3f)
    val infiniteSizeAnimationRedPart = BrainPathAnimationCyclicFloat(0f, 0.3f, 200, 800)

    // YellowPart
    var isIncreasedYellowPart by remember { mutableStateOf(true) }
    val sizeAnimationYellowPart = BrainPathAnimationFloat(isIncreasedYellowPart, 0f, 1.3f)
    val infiniteSizeAnimationYellowPart = BrainPathAnimationCyclicFloat(0f, 0.3f, 250, 750)

    // BeigePart
    var isIncreasedBeigePart by remember { mutableStateOf(true) }
    val sizeAnimationBeigePart = BrainPathAnimationFloat(isIncreasedBeigePart, 0f, 1.3f)
    val infiniteSizeAnimationBeigePart = BrainPathAnimationCyclicFloat(0f, 0.2f, 250, 750)

    // BluePart
    var isIncreasedBluePart by remember { mutableStateOf(true) }
    val sizeAnimationBluePart = BrainPathAnimationFloat(isIncreasedBluePart, 0f, 1.3f)
    val infiniteSizeAnimationBluePart = BrainPathAnimationCyclicFloat(0f, 0.3f, 300, 700)

    // OrangePart
    var isIncreasedOrangePart by remember { mutableStateOf(true) }
    val sizeAnimationOrangePart = BrainPathAnimationFloat(isIncreasedOrangePart, 0f, 1.3f)
    val infiniteSizeAnimationOrangePart = BrainPathAnimationCyclicFloat(0f, 0.3f, 350, 650)

    // WhitePart
    var isIncreasedWhitePart by remember { mutableStateOf(true) }
    val sizeAnimationWhitePart = BrainPathAnimationFloat(isIncreasedWhitePart, 0f, 1.3f)
    val infiniteSizeAnimationWhitePart = BrainPathAnimationCyclicFloat(0f, 0.3f, 400, 600)

    //endregion
    //region Circle animation scale
    val sizeAnimationSmallCircle = BrainPathAnimationCyclicFloat(0f, 1.6f, 4000, 0)
    val sizeAnimationMediumCircle = BrainPathAnimationCyclicFloat(0f, 1.9f, 4000, 0)
    val sizeAnimationBigCircle = BrainPathAnimationCyclicFloat(0f, 2.2f, 4000, 0)
    //endregion
    //region Circle animation alpha
    val alphaAnimationSmallCircle = BrainPathAnimationCyclicFloat(0.8f, 0f, 4000, 0)
    val alphaAnimationMediumCircle = BrainPathAnimationCyclicFloat(0.7f, 0f, 4000, 0)
    val alphaAnimationBigCircle = BrainPathAnimationCyclicFloat(0.6f, 0f, 4000, 0)
    //endregion
    //region BrainStorm animation
    var brainStormAnimationEnd by remember { mutableStateOf(false) }
    val alphaAnimationTapBrainStorm = BrainPathAnimationInt(brainStormAnimationEnd, 0, 255, 2000)
    var brainTextAnim by remember { mutableStateOf(false) }
    val sizeAnimationBrainText = BrainPathAnimationFloat(brainTextAnim, 1f, 0f, dampingRatio = Spring.DampingRatioNoBouncy)
    var stormTextAnim by remember { mutableStateOf(false) }
    val sizeAnimationStormText = BrainPathAnimationFloat(stormTextAnim, 1f, 0f, dampingRatio = Spring.DampingRatioNoBouncy)
    //endregion
    //region Tap to Screen animation
    var infiniteTap by remember { mutableStateOf(false) }
    val alphaAnimationTap = BrainPathAnimationInt(infiniteTap, 255, 0, 3000)
    val alphaAnimationTap2 = BrainPathAnimationInt(loadingHasStarted, 255, 0, 3000)
    //endregion
    //region Loading + Dot animation
    var loadingTextAnim by remember { mutableStateOf(false) }
    val sizeAnimationLoadingText = BrainPathAnimationFloat(loadingTextAnim, 1f, 0f, dampingRatio = Spring.DampingRatioNoBouncy)

    val infiniteAlphaAnimationDot1 = BrainPathAnimationCyclicFloat(255f, 0f, 400, 200)
    val infiniteAlphaAnimationDot2 = BrainPathAnimationCyclicFloat(255f, 0f, 300, 200)
    val infiniteAlphaAnimationDot3 = BrainPathAnimationCyclicFloat(255f, 0f, 200, 200)
    //endregion
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                enabled = isBrainAnimationEnd,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (isBrainAnimationEnd) {
                    scope.launch {
                        MusicPlayer(context = context.value).playChoiceClick()
                        isBrainAnimationEnd = !isBrainAnimationEnd // Для блокировки кнопки
                        delay(500)
                        loadingHasStarted = !loadingHasStarted
                        loadingTextAnim = !loadingTextAnim
                        delay(delayMilsLoading)
                        onStartMainMenuClick()
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier) {
            val correctionInCalculationBrain = 7
            val correctionInCalculationCircle = 0

            BigCircle(animationScale = sizeAnimationBigCircle.value, alphaAnimationSmallCircle.value, correctionInCalculationCircle)
            MediumCircle(animationScale = sizeAnimationMediumCircle.value, alphaAnimationMediumCircle.value, correctionInCalculationCircle)
            SmallCircle(animationScale = sizeAnimationSmallCircle.value, alphaAnimationBigCircle.value, correctionInCalculationCircle)

            YellowPart(sizeAnimationYellowPart.value, correctionInCalculationBrain, infiniteSizeAnimationYellowPart.value, loadingHasStarted)
            RedPart(sizeAnimationRedPart.value, correctionInCalculationBrain, infiniteSizeAnimationRedPart.value, loadingHasStarted)
            WhitePart(sizeAnimationWhitePart.value, correctionInCalculationBrain, infiniteSizeAnimationWhitePart.value, loadingHasStarted)
            BeigePart(sizeAnimationBeigePart.value, correctionInCalculationBrain, infiniteSizeAnimationBeigePart.value, loadingHasStarted)
            BluePart(sizeAnimationBluePart.value, correctionInCalculationBrain, infiniteSizeAnimationBluePart.value, loadingHasStarted)
            OrangePart(sizeAnimationOrangePart.value, correctionInCalculationBrain, infiniteSizeAnimationOrangePart.value, loadingHasStarted)

            BrainText(alphaAnimationTapBrainStorm.value, sizeAnimationBrainText.value,)
            StormText(alphaAnimationTapBrainStorm.value, sizeAnimationStormText.value,)
            TapToScreen(alphaAnimationTap.value, alphaAnimationTap2.value)

            Loading(sizeAnimationLoadingText.value)
            Dot1(sizeAnimationLoadingText.value, infiniteAlphaAnimationDot1.value.toInt(), loadingHasStarted)
            Dot2(sizeAnimationLoadingText.value, infiniteAlphaAnimationDot2.value.toInt(), loadingHasStarted)
            Dot3(sizeAnimationLoadingText.value, infiniteAlphaAnimationDot3.value.toInt(), loadingHasStarted)
        }
    }
    //endregion ################################################################################## */

    // ############ Временная карта анимации
    LaunchedEffect(Unit) {
        delay(2000)
        isIncreasedYellowPart = !isIncreasedYellowPart
        delay(500)
        isIncreasedRedPart = !isIncreasedRedPart
        delay(450)
        isIncreasedBeigePart = !isIncreasedBeigePart
        delay(300)
        isIncreasedBluePart = !isIncreasedBluePart
        delay(300)
        isIncreasedOrangePart = !isIncreasedOrangePart
        delay(300)
        isIncreasedWhitePart = !isIncreasedWhitePart
        delay(1000)
        brainTextAnim = !brainTextAnim
        delay(750)
        stormTextAnim = !stormTextAnim
        delay(2000)
        brainStormAnimationEnd = !brainStormAnimationEnd
        delay(700)
        infiniteTap = !infiniteTap
        delay(1000)
        isBrainAnimationEnd = !isBrainAnimationEnd
    }
    // ########################

}
//endregion




/* ################ 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ################# */

// Цвета для KSU (сломался, сука, мой градиент)
val colorsPackOne = listOf(Color(0xff0c056d), Color(0xff590d82), Color(0xffb61aae))
val colorsPackTwo = listOf( Color(0xffe61c5d), Color(0xff930077), Color(0xff3a0088))

// ######################## Отрисовка элементов анимации
// --------------------- BRAIN
//region YellowPart
@Composable
fun YellowPart(animationScale: Float, correctionInCalculations: Int, animationScale2: Float, loadingHasStarted: Boolean) {
    Canvas(
        modifier = Modifier
    ) {
        // Для красивого отображения нужно было чуток подвинуть. Не стал делать новую анимацию, а привязал к имеющийся.
        val firstPositionX = center.x - correctionInCalculations.dp.toPx() + if(loadingHasStarted) (animationScale2 * 15).dp.toPx() else 0.dp.toPx()
        val firstPositionY = center.y - if(loadingHasStarted) (animationScale2 * 15).dp.toPx() else 0.dp.toPx()
        scale(scale = animationScale + if(loadingHasStarted) animationScale2 else 0f) {
            drawPath(
                path = Path().apply {
                    moveTo(firstPositionX, firstPositionY)
                    lineTo(firstPositionX, firstPositionY - 25.dp.toPx())
                    lineTo(firstPositionX + 16.dp.toPx(), firstPositionY - 40.dp.toPx())
                    lineTo(firstPositionX + 24.dp.toPx(), firstPositionY - 60.dp.toPx())
                    lineTo(firstPositionX + 45.dp.toPx(), firstPositionY - 55.dp.toPx())
                    lineTo(firstPositionX + 55.dp.toPx(), firstPositionY - 48.dp.toPx())
                    lineTo(firstPositionX + 75.dp.toPx(), firstPositionY - 28.dp.toPx())
                    lineTo(firstPositionX + 75.dp.toPx(), firstPositionY - 22.dp.toPx())
                    lineTo(firstPositionX + 52.dp.toPx(), firstPositionY - 2.dp.toPx())
                    lineTo(firstPositionX + 27.dp.toPx(), firstPositionY - 17.dp.toPx())
                    lineTo(firstPositionX, firstPositionY)
                },
                color = WelcomeScreenYellow,
                style = Fill,
            )
        }
    }
}

//endregion
//region RedPart
@Composable
fun RedPart(animationScale: Float, correctionInCalculations: Int, animationScale2: Float, loadingHasStarted: Boolean) {
    Canvas(
        modifier = Modifier
    ) {
        // Для красивого отображения нужно было чуток подвинуть. Не стал делать новую анимацию, а привязал к имеющийся.
        val firstPositionX = center.x - correctionInCalculations.dp.toPx() - if(loadingHasStarted) (animationScale2 * 15).dp.toPx() else 0.dp.toPx()
        val firstPositionY = center.y
        scale(scale = animationScale + if(loadingHasStarted) animationScale2 else 0f) {
            drawPath(
                path = Path().apply {
                    moveTo(firstPositionX, firstPositionY)
                    lineTo(firstPositionX, firstPositionY - 25.dp.toPx())
                    lineTo(firstPositionX + 16.dp.toPx(), firstPositionY - 40.dp.toPx())
                    lineTo(firstPositionX + 24.dp.toPx(), firstPositionY - 60.dp.toPx())
                    lineTo(firstPositionX - 24.dp.toPx(), firstPositionY - 57.dp.toPx())
                    lineTo(firstPositionX - 49.dp.toPx(), firstPositionY - 45.dp.toPx())
                    lineTo(firstPositionX - 73.dp.toPx(), firstPositionY - 20.dp.toPx())
                    lineTo(firstPositionX - 71.dp.toPx(), firstPositionY + 8.dp.toPx())
                    lineTo(firstPositionX - 47.dp.toPx(), firstPositionY + 24.dp.toPx())
                    lineTo(firstPositionX - 32.dp.toPx(), firstPositionY + 24.dp.toPx())
                    lineTo(firstPositionX - 25.dp.toPx(), firstPositionY + 20.dp.toPx())
                    lineTo(firstPositionX, firstPositionY)
                },
                color = WelcomeScreenRed,
                style = Fill,
            )
        }
    }
}

//endregion
//region BeigePart
@Composable
fun BeigePart(animationScale: Float, correctionInCalculations: Int, animationScale2: Float, loadingHasStarted: Boolean) {
    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX = center.x - correctionInCalculations.dp.toPx()
        val firstPositionY = center.y
        scale(scale = animationScale + if(loadingHasStarted) animationScale2 else 0f) {
            drawPath(
                path = Path().apply {
                    moveTo(firstPositionX, firstPositionY)
                    lineTo(firstPositionX - 25.dp.toPx(), firstPositionY + 20.dp.toPx())
                    lineTo(firstPositionX - 25.dp.toPx(), firstPositionY + 35.dp.toPx())
                    lineTo(firstPositionX, firstPositionY + 46.dp.toPx())
                    lineTo(firstPositionX + 58.dp.toPx(), firstPositionY + 24.dp.toPx())
                    lineTo(firstPositionX + 59.dp.toPx(), firstPositionY + 13.dp.toPx())
                    lineTo(firstPositionX + 52.dp.toPx(), firstPositionY - 2.dp.toPx())
                    lineTo(firstPositionX + 27.dp.toPx(), firstPositionY - 17.dp.toPx())
                    lineTo(firstPositionX, firstPositionY)
                },
                color = WelcomeScreenBeige,
                style = Fill,
            )
        }
    }
}

//endregion
//region BluePart
@Composable
fun BluePart(animationScale: Float, correctionInCalculations: Int, animationScale2: Float, loadingHasStarted: Boolean) {
    Canvas(
        modifier = Modifier
    ) {
        val secondPositionX = center.x + 58.dp.toPx() - correctionInCalculations.dp.toPx()
        val secondPositionY = center.y + 24.dp.toPx()
        scale(scale = animationScale + if(loadingHasStarted) animationScale2 else 0f) {
            drawPath(
                path = Path().apply {

                    moveTo(secondPositionX, secondPositionY)
                    lineTo(secondPositionX + 10.dp.toPx(), secondPositionY + 4.dp.toPx())
                    lineTo(secondPositionX + 23.dp.toPx(), secondPositionY + 5.dp.toPx())
                    lineTo(secondPositionX + 28.dp.toPx(), secondPositionY - 12.dp.toPx())
                    lineTo(secondPositionX + 23.dp.toPx(), secondPositionY - 26.dp.toPx())
                    lineTo(secondPositionX + 25.dp.toPx(), secondPositionY - 39.dp.toPx())
                    lineTo(secondPositionX + 17.dp.toPx(), secondPositionY - 46.dp.toPx())
                    lineTo(secondPositionX - 6.dp.toPx(), secondPositionY - 26.dp.toPx())
                    lineTo(secondPositionX + 1.dp.toPx(), secondPositionY - 11.dp.toPx())
                    lineTo(secondPositionX, secondPositionY)

                },
                color = WelcomeScreenBlue,
                style = Fill,
            )
        }
    }
}

//endregion
//region OrangePart
@Composable
fun OrangePart(animationScale: Float, correctionInCalculations: Int, animationScale2: Float, loadingHasStarted: Boolean) {
    Canvas(
        modifier = Modifier
    ) {
        val secondPositionX = center.x + 58.dp.toPx() - correctionInCalculations.dp.toPx()
        val secondPositionY = center.y + 24.dp.toPx()
        scale(scale = animationScale + if(loadingHasStarted) animationScale2 else 0f) {
            drawPath(
                path = Path().apply {

                    moveTo(secondPositionX, secondPositionY)
                    lineTo(secondPositionX + 10.dp.toPx(), secondPositionY + 4.dp.toPx())
                    lineTo(secondPositionX + 11.dp.toPx(), secondPositionY + 22.dp.toPx())
                    lineTo(secondPositionX - 14.dp.toPx(), secondPositionY + 37.dp.toPx())
                    lineTo(secondPositionX - 36.dp.toPx(), secondPositionY + 25.dp.toPx())
                    lineTo(secondPositionX - 37.dp.toPx(), secondPositionY + 14.dp.toPx())
                    lineTo(secondPositionX, secondPositionY)
                },
                color = WelcomeScreenOrange,
                style = Fill,
            )
        }
    }
}

//endregion
//region WhitePart
@Composable
fun WhitePart(animationScale: Float, correctionInCalculations: Int, animationScale2: Float, loadingHasStarted: Boolean) {
    Canvas(
        modifier = Modifier
    ) {
        // Для красивого отображения нужно было чуток подвинуть. Не стал делать новую анимацию, а привязал к имеющийся.
        val thirdPositionX = center.x + 7.dp.toPx() - correctionInCalculations.dp.toPx() - if(loadingHasStarted) (animationScale2 * 10).dp.toPx() else 0.dp.toPx()
        val thirdPositionY = center.y + 44.dp.toPx() + if(loadingHasStarted) (animationScale2 * 10).dp.toPx() else 0.dp.toPx()
        scale(scale = animationScale + if(loadingHasStarted) animationScale2 else 0f) {
            drawPath(
                path = Path().apply {
                    moveTo(thirdPositionX, thirdPositionY)
                    lineTo(thirdPositionX + 15.dp.toPx(), thirdPositionY + 10.dp.toPx())
                    lineTo(thirdPositionX + 20.dp.toPx(), thirdPositionY + 25.dp.toPx())
                    lineTo(thirdPositionX + 29.dp.toPx(), thirdPositionY + 24.dp.toPx())
                    lineTo(thirdPositionX + 29.dp.toPx(), thirdPositionY + 12.dp.toPx())
                    lineTo(thirdPositionX + 15.dp.toPx(), thirdPositionY + 5.dp.toPx())
                    lineTo(thirdPositionX + 14.dp.toPx(), thirdPositionY - 7.dp.toPx())
                    lineTo(thirdPositionX, thirdPositionY)
                },
                color = WelcomeScreenLightOrange,
                style = Fill,
            )
        }
    }
}
//endregion

// --------------------- CIRCLES
//region SmallCircle
@Composable
fun SmallCircle(animationScale: Float, animationAlpha: Float, correctionInCalculations: Int = 0) {
    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX = center.x - correctionInCalculations.dp.toPx()
        val firstPositionY = center.y
        scale(scale = animationScale) {
            drawCircle(
                center = Offset(firstPositionX, firstPositionY),
                color = WelcomeScreenOrange,
                alpha = animationAlpha,
                radius = 100.dp.toPx(),
                style = Fill
            )
        }
    }
}

//endregion
//region MediumCircle
@Composable
fun MediumCircle(animationScale: Float, animationAlpha: Float, correctionInCalculations: Int = 0) {
    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX = center.x - correctionInCalculations.dp.toPx()
        val firstPositionY = center.y
        scale(scale = animationScale) {
            drawCircle(
                center = Offset(firstPositionX, firstPositionY),
                color = WelcomeScreenLightOrange,
                alpha = animationAlpha,
                radius = 100.dp.toPx(),
                style = Fill
            )
        }
    }
}

//endregion
//region BigCircle
@Composable
fun BigCircle(animationScale: Float, animationAlpha: Float, correctionInCalculations: Int = 0) {
    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX = center.x - correctionInCalculations.dp.toPx()
        val firstPositionY = center.y
        scale(scale = animationScale) {
            drawCircle(
                center = Offset(firstPositionX, firstPositionY),
                color = WelcomeScreenYellow,
                alpha = animationAlpha,
                radius = 100.dp.toPx(),
                style = Fill
            )
        }
    }
}
//endregion

// --------------------- Text
//region TapToScreen
@Composable
fun TapToScreen(alphaAnimation: Int, alphaAnimation2: Int) {

    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX = center.x
        val firstPositionY = center.y + 130.dp.toPx()

        val paint = Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 24.sp.toPx()
            color = Color(0xFF959DCE).toArgb()
            alpha = alphaAnimation - alphaAnimation2
        }

        drawContext.canvas.nativeCanvas.drawText(
            "Tap   to   screen",
            firstPositionX,
            firstPositionY,
            paint
        )
    }
}
//endregion
//region BrainStorm
@Composable
fun BrainText(alphaAnimation: Int, animationScale: Float) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = animationScale) {
            val firstPositionX = center.x - 40.dp.toPx()
            val firstPositionY = center.y + 130.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFF209E99).toArgb()
                alpha = alphaAnimation
            }

            drawContext.canvas.nativeCanvas.drawText(
                "Brain",
                firstPositionX,
                firstPositionY,
                paint
            )
        }
    }
}

@Composable
fun StormText(alphaAnimation: Int, animationScale: Float) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = animationScale) {
            val firstPositionX = center.x + 40.dp.toPx()
            val firstPositionY = center.y + 130.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = PinkAppColor.toArgb()
                alpha = alphaAnimation
            }

            drawContext.canvas.nativeCanvas.drawText(
                "Storm",
                firstPositionX,
                firstPositionY,
                paint
            )
        }
    }
}
//endregion
//region Loading
@Composable
fun Loading(animationScale: Float) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = animationScale) {
            val firstPositionX = center.x
            val firstPositionY = center.y + 140.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFF209E99).toArgb()
            }

            drawContext.canvas.nativeCanvas.drawText(
                "Loading",
                firstPositionX,
                firstPositionY,
                paint
            )
        }
    }
}

@Composable
fun Dot1(animationScale: Float, alphaAnimation: Int, loadingHasStarted: Boolean) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = animationScale) {
            val firstPositionX = center.x + 60.dp.toPx()
            val firstPositionY = center.y + 140.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFF209E99).toArgb()
                alpha = if(loadingHasStarted) alphaAnimation else 0
            }

            drawContext.canvas.nativeCanvas.drawText(
                ".",
                firstPositionX,
                firstPositionY,
                paint
            )
        }
    }
}

@Composable
fun Dot2(animationScale: Float, alphaAnimation: Int, loadingHasStarted: Boolean) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = animationScale) {
            val firstPositionX = center.x + 65.dp.toPx()
            val firstPositionY = center.y + 140.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFF209E99).toArgb()
                alpha = if(loadingHasStarted) alphaAnimation else 0
            }

            drawContext.canvas.nativeCanvas.drawText(
                ".",
                firstPositionX,
                firstPositionY,
                paint
            )
        }
    }
}

@Composable
fun Dot3(animationScale: Float, alphaAnimation: Int, loadingHasStarted: Boolean) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = animationScale) {
            val firstPositionX = center.x + 70.dp.toPx()
            val firstPositionY = center.y + 140.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFF209E99).toArgb()
                alpha = if(loadingHasStarted) alphaAnimation else 0
            }

            drawContext.canvas.nativeCanvas.drawText(
                ".",
                firstPositionX,
                firstPositionY,
                paint
            )
        }
    }
}
//endregion

// --------------------- KSU (Company name)
//region LetterK
@Composable
fun LetterK(firstTranslateCoordinatesAnimation: Dp, secondTranslateCoordinateAnimation: Dp) {

    val animatedFraction by rememberInfiniteTransition("").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, delayMillis = 2500),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val gradientColor = lerp(
        lerp(colorsPackOne[1], colorsPackTwo[1], animatedFraction),
        lerp(colorsPackOne[0], colorsPackTwo[0], animatedFraction),
        animatedFraction
    )
    


    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX =
            (center.x - 30.dp.toPx()) - firstTranslateCoordinatesAnimation.toPx() + secondTranslateCoordinateAnimation.toPx()
        val firstPositionY = center.y
        scale(1.05f) {
            drawPath(
                path = Path().apply {
                    moveTo(firstPositionX - 127.5.dp.toPx(), firstPositionY + 75.dp.toPx())
                    lineTo(firstPositionX - 127.5.dp.toPx(), firstPositionY - 75.dp.toPx())
                    lineTo(firstPositionX - 100.dp.toPx(), firstPositionY - 45.dp.toPx())
                    lineTo(firstPositionX - 100.dp.toPx(), firstPositionY - 5.dp.toPx())
                    lineTo(firstPositionX - 52.dp.toPx(), firstPositionY - 55.dp.toPx())
                    lineTo(firstPositionX - 52.dp.toPx(), firstPositionY - 30.dp.toPx())
                    lineTo(firstPositionX - 100.dp.toPx(), firstPositionY + 15.dp.toPx())
                    lineTo(firstPositionX - 52.dp.toPx(), firstPositionY + 55.dp.toPx())
                    lineTo(firstPositionX - 52.dp.toPx(), firstPositionY + 75.dp.toPx())
                    lineTo(firstPositionX - 100.dp.toPx(), firstPositionY + 40.dp.toPx())
                    lineTo(firstPositionX - 100.dp.toPx(), firstPositionY + 55.dp.toPx())
                    lineTo(firstPositionX - 127.5.dp.toPx(), firstPositionY + 75.dp.toPx())
                },
                style = Fill,
                brush = SolidColor(gradientColor)
            )
        }

    }
}

//endregion
//region LetterS
@Composable
fun LetterS(firstTranslateCoordinatesAnimation: Dp, secondTranslateCoordinatesAnimation: Dp) {

    val animatedFraction by rememberInfiniteTransition("").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, delayMillis = 1500),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val gradientColor = lerp(
        lerp(colorsPackOne[1], colorsPackTwo[1], animatedFraction),
        lerp(colorsPackOne[0], colorsPackTwo[0], animatedFraction),
        animatedFraction
    )

    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX =
            center.x - 10.dp.toPx() - firstTranslateCoordinatesAnimation.toPx() + secondTranslateCoordinatesAnimation.toPx()
        val firstPositionY = center.y

        drawCircle(
            center = Offset(firstPositionX, firstPositionY - 25.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 50.dp.toPx(),
            style = Fill
        )

        drawCircle(
            center = Offset(firstPositionX, firstPositionY + 25.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 50.dp.toPx(),
            style = Fill
        )


        drawCircle(
            center = Offset(firstPositionX, firstPositionY + 35.dp.toPx()),
            color = WhiteAppBackground,
            radius = 15.dp.toPx(),
            style = Fill
        )

        drawCircle(
            center = Offset(firstPositionX, firstPositionY - 35.dp.toPx()),
            color = WhiteAppBackground,
            radius = 15.dp.toPx(),
            style = Fill
        )

        drawPath(
            path = Path().apply {
                moveTo(firstPositionX, firstPositionY - 35.dp.toPx())
                lineTo(firstPositionX + 60.dp.toPx(), firstPositionY + 25.dp.toPx())

                moveTo(firstPositionX + 11.dp.toPx(), firstPositionY - 45.dp.toPx())
                lineTo(firstPositionX + 50.dp.toPx(), firstPositionY - 5.dp.toPx())


                lineTo(firstPositionX + 50.dp.toPx(), firstPositionY + 35.dp.toPx())
                lineTo(firstPositionX - 11.dp.toPx(), firstPositionY - 24.dp.toPx())
                lineTo(firstPositionX + 10.dp.toPx(), firstPositionY - 45.dp.toPx())
            },
            color = WhiteAppBackground,
            style = Fill,
        )

        drawPath(
            path = Path().apply {

                moveTo(firstPositionX - 11.dp.toPx(), firstPositionY + 45.dp.toPx())
                lineTo(firstPositionX - 50.dp.toPx(), firstPositionY + 5.dp.toPx())

                lineTo(firstPositionX - 50.dp.toPx(), firstPositionY - 35.dp.toPx())
                lineTo(firstPositionX + 11.dp.toPx(), firstPositionY + 24.dp.toPx())
                lineTo(firstPositionX - 10.dp.toPx(), firstPositionY + 45.dp.toPx())
            },
            color = WhiteAppBackground,
            style = Fill,
        )
    }
}

//endregion
//region LetterU
@Composable
fun LetterU(firstTranslateCoordinatesAnimation: Dp, secondTranslateCoordinateAnimation: Dp) {

    val animatedFraction by rememberInfiniteTransition("").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, delayMillis = 500),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val gradientColor = lerp(
        lerp(colorsPackOne[1], colorsPackTwo[1], animatedFraction),
        lerp(colorsPackOne[0], colorsPackTwo[0], animatedFraction),
        animatedFraction
    )


    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX =
            center.x - 10.dp.toPx() - firstTranslateCoordinatesAnimation.toPx() + secondTranslateCoordinateAnimation.toPx()
        val firstPositionY = center.y


        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY + 25.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 50.dp.toPx(),
            style = Fill
        )


        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY + 5.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 50.dp.toPx(),
            style = Fill
        )


        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY - 15.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 50.dp.toPx(),
            style = Fill
        )

        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY - 25.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 50.dp.toPx(),
            style = Fill
        )

        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY - 25.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 40.dp.toPx(),
            style = Fill
        )

        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY - 25.dp.toPx()),
            brush = SolidColor(gradientColor),
            radius = 30.dp.toPx(),
            style = Fill
        )


        drawCircle(
            center = Offset(firstPositionX + 127.5.dp.toPx(), firstPositionY + 25.dp.toPx()),
            color = WhiteAppBackground,
            radius = 20.dp.toPx(),
            style = Fill
        )

        drawPath(
            path = Path().apply {
                moveTo(firstPositionX + 127.5.dp.toPx(), firstPositionY + 25.dp.toPx())
                lineTo(firstPositionX + 127.5.dp.toPx(), firstPositionY - 75.dp.toPx())
            },
            color = WhiteAppBackground,
            style = Stroke(width = 40.dp.toPx()),
        )
    }
}
//endregion



// ######################## Анимация
//region CompanyScreenAnimation --> LetterAnimation
@Composable
private fun LetterAnimationFirst(targetState: Boolean, delayMillis: Int, durationMillis: Int): State<Dp> {
    return animateDpAsState(
        targetValue = if (targetState) 500.dp else 0.dp,
        animationSpec = tween(delayMillis = delayMillis, durationMillis = durationMillis),
        label = ""
    )
}

@Composable
private fun LetterAnimationSecond(targetState: Boolean, delayMillis: Int, durationMillis: Int): State<Dp> {
    return animateDpAsState(
        targetValue = if (targetState) 0.dp else 500.dp,
        animationSpec = tween(delayMillis = delayMillis, durationMillis = durationMillis),
        label = ""
    )
}
//endregion
//region BrainPathAnimation
@Composable
private fun BrainPathAnimationFloat(
    targetState: Boolean, initialValue: Float, targetValue: Float, dampingRatio: Float = Spring.DampingRatioHighBouncy
): State<Float> {
    return animateFloatAsState(
        targetValue = if (targetState) initialValue else targetValue,
        animationSpec = spring(dampingRatio = dampingRatio, stiffness = Spring.StiffnessLow),
        label = ""
    )
}


@Composable
private fun BrainPathAnimationCyclicFloat(
    initialValue: Float, targetValue: Float, durationMillis: Int, delayMillis: Int
): State<Float> {
    return rememberInfiniteTransition(label = "").animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, delayMillis = delayMillis),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
}


@Composable
private fun BrainPathAnimationInt(
    targetState: Boolean, initialValue: Int, targetValue: Int, durationMillis: Int
): State<Int> {
    return animateIntAsState(
        targetValue = if (targetState) initialValue else targetValue,
        animationSpec = tween(durationMillis = durationMillis),
        label = ""
    )
}
//endregion

/* ########################################################################################## */








