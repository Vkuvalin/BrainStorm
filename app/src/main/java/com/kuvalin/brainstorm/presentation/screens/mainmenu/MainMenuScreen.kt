package com.kuvalin.brainstorm.presentation.screens.mainmenu

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.kuvalin.brainstorm.R
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.globalClasses.presentation.rememberMusicPlayer
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CrosshairColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.GameLevelAColor
import com.kuvalin.brainstorm.ui.theme.GameLevelBColor
import com.kuvalin.brainstorm.ui.theme.GameLevelCColor
import com.kuvalin.brainstorm.ui.theme.GameLevelSColor
import com.kuvalin.brainstorm.ui.theme.LinearTrackColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun MainMenuScreen(
    navigationState: NavigationState,
    paddingValues: PaddingValues
) {


    // Для проигрывания звуков
    val context = LocalContext.current
    val musicScope = CoroutineScope(Dispatchers.Default)

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .background(color = BackgroundAppColor)
            .fillMaxSize()
    ) {

        //region Аватарка + жизни/койны
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(color = CyanAppColor)
                .wrapContentHeight()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Аватарка
            Box(modifier = Modifier.noRippleClickable {
                navigationState.navigateToProfile()
                musicScope.launch {
                    MusicPlayer(context = context).run {
                        playChangeNavigation()
                        delay(3000)
                        release()
                    }
                }
            }){
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    AssetImage(fileName = "av_user.png")
                }
                //region Pencil
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(10))
                        .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(10))
                        .background(color = Color.White)
                        .align(alignment = Alignment.BottomEnd)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    AssetImage(
                        fileName = "ic_avatar_pencil.png",
                        modifier = Modifier
                            .size(15.dp)
                    )
                }
                //endregion
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

            StatisticsCards(screenWidth)
            DrawingChart()
            ButtonChallenge()

        }

    }
}


