package com.kuvalin.brainstorm.presentation.screens.mainmenu.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kuvalin.brainstorm.ui.theme.CrosshairColorCyan
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlin.random.Random



// ###################### DrawingChart
//region DrawingChart
@Composable
fun DrawingChart(
    // Пожалуй оставлю это будущему себе, а пока сделаю на отъебись.
    // В общем-то она больше не будет использоваться.
    // Планировалось 3 места: главный экран, поделиться статистикой и экран игры
    workMode: Int
) {

    val modifier: Modifier = Modifier
        .clip(CircleShape)
        .size(240.dp)
        .scale(if (workMode == 1) 1f else 0.8f)


    Box(
        modifier = Modifier
            .padding(bottom = 22.dp, start = 20.dp, end = 20.dp)
            .scale(0.9f)
    ) {

        Circle(modifier)
        CircleBackground(modifier = modifier, workMode = workMode)
        Crosshair(modifier)

        // Цифры немного странные получились, даже не могу объяснить, почему именно такие (глубоко не стал копаться)
        SkillNamePolus(text = "Speed", x = 5f, y = -120f, workMode = workMode)
        SkillNamePolus(text = "Accuracy", x = 10f, y = 135f, workMode = workMode)

        SkillName(text = "Judgement", x = 80f * 1.6f, y = -55f, workMode = workMode)
        SkillName(text = "Calculation", x = 80f * 1.6f, y = 80f, workMode = workMode)
        SkillName(text = "Memory", x = -85f * 1.6f, y = -60f, workMode = workMode)
        SkillName(text = "Observation", x = -100f * 1.6f, y = 75f, workMode = workMode)

        if (workMode == 1){
            Neck()
            Chin(modifier)
        }

        Graph(
            speed = (Random.nextInt(100, 1000) * if (workMode == 1) 0.9 else 0.8).toInt(),
            judgement = (Random.nextInt(100, 1000) * if (workMode == 1) 0.9 else 0.8).toInt(),
            calculation = (Random.nextInt(100, 1000) * if (workMode == 1) 0.9 else 0.8).toInt(),
            accuracy = (Random.nextInt(100, 1000) * if (workMode == 1) 0.9 else 0.8).toInt(),
            observation = (Random.nextInt(100, 1000) * if (workMode == 1) 0.9 else 0.8).toInt(),
            memory = (Random.nextInt(100, 1000) * if (workMode == 1) 0.9 else 0.8).toInt(),
            workMode = 1
        )

        if (workMode == 3){
            Graph(
                speed = (Random.nextInt(100, 1000) * 0.8).toInt(),
                judgement = (Random.nextInt(100, 1000) * 0.8).toInt(),
                calculation = (Random.nextInt(100, 1000) * 0.8).toInt(),
                accuracy = (Random.nextInt(100, 1000) * 0.8).toInt(),
                observation = (Random.nextInt(100, 1000) * 0.8).toInt(),
                memory = (Random.nextInt(100, 1000) * 0.8).toInt(),
                workMode = 3
            )
        }


    }
}

//endregion
//region Circle
@Composable
private fun Circle(modifier: Modifier) {
    Canvas(
        modifier = modifier
            .zIndex(2f)
    ) {

        drawCircle(
            center = Offset(center.x, center.y),
            color = Color.White,
            radius = 120.dp.toPx(),
            style = Stroke(width = 20.dp.toPx())
        )

    }
}
//endregion
//region CircleBackground
@Composable
private fun CircleBackground(modifier: Modifier, workMode: Int) {
    Canvas(
        modifier = modifier
            .zIndex(-1f)
    ) {

        drawCircle(
            center = Offset(center.x, center.y),
            color = if (workMode == 1) Color(0xFFE6E6E6) else Color.White,
            radius = 120.dp.toPx(),
            style = Fill
        )

    }
}

