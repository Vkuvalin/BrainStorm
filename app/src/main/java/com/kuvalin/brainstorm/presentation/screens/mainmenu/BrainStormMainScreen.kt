package com.kuvalin.brainstorm.presentation.screens.mainmenu

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.NoRippleInteractionSource
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.navigation.mainmenu.AppNavGraph
import com.kuvalin.brainstorm.navigation.mainmenu.NavigationItem
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen
import com.kuvalin.brainstorm.navigation.staticsClasses.rememberNavigationState
import com.kuvalin.brainstorm.presentation.screens.achievements.AchievementsScreen
import com.kuvalin.brainstorm.presentation.screens.achievements.QuestionButton
import com.kuvalin.brainstorm.presentation.screens.friends.AddFriendsButtonContent
import com.kuvalin.brainstorm.presentation.screens.friends.FriendsMainScreen
import com.kuvalin.brainstorm.presentation.screens.game.GameSettingsButton
import com.kuvalin.brainstorm.presentation.screens.game.GamesMainScreen
import com.kuvalin.brainstorm.presentation.screens.mainmenu.menu.MenuScreen
import com.kuvalin.brainstorm.presentation.screens.mainmenu.profile.ProfileScreenContent
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.SearchForWar
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.WarScreen
import com.kuvalin.brainstorm.presentation.screens.statistics.StatisticsMainScreen
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun MainScreen(
    onClickRefreshButton: () -> Unit
) {

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    val navigationState = rememberNavigationState()

    // Для проигрывания звуков
    val context = LocalContext.current
    val musicScope = CoroutineScope(Dispatchers.Default)

    // TopAppBar
    val appbarHeight = 50
    val runGameScreenState = GlobalStates.runGameScreenState.collectAsState().value
    val animLoadState = GlobalStates.animLoadState.collectAsState().value

    // Стейт нажатия по навиге
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(350){ clickNavigation = false } }

    // Icon
    val sizeIcon = 35
    val paddingBottomIcon = 3
    val paddingTopIcon = 3
    val strokeWidthIcon = 3
    val correctionValueHeightBorder = 1

    // Separator
    val separatorHeight = (sizeIcon * 0.8).toInt()
    val separatorColor = Color.Gray
    val separatorWidth = 1
    /* ########################################################################################## */



    Scaffold(

        modifier = Modifier.fillMaxSize().background(color = BackgroundAppColor),

        topBar = {

            if (!runGameScreenState){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //region TopAppBar 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color(0xFF373737)),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TopAppBarContent(
                            navigationState = navigationState,
                            onClickNavigationButton = { clickNavigation = true },
                            onClickRefreshButton = { onClickRefreshButton() }
                        )
                    }
                    //endregion
                    //region TopAppBar 2
                    BottomAppBar(
                        modifier = Modifier.height(appbarHeight.dp),
                        containerColor = Color(0xFFE6E6E6)
                    ) {

                        // Получаем доступ к текущему файлу NavDestination (из доки)
                        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                        // для выделения выбранного элемента
                        val items = listOf(
                            NavigationItem.Home,
                            NavigationItem.Friends,
                            NavigationItem.Achievements,
                            NavigationItem.Statistic,
                            NavigationItem.Games
                        )
                        items.forEachIndexed { index, item ->

                            val selected = navBackStackEntry?.destination?.hierarchy?.any {
                                it.route == item.screen.route
                            } ?: false

                            //region NavigationBarItem
                            NavigationBarItem(
                                interactionSource = remember { NoRippleInteractionSource() },
                                modifier = Modifier.fillMaxWidth(),
                                selected = selected,
                                onClick = {

                                    // TODO - ОБЪЕДИНИТЬ (Вообще, как лучше в даном случае быть?)
                                    if (!selected && animLoadState) {
                                        clickNavigation = true

                                        if (item.screen.route == Screen.Home.route) {
                                            musicScope.launch {
                                                MusicPlayer(context = context).run {
                                                    playChangeNavigation()
                                                    delay(3000)
                                                    release()
                                                }
                                            }
                                            navigationState.navHostController.navigate(
                                                Screen.Home.route
                                            ){
                                                popUpTo(Screen.Home.route) {
                                                    inclusive = true
                                                }
                                            }
                                        } else {
                                            musicScope.launch {
                                                MusicPlayer(context = context).run {
                                                    playChangeNavigation()
                                                    delay(3000)
                                                    release()
                                                }
                                            }
                                            navigationState.navigateTo(item.screen.route)
                                        }
                                    }
                                    // TODO - ОБЪЕДИНИТЬ


                                },
                                icon = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {

                                        Icon(
                                            bitmap = GetAssetBitmap(fileName = item.iconFileName),
                                            contentDescription = null,
                                            tint = if (selected) Color(0xFF312D2D) else Color.Gray,
                                            modifier = Modifier
                                                .noRippleClickable {

                                                    // TODO - ОБЪЕДИНИТЬ (Вообще, как лучше в даном случае быть?)
                                                    if (!selected && animLoadState) {
                                                        clickNavigation = true

                                                        if (item.screen.route == Screen.Home.route) {
                                                            musicScope.launch {
                                                                MusicPlayer(context = context).run {
                                                                    playChangeNavigation()
                                                                    delay(3000)
                                                                    release()
                                                                }
                                                            }
                                                            navigationState.navHostController.navigate(
                                                                Screen.Home.route
                                                            ){
                                                                popUpTo(Screen.Home.route) {
                                                                    inclusive = true
                                                                }
                                                            }
                                                        } else {
                                                            musicScope.launch {
                                                                MusicPlayer(context = context).run {
                                                                    playChangeNavigation()
                                                                    delay(3000)
                                                                    release()
                                                                }
                                                            }
                                                            navigationState.navigateTo(item.screen.route)
                                                        }
                                                    }
                                                    // TODO - ОБЪЕДИНИТЬ

                                                }
                                                .zIndex(-1f)
                                                .size(sizeIcon.dp)
                                                .padding(
                                                    top = paddingTopIcon.dp,
                                                    bottom = paddingBottomIcon.dp
                                                )
                                                .drawBehind {
                                                    if (selected) {
                                                        drawLine(
                                                            color = PinkAppColor,
                                                            strokeWidth = strokeWidthIcon.dp.toPx(),
                                                            start = Offset(
                                                                0f,
                                                                sizeIcon.dp.toPx() + correctionValueHeightBorder.dp.toPx()
                                                            ),
                                                            end = Offset(
                                                                sizeIcon.dp.toPx(),
                                                                sizeIcon.dp.toPx() + correctionValueHeightBorder.dp.toPx()
                                                            )
                                                        )
                                                    }
                                                }
                                        )

                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFE6E6E6))
                            )
                            //endregion

                            if (index < items.size - 1) {
                                //region Иные реализации
    //                                Spacer(
    //                                    modifier = Modifier
    //                                        .fillMaxHeight()
    ////                                        .height(20.dp)
    //                                        .width(2.dp)
    //                                        .background(Color.Black)
    //                                )
    //                                Divider(
    //                                    color = Color.Blue,
    //                                    modifier = Modifier
    //                                        .fillMaxHeight()
    //                                        .width(2.dp)
    //                                )
                                //endregion
                                Column (
                                    modifier = Modifier.zIndex(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(separatorHeight.dp)
                                            .width(separatorWidth.dp)
                                            .background(separatorColor)
                                    )
                                }
                            }

                        }
                    }
                    //endregion

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(color = Color.DarkGray)
                    )

                }
            }

        }

    ) { paddingValues ->

        // Настоятельно рекомендуется не передавать сложные объекты данных при навигации
        //region AppNavGraph
        AppNavGraph(
            navHostController = navigationState.navHostController,

            mainMenuScreenContent = { MainMenuScreen(navigationState, paddingValues) },
            menuScreenContent = { MenuScreen() },
            profileScreenContent = { ProfileScreenContent(paddingValues) },

            //region Почему решил сделать так?
            /*
            Решил не делать аналогично с GamesMainScreen, потому что там прям отдельная навигационный
            экран с множеством особенностей, а тут всё-таки просто несколько последовательных экранов.

            Да, логика своя будет, но она будет привязана к своей отдельной ViewModel.
            В общем не вижу проблемы в данной реализации, хоть и вышеупомянутый вариант выглядит лучше.
            */
            //endregion
            searchForWarScreenContent = { SearchForWar(navigationState) },
            warScreenContent = { WarScreen(navigationState) },

            friendsScreenContent = { FriendsMainScreen(paddingValues) },
            achievementsScreenContent = { AchievementsScreen(paddingValues) },
            statisticScreenContent = { StatisticsMainScreen(paddingValues) },
            gamesScreenContent = { GamesMainScreen(paddingValues) }
        )
        //endregion

    }

}


