package com.kuvalin.brainstorm.presentation.screens.mainmenu

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.navigation.AppNavGraph
import com.kuvalin.brainstorm.navigation.NavigationItem
import com.kuvalin.brainstorm.navigation.staticsClasses.NavigationState
import com.kuvalin.brainstorm.navigation.staticsClasses.Screen
import com.kuvalin.brainstorm.navigation.staticsClasses.rememberNavigationState
import com.kuvalin.brainstorm.presentation.animation.BrainLoading
import com.kuvalin.brainstorm.presentation.screens.achievements.AchievementsScreen
import com.kuvalin.brainstorm.presentation.screens.friends.AddFriendsButtonContent
import com.kuvalin.brainstorm.presentation.screens.friends.FriendsMainScreen
import com.kuvalin.brainstorm.presentation.screens.achievements.QuestionButton
import com.kuvalin.brainstorm.presentation.screens.games.GameSettingsButton
import com.kuvalin.brainstorm.presentation.screens.mainmenu.menu.MenuScreen
import com.kuvalin.brainstorm.presentation.screens.mainmenu.profile.ProfileScreenContent
import com.kuvalin.brainstorm.presentation.screens.statistics.StatisticsMainScreen


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    onClickRefreshButton: () -> Unit
) {

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    val navigationState = rememberNavigationState()


    // TopAppBar
    val appbarHeight = 50

    // Icon
    val sizeIcon = 35
    val paddingBottomIcon = 3
    val paddingTopIcon = 3
    val strokeWidthIcon = 3
    val correctionValueHeightBorder = 1

    // Separator
    val separatorSize = (sizeIcon * 0.8).toInt()
    val separatorColor = Color.Gray
    val separatorWidth = 1


    /* ########################################################################################## */
    Scaffold(
        topBar = {

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
                        onClickRefreshButton = {
                            onClickRefreshButton()
                        }
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
                            selected = selected,
                            onClick = {},
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Icon(
                                        bitmap = GetAssetBitmap(fileName = item.iconFileName),
                                        contentDescription = null,
                                        tint = if (selected) Color(0xFF312D2D) else Color.Gray,
                                        modifier = Modifier
                                            .noRippleClickable {
                                                if (!selected) {
                                                    if (item.screen.route == Screen.Home.route) {
                                                        navigationState.navHostController.navigate(
                                                            Screen.Home.route
                                                        ) {
                                                            popUpTo(Screen.Home.route) {
                                                                inclusive = true
                                                            }
                                                        }
                                                    } else {
                                                        navigationState.navigateTo(item.screen.route)
                                                    }
                                                }
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
                                                        color = Color(0xFFEB6FA6),
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
                                        .height(separatorSize.dp)
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
    ) { paddingValues ->

        // Настоятельно рекомендуется не передавать сложные объекты данных при навигации
        //region AppNavGraph
        AppNavGraph(
            navHostController = navigationState.navHostController,
            mainMenuScreenContent = { MainMenuScreen(navigationState, paddingValues) },
            menuScreenContent = { MenuScreen() },
            profileScreenContent = { ProfileScreenContent(paddingValues) },

            friendsScreenContent = { FriendsMainScreen(paddingValues) },
            achievementsScreenContent = { AchievementsScreen(paddingValues) },
            statisticScreenContent = { StatisticsMainScreen(paddingValues) },
            gamesScreenContent = {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFE6E6E6))
                ){
                    BrainLoading()
                }
            }
        )
        //endregion

    }

}


//region TopAppBarContent
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun TopAppBarContent(
    navigationState: NavigationState,
    onClickRefreshButton: () -> Unit
) {

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
                    .noRippleClickable { navigationState.navigateToMenu() }
            )

            listFilesNames.add("tab_camera.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        clickOnShareState = true
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
                        onClickRefreshButton()
                    }
            )

            listFilesNames.add("tab_add_friends.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        clickOnAddFriendsButton = true
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
                        clickOnAddQuestionButton = true
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
                    }
            )

        }
        "games" -> {
            screenName = "Games"

            listFilesNames.add("tab_settings.png")
            listModifierButtons.add(
                Modifier
                    .size(40.dp)
                    .noRippleClickable {
                        clickOnGameSettingsButton = true
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
                    navigationState.navHostController.popBackStack()
                }
        )
        //endregion
        //region Лого
        AssetImage(
            fileName = "tab_logo.png",
            modifier = Modifier
                .size(40.dp)
                .clickable( // Тут продублировал логигу кнопки назад, чтобы расширить поле нажатия
                    enabled = clickableArrowButtonState // Проверить, влияет ли это на производительность?
                ) {}
                .noRippleClickable {
                    navigationState.navHostController.popBackStack()
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