//endregion
//region Crosshair
@Composable
fun Crosshair(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {

        val degreeConstant = 1.6
        val length = 60
        val radius = 120

        drawPath(
            path = Path().apply {

                // 0u
                moveTo(center.x, center.y)
                lineTo(center.x, center.y - radius.dp.toPx())

                // 60u
                moveTo(center.x, center.y)
                lineTo(center.x + (degreeConstant * length).dp.toPx(), center.y - length.dp.toPx())

                // 120u
                moveTo(center.x, center.y)
                lineTo(center.x + (degreeConstant * length).dp.toPx(), center.y + length.dp.toPx())

                // 180u
                moveTo(center.x, center.y)
                lineTo(center.x, center.y + radius.dp.toPx())

                // 240u
                moveTo(center.x, center.y)
                lineTo(center.x - (degreeConstant * length).dp.toPx(), center.y + length.dp.toPx())

                // 300u
                moveTo(center.x, center.y)
                lineTo(center.x - (degreeConstant * length).dp.toPx(), center.y - length.dp.toPx())

            },
            color = CrosshairColorCyan,
            style = Stroke(width = 1.dp.toPx()),
        )
    }
}

//endregion
//region Graph
@Composable
fun Graph(
    speed: Int = 0,
    judgement: Int = 0,
    calculation: Int = 0,
    accuracy: Int = 0,
    observation: Int = 0,
    memory: Int = 0,
    workMode: Int
) {

    val degreeConstant = 1.6

    Canvas(
        modifier = Modifier
            .zIndex(4f)
            .size(240.dp)
    ) {
        drawPath(
            path = Path().apply {

                // 0u
                moveTo(center.x, center.y - calculateLevel(speed, policy = true).dp.toPx()) // Speed
                lineTo(
                    center.x + (degreeConstant * calculateLevel(judgement)).dp.toPx(),
                    center.y - calculateLevel(judgement).dp.toPx()
                ) // Judgement
                lineTo(
                    center.x + (degreeConstant * calculateLevel(calculation)).dp.toPx(),
                    center.y + calculateLevel(calculation).dp.toPx()
                ) // Calculation
                lineTo(
                    center.x,
                    center.y + calculateLevel(accuracy, policy = true).dp.toPx()
                ) // Accuracy
                lineTo(
                    center.x - (degreeConstant * calculateLevel(observation)).dp.toPx(),
                    center.y + calculateLevel(observation).dp.toPx()
                ) // Observation
                lineTo(
                    center.x - (degreeConstant * calculateLevel(memory)).dp.toPx(),
                    center.y - calculateLevel(memory).dp.toPx()
                ) // Memory
                lineTo(center.x, center.y - calculateLevel(speed, policy = true).dp.toPx()) // Speed

            },
            color = if (workMode != 3) CrosshairColorCyan else PinkAppColor,
            style = Fill,
            alpha = 0.5f
        )
    }

}

private fun calculateLevel(rating: Int, policy: Boolean = false): Double {
    val degreeConstant = 1.6
    val ratingCalculate = rating / 10 / degreeConstant

    val length = 1
    val radius = 2

    return if (policy) radius * ratingCalculate else length * ratingCalculate
}
//endregion

