package com.kuvalin.brainstorm.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.Action
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.NoRippleInteractionSource
import com.kuvalin.brainstorm.globalClasses.UniversalDecorator
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates.putScreenState
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
import com.kuvalin.brainstorm.presentation.screens.mainmenu.main.MainMenuScreen
import com.kuvalin.brainstorm.presentation.screens.mainmenu.main.ShareContent
import com.kuvalin.brainstorm.presentation.screens.mainmenu.menu.MenuScreen
import com.kuvalin.brainstorm.presentation.screens.mainmenu.profile.ProfileScreenContent
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.searchForWarScreen.SearchForWar
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.WarScreen
import com.kuvalin.brainstorm.presentation.screens.statistics.StatisticsMainScreen
import com.kuvalin.brainstorm.presentation.viewmodels.main.BrainStormMainViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import com.kuvalin.brainstorm.ui.theme.TopAppBarBackgroundColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "StateFlowValueCalledInComposition"
)
@Composable
fun BrainStormMainScreen() {

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    // Компонент
    val component = getApplicationComponent()
    val viewModel: BrainStormMainViewModel = viewModel(factory = component.getViewModelFactory())

    // Навигация
    val navigationState = rememberNavigationState()

    // Для проигрывания звуков
    val context = LocalContext.current

    // TopAppBar
    val appbarHeight = remember { 50 } // TODO подумать, где ещё фигурирует и куда вынести
    val runGameScreenState by GlobalStates.runGameScreenState.collectAsState()
    val animLoadState by GlobalStates.animLoadState.collectAsState()

    // Стейт нажатия по навиге
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation) {
        GlobalStates.AnimLoadState(400) { clickNavigation = false } // Было 350
    }
    /* ########################################################################################## */



    Scaffold(

        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundAppColor),

        topBar = {

            if (!runGameScreenState) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    //region TopAppBar 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = TopAppBarBackgroundColor),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TopAppBarContent(
                            navigationState = navigationState,
                            onClickNavigationButton = { clickNavigation = true }
                        )
                    }
                    //endregion
                    //region TopAppBar 2
                    BottomAppBar(
                        modifier = Modifier.height(appbarHeight.dp),
                        containerColor = BackgroundAppColor
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

                                    onClickNavigationItem(
                                        selected,
                                        animLoadState,
                                        item,
                                        viewModel,
                                        context,
                                        navigationState
                                    ) { clickNavigation = it }

                                },
                                icon = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                        Icon(
                                            bitmap = GetAssetBitmap(fileName = item.iconFileName),
                                            contentDescription = null,
                                            tint = if (selected) Color(0xFF312D2D) else Color.Gray,
                                            modifier = Modifier
                                                .noRippleClickable {
                                                    onClickNavigationItem(
                                                        selected,
                                                        animLoadState,
                                                        item,
                                                        viewModel,
                                                        context,
                                                        navigationState
                                                    ) { clickNavigation = it }
                                                }
                                                .zIndex(-1f)
                                                .size(viewModel.sizeIcon.dp)
                                                .padding(
                                                    top = viewModel.paddingTopIcon.dp,
                                                    bottom = viewModel.paddingBottomIcon.dp
                                                )
                                                .drawBehind {
                                                    if (selected) {
                                                        drawLine(
                                                            color = PinkAppColor,
                                                            strokeWidth = viewModel.strokeWidthIcon.dp.toPx(),
                                                            start = Offset(
                                                                0f,
                                                                viewModel.sizeIcon.dp.toPx() + viewModel.correctionValueHeightBorder.dp.toPx()
                                                            ),
                                                            end = Offset(
                                                                viewModel.sizeIcon.dp.toPx(),
                                                                viewModel.sizeIcon.dp.toPx() + viewModel.correctionValueHeightBorder.dp.toPx()
                                                            )
                                                        )
                                                    }
                                                }
                                        )

                                    }
                                },
                                colors = NavigationBarItemDefaults.colors( indicatorColor = Color(0xFFE6E6E6) )
                            )
                            //endregion

                            if (index < items.size - 1) {
                                Column(
                                    modifier = Modifier.zIndex(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(viewModel.separatorHeight.dp)
                                            .width(viewModel.separatorWidth.dp)
                                            .background(viewModel.separatorColor)
                                    )
                                }
                            }

                        }
                    }
                    //endregion

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = Color.LightGray)
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
    onClickNavigationButton: () -> Unit
) {

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Компонент и производные
    val component = getApplicationComponent()
    val viewModel: BrainStormMainViewModel = viewModel(factory = component.getViewModelFactory())


    // Для проигрывания звуков
    val context = LocalContext.current
    val animLoadState by GlobalStates.animLoadState.collectAsState()


    // Получаем доступ к текущему файлу NavDestination, чтобы узнать, на каком мы экране.
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val selected = navBackStackEntry?.destination?.route.toString()

    var arrowButtonAlpha = 1f
    var clickableArrowButtonState by remember { mutableStateOf(true) }

    var screenName by remember { mutableStateOf("") }

    val listFilesNames = mutableListOf<String>()
    val listModifierButtons = mutableListOf<Modifier>()


    // Обработка нажатий на кнопки верхнего TopAppBar
    val clickOnShareState by viewModel.clickOnShareState.collectAsState()
    if (clickOnShareState) { ShareContent { viewModel.toggleShareState(false) } }

    val clickOnAddFriendsButton by viewModel.clickOnAddFriendsButton.collectAsState()
    if (clickOnAddFriendsButton) { AddFriendsButtonContent { viewModel.toggleAddFriendsButton(false) } }

    val clickOnAddQuestionButton by viewModel.clickOnAddQuestionButton.collectAsState()
    if (clickOnAddQuestionButton) { QuestionButton { viewModel.toggleAddQuestionButton(false) } }

    val clickOnGameSettingsButton by viewModel.clickOnGameSettingsButton.collectAsState()
    if (clickOnGameSettingsButton) { GameSettingsButton { viewModel.toggleGameSettingsButton(false) } }

    /* ########################################################################################## */



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
                        if (animLoadState) {
                            navigationState.navigateToMenu()
                            viewModel.playChangeNavigationSound(context = context)
                            onClickNavigationButton()
                        }
                    }
            )

            listFilesNames.add("tab_camera.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        viewModel.toggleShareState(true)
                        viewModel.playChoiceClickSound(context = context)
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
                    .noRippleClickable {
                        viewModel.playChoiceClickSound(context = context)

                        CoroutineScope(Dispatchers.Default).launch {
                            UniversalDecorator().executeAsync(
                                mainFunc = { delay(3000) },
                                beforeActions = listOf(Action.Execute{ putScreenState("animBrainLoadState", true) }),
                                afterActions = listOf(Action.Execute{ putScreenState("animBrainLoadState", false) })
                            )
                        }
                        onClickNavigationButton()
                    }
            )

            listFilesNames.add("tab_add_friends.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        viewModel.playChoiceClickSound(context = context)
                        viewModel.toggleAddFriendsButton(true)
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
                        viewModel.playChoiceClickSound(context = context)
                        viewModel.toggleAddQuestionButton(true)
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
                        CoroutineScope(Dispatchers.Default).launch {
                            UniversalDecorator().executeAsync(
                                mainFunc = { delay(3000) },
                                beforeActions = listOf(Action.Execute{ putScreenState("animBrainLoadState", true) }),
                                afterActions = listOf(Action.Execute{ putScreenState("animBrainLoadState", false) })
                            )
                        }
                        viewModel.playChoiceClickSound(context = context)
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
                        viewModel.playChoiceClickSound(context = context)
                        viewModel.toggleGameSettingsButton(true)
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
                    if (animLoadState) {
                        navigationState.navHostController.popBackStack()
                        viewModel.playChangeNavigationSound(context = context)
                        onClickNavigationButton()
                    }
                }
        )
        //endregion
        //region Лого
        AssetImage(
            fileName = "tab_logo.png",
            modifier = Modifier
                .size(40.dp)
                // TODO о чем я вообще ниже говорю?
                // Тут продублировал логику кнопки назад, чтобы расширить поле нажатия
                // Проверить, влияет ли это на производительность?
                .clickable(enabled = clickableArrowButtonState) {}
                .noRippleClickable {
                    if (animLoadState) {
                        navigationState.navHostController.popBackStack()
                        viewModel.playChangeNavigationSound(context = context)
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
            if (index > 0 && index != listFilesNames.size) {
                Spacer(modifier = Modifier.width(5.dp))
            }

            if (fileName == "tab_refresh_stats.png") {  // Почему-то, сука, в виде иконци уменьшается до невозможного
                Image(
                    bitmap = GetAssetBitmap(fileName = "tab_refresh_stats.png"),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = listModifierButtons[index]
                )
            } else {
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
//region onClickNavigationItem
private fun onClickNavigationItem(
    selected: Boolean,
    animLoadState: Boolean,
    item: NavigationItem,
    viewModel: BrainStormMainViewModel,
    context: Context,
    navigationState: NavigationState,
    clickNavigation: (Boolean) -> Unit
) {
    if (!selected && animLoadState) {
        clickNavigation(true)
        viewModel.playChangeNavigationSound(context = context)
        if (item.screen.route == Screen.Home.route) {
            navigationState.navHostController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        } else {
            navigationState.navigateTo(item.screen.route)
        }
    }
}
//endregion