//region TopAppBarContent
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun TopAppBarContent(
    navigationState: NavigationState,
    onClickNavigationButton: () -> Unit,
    onClickRefreshButton: () -> Unit,
) {

    // Для проигрывания звуков
    val context = LocalContext.current
    val musicScope = CoroutineScope(Dispatchers.Default)

    val animLoadState = GlobalStates.animLoadState.collectAsState().value

    // Получаем доступ к текущему файлу NavDestination, чтобы узнать, на каком мы экране.
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val selected = navBackStackEntry?.destination?.route.toString()

    var arrowButtonAlpha = 1f
    var clickableArrowButtonState = true

    var screenName = ""
    val listFilesNames = mutableListOf<String>()
    val listModifierButtons = mutableListOf<Modifier>()

    var clickOnShareState by remember { mutableStateOf(false) }
    if (clickOnShareState){
        ShareContent(){ clickOnShareState = false }
    }

    var clickOnAddFriendsButton by remember { mutableStateOf(false) }
    if (clickOnAddFriendsButton){
        AddFriendsButtonContent(){ clickOnAddFriendsButton = false }
    }

    var clickOnAddQuestionButton by remember { mutableStateOf(false) }
    if (clickOnAddQuestionButton){
        QuestionButton(){ clickOnAddQuestionButton = false }
    }

    var clickOnGameSettingsButton by remember { mutableStateOf(false) }
    if (clickOnGameSettingsButton){
        GameSettingsButton(){ clickOnGameSettingsButton = false }
    }


    //region Кнопки и их логика
    when (selected) {
        "main_menu", "" -> {
            arrowButtonAlpha = 0f
            clickableArrowButtonState = false
            screenName = "BrainStorm"

            listFilesNames.add("tab_dots.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        if (animLoadState){
                            navigationState.navigateToMenu()
                            musicScope.launch {
                                MusicPlayer(context = context).run {
                                    playChangeNavigation()
                                    delay(3000)
                                    release()
                                }
                            }
                            onClickNavigationButton()
                        }
                    }
            )

            listFilesNames.add("tab_camera.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        clickOnShareState = true
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChoiceClick()
                                delay(3000)
                                release()
                            }
                        }
                    }
            )
        }
        "menu" -> {
            screenName = "Menu"
        }
        "profile" -> {
            screenName = "Profile"
        }
        "friends" -> {
            screenName = "Friends"

            listFilesNames.add("tab_refresh.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable { // TODO нужно немного иначе обрабатывать (подумать)
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChoiceClick()
                                delay(3000)
                                release()
                            }
                        }
                        onClickRefreshButton()
                        onClickNavigationButton()
                    }
            )

            listFilesNames.add("tab_add_friends.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChoiceClick()
                                delay(3000)
                                release()
                            }
                        }
                        clickOnAddFriendsButton = true
                        onClickNavigationButton()
                    }
            )
        }
        "achievements" -> {
            screenName = "Achievements"

            listFilesNames.add("tab_question_1.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChoiceClick()
                                delay(3000)
                                release()
                            }
                        }
                        clickOnAddQuestionButton = true
                        onClickNavigationButton()
                    }
            )

        }
        "statistics" -> {
            screenName = "Statistics"

            listFilesNames.add("tab_refresh_stats.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        onClickRefreshButton()
                        onClickNavigationButton()
                    }
            )

        }
        "games" -> {
            screenName = "Games"

            listFilesNames.add("tab_settings.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable { // TODO нужно немного иначе обрабатывать (подумать)
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChoiceClick()
                                delay(3000)
                                release()
                            }
                        }
                        clickOnGameSettingsButton = true
                        onClickNavigationButton()
                    }
            )
        }
    }
    //endregion

    //region Лого и название экрана
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .offset(x = (-5).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //region Стрелка "назад"
        AssetImage(
            fileName = "tab_arrow_button.png",
            modifier = Modifier
                .height(20.dp)
                .alpha(arrowButtonAlpha)
                .clickable(
                    enabled = clickableArrowButtonState
                ) {}
                .noRippleClickable {
                    if (animLoadState){
                        navigationState.navHostController.popBackStack()
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChangeNavigation()
                                delay(3000)
                                release()
                            }
                        }
                        onClickNavigationButton()
                    }
                }
        )
        //endregion
        //region Лого
        AssetImage(
            fileName = "tab_logo.png",
            modifier = Modifier
                .size(40.dp) // TODO о чем я вообще ниже говорю?
                .clickable( // Тут продублировал логику кнопки назад, чтобы расширить поле нажатия
                    enabled = clickableArrowButtonState // Проверить, влияет ли это на производительность?
                ) {}
                .noRippleClickable {
                    if (animLoadState){
                        navigationState.navHostController.popBackStack()
                        musicScope.launch {
                            MusicPlayer(context = context).run {
                                playChangeNavigation()
                                delay(3000)
                                release()
                            }
                        }
                        onClickNavigationButton()
                    }
                }
        )
        //endregion
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = screenName, fontSize = 30.sp, color = Color.White)
    }
    //endregion

    //region Кнопки справа
    Row(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listFilesNames.forEachIndexed { index, fileName ->
            if (index > 0 && index != listFilesNames.size) { Spacer(modifier = Modifier.width(5.dp)) }

            if (fileName == "tab_refresh_stats.png"){  // Почему-то, сука, в виде иконци уменьшается до невозможного
                Image(
                    bitmap = GetAssetBitmap(fileName = "tab_refresh_stats.png"),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = listModifierButtons[index]
                )
            }else {
                Icon(
                    bitmap = GetAssetBitmap(fileName = fileName),
                    contentDescription = null,
                    tint = Color.White, // Не стал переделывать само фото, просто покрасил
                    modifier = listModifierButtons[index]
                )
            }
        }
    }
    //endregion

}
//endregion