//region SkillNames
@OptIn(ExperimentalTextApi::class)
@Composable
fun SkillName(text: String, x: Float = 0f, y: Float = 0f, workMode: Int) {

    //region Старый вариант
    val density = LocalDensity.current.density
    val context = LocalContext.current
    //endregion

    val textMeasurer = rememberTextMeasurer()
    val color = if (workMode == 1 || workMode == 4) Color(0xFF3EB5B2)
    else if (text == "Memory" || text == "Judgement") Color.White else Color.Black

    Canvas(
        modifier = Modifier
            .size(200.dp)
            .zIndex(3f)
    ) {
        val firstPositionX = center.x + x.dp.toPx()
        val firstPositionY = center.y + y.dp.toPx()

        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(firstPositionX - 5.dp.toPx(), firstPositionY)
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun SkillNamePolus(text: String, x: Float = 0f, y: Float = 0f, workMode: Int) {

    //region Старый вариант
    val density = LocalDensity.current.density
    val context = LocalContext.current
    //endregion

    val textMeasurer = rememberTextMeasurer()
    val color = if (workMode == 1 || workMode == 4) Color(0xFF3EB5B2)
    else if (text == "Speed") Color.White else Color.Black

    Canvas(
        modifier = Modifier
            .size(200.dp)
            .zIndex(3f)
    ) {
        val firstPositionX = center.x
        val firstPositionY = center.y + y.dp.toPx()

        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(firstPositionX - x.dp.toPx(), firstPositionY)
        )
    }
}
//endregion

//region Neck
@Composable
fun Neck() {
    Canvas(
        modifier = Modifier
            .size(200.dp)
            .zIndex(-2f)
    ) {

        val radius = 150
        val heightCorrection = -30.dp.toPx()

        drawPath(
            path = Path().apply {

                moveTo(
                    center.x + radius * 1.5f,
                    center.y + (radius * 0.75).dp.toPx() + heightCorrection
                )
                lineTo(
                    center.x - radius * 1.5f / 2,
                    center.y + (radius * 0.75).dp.toPx() + heightCorrection
                )

                moveTo(
                    center.x + radius * 1.5f,
                    center.y + (radius * 0.75).dp.toPx() + heightCorrection
                )
                lineTo(
                    center.x + radius * 1.5f,
                    center.y + (radius * 0.75).dp.toPx() + 80.dp.toPx() + heightCorrection
                )

                moveTo(
                    center.x - radius * 1.5f / 2,
                    center.y + (radius * 0.75).dp.toPx() + heightCorrection
                )
                lineTo(
                    center.x - radius * 1.5f / 2,
                    center.y + (radius * 0.75).dp.toPx() + 80.dp.toPx() + heightCorrection
                )
                lineTo(
                    center.x + radius * 1.5f,
                    center.y + (radius * 0.75).dp.toPx() + 80.dp.toPx() + heightCorrection
                )
                lineTo(
                    center.x + radius * 1.5f,
                    center.y + (radius * 0.75).dp.toPx() + heightCorrection
                )
            },
            color = Color.White,
            style = Fill,
        )
    }
}

//endregion
//region Chin
@Composable
fun Chin(modifier: Modifier) {

    val radius = 150

    val degreeConstant = 1.6
    val length = 60


    Canvas(
        modifier = Modifier
            .zIndex(-2f)
            .size(200.dp)
    ) {


        drawCircle(
            center = Offset(center.x - radius * 2f / 2, center.y + (radius * 0.65).dp.toPx()),
            color = Color.White,
            radius = 40.dp.toPx(),
            style = Fill
        )

        drawCircle(
            center = Offset(
                center.x + 10.dp.toPx() - radius * 2f / 2 - 75.dp.toPx(),
                center.y + (radius * 0.65).dp.toPx() - 40 * 0.75.dp.toPx() - 10.dp.toPx()
            ),
            color = Color.White,
            radius = 10.dp.toPx(),
            style = Fill
        )

        drawPath(
            path = Path().apply {

                moveTo(
                    center.x - radius * 2f / 2,
                    center.y + (radius * 0.65).dp.toPx() + 40.dp.toPx()
                )
                lineTo(
                    center.x - radius * 2f / 2 + 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx() + 40.dp.toPx()
                )
                lineTo(
                    center.x - radius * 2f / 2 + 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx()
                )
                lineTo(center.x - radius * 2f / 2, center.y + (radius * 0.65).dp.toPx())
                lineTo(
                    center.x - radius * 2f / 2,
                    center.y + (radius * 0.65).dp.toPx() + 40.dp.toPx()
                )

                moveTo(
                    center.x - radius * 2f / 2 - 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx()
                )
                lineTo(
                    center.x - radius * 2f / 2 - 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx() - 80.dp.toPx()
                )
                lineTo(
                    center.x - radius * 2f / 2,
                    center.y + (radius * 0.65).dp.toPx() - 80.dp.toPx()
                )
                lineTo(center.x - radius * 2f / 2, center.y + (radius * 0.65).dp.toPx())
                lineTo(
                    center.x - radius * 2f / 2 - 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx()
                )

                moveTo(
                    center.x + 10.dp.toPx() - radius * 2f / 2 - 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx() - 40 * 0.75.dp.toPx()
                )
                lineTo(
                    center.x + 10.dp.toPx() - radius * 2f / 2 - 80.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx() - 40 * 0.75.dp.toPx()
                )
                lineTo(
                    center.x + 10.dp.toPx() - radius * 2f / 2 - 80.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx() - 40 * 0.75.dp.toPx() - 20.dp.toPx()
                )
                lineTo(
                    center.x + 10.dp.toPx() - (degreeConstant * length).dp.toPx() + 30.dp.toPx(),
                    center.y - length.dp.toPx() + 30.dp.toPx()
                )
                lineTo(
                    center.x + 10.dp.toPx() - radius * 2f / 2 - 40.dp.toPx(),
                    center.y + (radius * 0.65).dp.toPx() - 40 * 0.75.dp.toPx()
                )

            },
            color = Color.White,
            style = Fill
        )

    }
}
//endregion
// ######################