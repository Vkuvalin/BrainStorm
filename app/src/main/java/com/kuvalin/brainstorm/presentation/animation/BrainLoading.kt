package com.kuvalin.brainstorm.presentation.animation

import android.graphics.Paint
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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BrainLoading() {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    //region Brain animation
    // RedPart
    val sizeAnimationRedPart = SmallBrainBounce()
    val infiniteSizeAnimationRedPart = BrainPathAnimationCyclicFloat(0f, 0.3f, 120, 480)

    // YellowPart
    val sizeAnimationYellowPart = SmallBrainBounce()
    val infiniteSizeAnimationYellowPart = BrainPathAnimationCyclicFloat(0f, 0.3f, 150, 450)

    // BeigePart
    val sizeAnimationBeigePart = SmallBrainBounce()
    val infiniteSizeAnimationBeigePart = BrainPathAnimationCyclicFloat(0f, 0.2f, 150, 450)

    // BluePart
    val sizeAnimationBluePart = SmallBrainBounce()
    val infiniteSizeAnimationBluePart = BrainPathAnimationCyclicFloat(0f, 0.3f, 180, 420)

    // OrangePart
    val sizeAnimationOrangePart = SmallBrainBounce()
    val infiniteSizeAnimationOrangePart = BrainPathAnimationCyclicFloat(0f, 0.3f, 210, 390)

    // WhitePart
    val sizeAnimationWhitePart = SmallBrainBounce()
    val infiniteSizeAnimationWhitePart = BrainPathAnimationCyclicFloat(0f, 0.3f, 240, 360)

    //endregion
    //region Loading + Dot animation
    val infiniteAlphaAnimationDot1 = BrainPathAnimationCyclicFloat(255f, 0f, 400, 200)
    val infiniteAlphaAnimationDot2 = BrainPathAnimationCyclicFloat(255f, 0f, 300, 200)
    val infiniteAlphaAnimationDot3 = BrainPathAnimationCyclicFloat(255f, 0f, 200, 200)
    //endregion
    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(10))
                .background(color = Color(0xCC000000))
                .border(width = 1.dp,color = Color(0xE6000000), shape = RoundedCornerShape(10)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column() {
                val correctionInCalculationBrain = 7

                YellowPartSmall(sizeAnimationYellowPart.value, correctionInCalculationBrain, infiniteSizeAnimationYellowPart.value)
                RedPartSmall(sizeAnimationRedPart.value, correctionInCalculationBrain, infiniteSizeAnimationRedPart.value)
                WhitePartSmall(sizeAnimationWhitePart.value, correctionInCalculationBrain, infiniteSizeAnimationWhitePart.value)
                BeigePartSmall(sizeAnimationBeigePart.value, correctionInCalculationBrain, infiniteSizeAnimationBeigePart.value)
                BluePartSmall(sizeAnimationBluePart.value, correctionInCalculationBrain, infiniteSizeAnimationBluePart.value)
                OrangePartSmall(sizeAnimationOrangePart.value, correctionInCalculationBrain, infiniteSizeAnimationOrangePart.value)

                LoadingSmall()
                Dot1Small(infiniteAlphaAnimationDot1.value.toInt())
                Dot2Small(infiniteAlphaAnimationDot2.value.toInt())
                Dot3Small(infiniteAlphaAnimationDot3.value.toInt())
            }
        }
    }
    //endregion ################################################################################## */

}