// Я зафаршмачил эту часть. Переделывать лень.
//region StatisticsCards
@Composable
fun StatisticsCards(screenWidth: Dp) {
    Row(
        modifier = Modifier
            .width(screenWidth)
            .offset(x = (35 / 2).dp)
        ,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        StatisticsCard(
            type = "Grade",
            cardName = "Grade",
            text = "Bear"
        )

        StatisticsCard(
            type = "League",
            cardName = "Rank",
            text = "924",
            text2 = "League Save" // League Up | League Drop
        )

    }
}
//endregion
//region Карточка статистики
@Composable
fun StatisticsCard(
    type: String,
    cardName: String,
    text: String,
    text2: String = ""
) {

    // Для проигрывания звуков
    val context = LocalContext.current
    val scope = CoroutineScope(Dispatchers.Default)
    val configuration = LocalConfiguration.current

    val leagueList = mutableListOf(
        mutableListOf("S", GameLevelSColor, false),
        mutableListOf("A", GameLevelAColor, true),
        mutableListOf("B", GameLevelBColor, false),
        mutableListOf("C", GameLevelCColor, false)
    )

    val gradeList = mutableListOf( // TODO перевести в базу, а затем добавить в ShareStatistics.kt
        mutableListOf("level_1_mouse.png", false),
        mutableListOf("level_2_snake.png", false),
        mutableListOf("level_3_beaver.png", false),
        mutableListOf("level_4_pig.png", false),
        mutableListOf("level_5_cow.png", false),
        mutableListOf("level_6_cat.png", false),
        mutableListOf("level_7_fox.png", false),
        mutableListOf("level_8_dog.png", false),
        mutableListOf("level_9_giraffe.png", false),
        mutableListOf("level_10_hippo.png", false),
        mutableListOf("level_11_elephant.png", false),
        mutableListOf("level_12_bear.png", true),
        mutableListOf("level_13_eagle.png", false),
        mutableListOf("level_14_lion.png", false)
    )

    var onClickGrade by remember { mutableStateOf(false) }
    if (onClickGrade){ GradeDialog(MusicPlayer(context = context)) { onClickGrade = false } }

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
                .border(width = 1.dp, color = Color.White, shape = CircleShape)
                .noRippleClickable {
                    if (type == "Grade"){
                        scope.launch {
                            MusicPlayer(context).playChoiceClick()
                        }
                        onClickGrade = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (type == "Grade"){
                gradeList.forEach {
                    if (it[1] == true){
                        AssetImage(fileName = it[0] as String, modifier = Modifier.padding(5.dp))
                    }
                }
            }else{
                leagueList.forEach {
                    if (it[2] == true){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(color = it[1] as Color)
                                .border(
                                    width = 4.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        ){
                            Text(
                                text = it[0] as String,
                                fontSize = 24.sp,
                                color = Color.White,
                                fontWeight = FontWeight.W400,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                            )
                        }
                    }
                }

            }
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
                            modifier = Modifier
                                .padding(start = (40).dp)
                                .offset(y = (-2).dp)
                        )
                    } else {
                        LinearProgressIndicator(
                            progress = 0.7f,
                            color = CyanAppColor,
                            trackColor = LinearTrackColor,
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
            speed = (Random.nextInt(100, 1000) * 0.9).toInt(),
            judgement = (Random.nextInt(100, 1000) * 0.9).toInt(),
            calculation = (Random.nextInt(100, 1000) * 0.9).toInt(),
            accuracy = (Random.nextInt(100, 1000) * 0.9).toInt(),
            observation = (Random.nextInt(100, 1000) * 0.9).toInt(),
            memory = (Random.nextInt(100, 1000) * 0.9).toInt()
        )

//        Graph(
//            speed = (851 * 0.9).toInt(),
//            judgement = (803 * 0.9).toInt(),
//            calculation = (701 * 0.9).toInt(),
//            accuracy = (951 * 0.9).toInt(),
//            observation = (845 * 0.9).toInt(),
//            memory = (998 * 0.9).toInt()
//        )


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
            color = CrosshairColor,
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
            color = CrosshairColor,
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

//region ButtonChallenge
@Composable
private fun ButtonChallenge() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(25))
            .background(color = CyanAppColor)
            .border(width = 1.dp, color = Color(0xFFE6E6E6), shape = RoundedCornerShape(25))
            .noRippleClickable { },
    ) {
        Text(
            text = "Challenge",
            fontSize = 25.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 20.dp)
        )
    }
}
//endregion
//region GradeDialog
@Composable
fun GradeDialog(
    mediaPlayer: MusicPlayer,
    onClickDismiss: () -> Unit
){
    val musicScope = CoroutineScope(Dispatchers.Default)

    val gradeList = mutableListOf(
        mutableListOf("level_1_mouse.png", false),
        mutableListOf("level_2_snake.png", false),
        mutableListOf("level_3_beaver.png", false),
        mutableListOf("level_4_pig.png", false),
        mutableListOf("level_5_cow.png", false),
        mutableListOf("level_6_cat.png", false),
        mutableListOf("level_7_fox.png", false),
        mutableListOf("level_8_dog.png", false),
        mutableListOf("level_9_giraffe.png", false),
        mutableListOf("level_10_hippo.png", false),
        mutableListOf("level_11_elephant.png", false),
        mutableListOf("level_12_bear.png", true),
        mutableListOf("level_13_eagle.png", false),
        mutableListOf("level_14_lion.png", false)
    )
    var checker = false  // Можно оставить?


    Dialog(
        onDismissRequest = {
            musicScope.launch {
                mediaPlayer.run {
                    playChoiceClick()
                    delay(3000)
                    release()
                }
            }
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(color = Color(0xFFE6E6E6))
            ) {

                //region Крестик
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .offset(x = (10).dp, y = (-10).dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .background(color = Color.White)
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            musicScope.launch {
                                mediaPlayer.run {
                                    playChoiceClick()
                                    delay(3000)
                                    release()
                                }
                            }
                            onClickDismiss()
                        }
                )
                //endregion
                GradeListLabel()
                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxWidth()){
                    LazyVerticalGrid(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(color = Color(0xFFE6E6E6)),
                        columns = GridCells.Adaptive(minSize = 60.dp)
                    ) {
                        items(gradeList.size) { position ->
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .zIndex(2f)
                                    .border(width = 1.dp, color = Color(0xFFE6E6E6), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ){

                                if (!checker){
                                    if (gradeList[position][1] == true){ checker = true }
                                    AssetImage(fileName = gradeList[position][0] as String, modifier = Modifier.padding(5.dp))
                                }else {
                                    AssetImage(fileName = "ic_grade_question.png", modifier = Modifier.padding(5.dp))
                                }

                            }
                        }
                    }
                }


            }
        },
    )
}
//endregion
//region AchievementsItemLabel
@Composable
private fun GradeListLabel() {
    Text(
        text = "Grade List",
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .offset(y = -(20).dp)
    )
}
//endregion

