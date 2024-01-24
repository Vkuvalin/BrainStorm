package com.kuvalin.brainstorm.presentation.screens.mainmenu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.R
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.findAssetFiles


@Composable
fun MainMenuScreen(
    paddingValues: PaddingValues
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .background(color = Color(0xFFE6E6E6))
            .fillMaxSize()
    ) {

        //region Аватарка + жизни/койны
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(color = Color(0xFF00BAB9)) // TODO вынести цвета в отдельный файл (ну а вообще-то я потом всё ещё 1000 раз буду причесывать)
                .wrapContentHeight()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Аватарка
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .border(width = 2.dp, color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
//                Image(painter = painterResource(R.drawable.user), contentDescription = null)
                AssetImage(fileName = "av_user.png")
            }

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp)
            )

            // Жизни и койны
            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_like_set),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_like_set),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_like_set),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_like_set),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_like_set),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            painter = painterResource(id = R.drawable.ic_stars),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "200",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W300,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }

        }
//endregion


        Column(
            modifier = Modifier
                .height(screenHeight)
                .width(screenWidth),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            

            //region Карточки статистики
            Row(
                modifier = Modifier
                    .width(screenWidth)
                    .offset(x = (35 / 2).dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                StatisticsCard(
                    pathImage = "av_papa.png",
                    cardName = "Grade",
                    text = "Turtle"
                )

                StatisticsCard(
                    pathImage = "av_user.png",
                    cardName = "Rank",
                    text = "924",
                    text2 = "League Save" // League Up | League Drop
                )

            }
            //endregion

            DrawingChart()

            //region Кнопка
            Button(
                modifier = Modifier
                    .clip(RoundedCornerShape(25))
                    .background(color = Color(0xFF00BAB9))
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(25)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0F)
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Challenge",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp,
                            vertical = 10.dp
                        )
                )
            }
            //endregion
        }


    }
}


//region Карточка статистики
@Composable
private fun StatisticsCard(
    pathImage: String,
    cardName: String,
    text: String,
    text2: String = ""
) {

    val configuration = LocalConfiguration.current

    Row(
        modifier = Modifier.width(configuration.screenWidthDp.dp / 2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .zIndex(2f)
                .background(color = Color.White)
                .border(width = 1.dp, color = Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AssetImage(fileName = pathImage)
        }

        Column(
            modifier = Modifier
                .offset(x = (-35).dp)
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = cardName,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(150.dp) // 140
                    .clip(RoundedCornerShape(25))
                    .background(color = Color.White)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(25))

            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(start = (40).dp)
                    )
                    if (text2 != "") {
                        Text(
                            text = text2,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W400,
                            letterSpacing= 0.1.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = (40).dp).offset(y = (-2).dp)
                        )
                    } else {
                        LinearProgressIndicator(
                            progress = 0.7f,
                            color = Color(0xFF01BBBA),
                            trackColor = Color(0xFF373737),
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(45))
                        )
                    }
                }
            }
        }
    }
}
//endregion

// ### DrawingChart
//region DrawingChart
@Composable
fun DrawingChart() {

    val modifier: Modifier = Modifier
        .clip(CircleShape)
        .size(240.dp)


    Box(
        modifier = Modifier
            .padding(bottom = 22.dp, start = 20.dp, end = 20.dp)
            .scale(0.9f)
    ) {

        Circle(modifier)
        CircleBackground(modifier)
        Crosshair(modifier)

        SkillNamePolus(text = "Speed", x = 5f, y = -110f)
        SkillNamePolus(text = "Accuracy", x = 10f, y = 125f)

        SkillName(text = "Judgement", x = 75f * 1.6f, y = -55f)
        SkillName(text = "Calculation", x = 75f * 1.6f, y = 70f)
        SkillName(text = "Memory", x = -80f * 1.6f, y = -55f)
        SkillName(text = "Observation", x = -98f * 1.6f, y = 70f)

        Neck()
        Chin(modifier)

        Graph(
            speed = (851 * 0.9).toInt(),
            judgement = (803 * 0.9).toInt(),
            calculation = (701 * 0.9).toInt(),
            accuracy = (951 * 0.9).toInt(),
            observation = (845 * 0.9).toInt(),
            memory = (998 * 0.9).toInt()
        )


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
private fun CircleBackground(modifier: Modifier) {
    Canvas(
        modifier = modifier
            .zIndex(-1f)
    ) {

        drawCircle(
            center = Offset(center.x, center.y),
            color = Color(0xFFE6E6E6),
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
            color = Color(0xFF00C5C0),
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
    memory: Int = 0
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
            color = Color(0xFF05A5A3),
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
fun SkillName(text: String, x: Float = 0f, y: Float = 0f) {

    //region Старый вариант
    val density = LocalDensity.current.density
    val context = LocalContext.current
    //endregion

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .size(200.dp)
            .zIndex(3f)
    ) {
        val firstPositionX = center.x + x.dp.toPx()
        val firstPositionY = center.y + y.dp.toPx()

        //region Старый вариант
//        val paint = Paint().apply {
//            textAlign = Paint.Align.CENTER
//            textSize = 12.sp.toPx()
//            color = Color(0xFF959DCE).toArgb()
//        }
//
//
//        drawContext.canvas.nativeCanvas.drawText(
//            "$text",
//            firstPositionX,
//            firstPositionY,
//            paint
//        )
        //endregion

        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(
                color = Color(0xFF3EB5B2),
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
fun SkillNamePolus(text: String, x: Float = 0f, y: Float = 0f) {

    //region Старый вариант
    val density = LocalDensity.current.density
    val context = LocalContext.current
    //endregion

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .size(200.dp)
            .zIndex(3f)
    ) {
        val firstPositionX = center.x
        val firstPositionY = center.y + y.dp.toPx()

        //region Старый вариант
//        val paint = Paint().apply {
//            textAlign = Paint.Align.CENTER
//            textSize = 12.sp.toPx()
//            color = Color(0xFF959DCE).toArgb()
//        }
//
//
//        drawContext.canvas.nativeCanvas.drawText(
//            "$text",
//            firstPositionX,
//            firstPositionY,
//            paint
//        )
        //endregion

        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = TextStyle(
                color = Color(0xFF3EB5B2),
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
// ###