/* ################ 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ################# */
//region Loading()
@Composable
fun LoadingSmall() {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = 0.7f) {
            val firstPositionX = center.x
            val firstPositionY = center.y + 90.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFFFFFFFF).toArgb()
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
fun Dot1Small(alphaAnimation: Int) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = 0.7f) {
            val firstPositionX = center.x + 60.dp.toPx()
            val firstPositionY = center.y + 90.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFFFFFFFF).toArgb()
                alpha = alphaAnimation
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
fun Dot2Small(alphaAnimation: Int) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = 0.7f) {
            val firstPositionX = center.x + 65.dp.toPx()
            val firstPositionY = center.y + 90.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFFFFFFFF).toArgb()
                alpha = alphaAnimation
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
fun Dot3Small(alphaAnimation: Int) {

    Canvas(
        modifier = Modifier
    ) {
        scale(scale = 0.7f) {
            val firstPositionX = center.x + 70.dp.toPx()
            val firstPositionY = center.y + 90.dp.toPx()

            val paint = Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 32.sp.toPx()
                color = Color(0xFFFFFFFF).toArgb()
                alpha = alphaAnimation
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

// --------------------- BRAIN
//region YellowPart
@Composable
fun YellowPartSmall(animationScale: Float, correctionInCalculations: Int, animationScale2: Float) {
    Canvas(
        modifier = Modifier
    ) {
        // Для красивого отображения нужно было чуток подвинуть. Не стал делать новую анимацию, а привязал к имеющийся.
        val firstPositionX = center.x - correctionInCalculations.dp.toPx() + (animationScale2 * 15).dp.toPx()
        val firstPositionY = center.y - (animationScale2 * 15).dp.toPx() - 20.dp.toPx()
        scale(scale = animationScale + animationScale2) {
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
                color = Color(0xFFFFFFFF),
                style = Fill,
            )
        }
    }
}

//endregion
//region RedPart
@Composable
fun RedPartSmall(animationScale: Float, correctionInCalculations: Int, animationScale2: Float) {
    Canvas(
        modifier = Modifier
    ) {
        // Для красивого отображения нужно было чуток подвинуть. Не стал делать новую анимацию, а привязал к имеющийся.
        val firstPositionX = center.x - correctionInCalculations.dp.toPx() - (animationScale2 * 15).dp.toPx()
        val firstPositionY = center.y - 20.dp.toPx()
        scale(scale = animationScale + animationScale2) {
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
                color = Color(0xFFFFFFFF),
                style = Fill,
            )
        }
    }
}

//endregion
//region BeigePart
@Composable
fun BeigePartSmall(animationScale: Float, correctionInCalculations: Int, animationScale2: Float) {
    Canvas(
        modifier = Modifier
    ) {
        val firstPositionX = center.x - correctionInCalculations.dp.toPx()
        val firstPositionY = center.y - 20.dp.toPx()
        scale(scale = animationScale + animationScale2) {
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
                color = Color(0xFFFFFFFF),
                style = Fill,
            )
        }
    }
}

//endregion
//region BluePart
@Composable
fun BluePartSmall(animationScale: Float, correctionInCalculations: Int, animationScale2: Float) {
    Canvas(
        modifier = Modifier
    ) {
        val secondPositionX = center.x + 58.dp.toPx() - correctionInCalculations.dp.toPx()
        val secondPositionY = center.y + 24.dp.toPx() - 20.dp.toPx()
        scale(scale = animationScale + animationScale2) {
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
                color = Color(0xFFFFFFFF),
                style = Fill,
            )
        }
    }
}

//endregion
//region OrangePart
@Composable
fun OrangePartSmall(animationScale: Float, correctionInCalculations: Int, animationScale2: Float) {
    Canvas(
        modifier = Modifier
    ) {
        val secondPositionX = center.x + 58.dp.toPx() - correctionInCalculations.dp.toPx()
        val secondPositionY = center.y + 24.dp.toPx() - 20.dp.toPx()
        scale(scale = animationScale + animationScale2) {
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
                color = Color(0xFFFFFFFF),
                style = Fill,
            )
        }
    }
}

//endregion
//region WhitePart
@Composable
fun WhitePartSmall(animationScale: Float, correctionInCalculations: Int, animationScale2: Float) {
    Canvas(
        modifier = Modifier
    ) {
        // Для красивого отображения нужно было чуток подвинуть. Не стал делать новую анимацию, а привязал к имеющийся.
        val thirdPositionX = center.x + 7.dp.toPx() - correctionInCalculations.dp.toPx() - (animationScale2 * 10).dp.toPx()
        val thirdPositionY = center.y + 44.dp.toPx() + (animationScale2 * 10).dp.toPx() - 20.dp.toPx()
        scale(scale = animationScale + animationScale2) {
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
                color = Color(0xFFFFFFFF),
                style = Fill,
            )
        }
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
//region SmallBrainBounce
@Composable
private fun SmallBrainBounce(): State<Float> {
    return animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessLow),
        label = ""
    )
}
//endregion
/* ########################################################################################## */








