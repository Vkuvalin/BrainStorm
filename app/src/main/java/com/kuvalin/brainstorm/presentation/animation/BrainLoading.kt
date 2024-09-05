package com.kuvalin.brainstorm.presentation.animation

import android.graphics.Paint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BrainLoading() {

    //region Brain animation
    //region RedPart
    val sizeAnimationRedPart by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    val infiniteRedPartScale = rememberInfiniteTransition(label = "")
    val infiniteSizeAnimationRedPart by infiniteRedPartScale.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 120, delayMillis = 480),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion
    //region YellowPart
    val sizeAnimationYellowPart by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    val infiniteYellowPartScale = rememberInfiniteTransition(label = "")
    val infiniteSizeAnimationYellowPart by infiniteYellowPartScale.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 150, delayMillis = 450),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion
    //region BeigePart
    val sizeAnimationBeigePart by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    val infiniteBeigePartScale = rememberInfiniteTransition(label = "")
    val infiniteSizeAnimationBeigePart by infiniteBeigePartScale.animateFloat(
        initialValue = 0f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 150, delayMillis = 450),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion
    //region BluePart
    val sizeAnimationBluePart by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    val infiniteBluePartScale = rememberInfiniteTransition(label = "")
    val infiniteSizeAnimationBluePart by infiniteBluePartScale.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 180, delayMillis = 420),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion
    //region OrangePart
    val sizeAnimationOrangePart by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    val infiniteOrangePartScale = rememberInfiniteTransition(label = "")
    val infiniteSizeAnimationOrangePart by infiniteOrangePartScale.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 210, delayMillis = 390),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion
    //region WhitePart
    val sizeAnimationWhitePart by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    val infiniteWhitePartScale = rememberInfiniteTransition(label = "")
    val infiniteSizeAnimationWhitePart by infiniteWhitePartScale.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 240, delayMillis = 360),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion
    //endregion

    //region Loading + Dot animation
    val infiniteAlphaDot1 = rememberInfiniteTransition(label = "")
    val infiniteAlphaAnimationDot1 by infiniteAlphaDot1.animateFloat(
        initialValue = 255f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val infiniteAlphaDot2 = rememberInfiniteTransition(label = "")
    val infiniteAlphaAnimationDot2 by infiniteAlphaDot2.animateFloat(
        initialValue = 255f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 300, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val infiniteAlphaDot3 = rememberInfiniteTransition(label = "")
    val infiniteAlphaAnimationDot3 by infiniteAlphaDot3.animateFloat(
        initialValue = 255f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 200, delayMillis = 200),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //endregion

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
                val correctionInCalculationsBrain = 7

                YellowPartSmall(sizeAnimationYellowPart, correctionInCalculationsBrain, infiniteSizeAnimationYellowPart)
                RedPartSmall(sizeAnimationRedPart, correctionInCalculationsBrain, infiniteSizeAnimationRedPart)
                WhitePartSmall(sizeAnimationWhitePart, correctionInCalculationsBrain, infiniteSizeAnimationWhitePart)
                BeigePartSmall(sizeAnimationBeigePart, correctionInCalculationsBrain, infiniteSizeAnimationBeigePart)
                BluePartSmall(sizeAnimationBluePart, correctionInCalculationsBrain, infiniteSizeAnimationBluePart)
                OrangePartSmall(sizeAnimationOrangePart, correctionInCalculationsBrain, infiniteSizeAnimationOrangePart)

                LoadingSmall()
                Dot1Small(infiniteAlphaAnimationDot1.toInt())
                Dot2Small(infiniteAlphaAnimationDot2.toInt())
                Dot3Small(infiniteAlphaAnimationDot3.toInt())
            }
        }
    }

}




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
