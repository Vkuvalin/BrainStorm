package com.kuvalin.brainstorm.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.navigation.staticsClasses.rememberNavigationState
import com.kuvalin.brainstorm.presentation.screens.mainmenu.MainMenuScreen


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    val navigationState = rememberNavigationState()


    // BottomAppBar
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
        // Это чисто прорисовка верхней навигации, с реализацией парсера из navBackStackEntry
        topBar = {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {

                //region BottomAppBar 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(color = Color(0xFF373737)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Image(
//                            modifier = Modifier.size(40.dp),
//                            painter = painterResource(R.drawable.unnamed),
//                            contentDescription = null
//                        )
                        AssetImage(
                            fileName = "tab_logo.png",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "BrainWars",
                            fontSize = 30.sp,
                            color = Color.White
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        Image(
//                            modifier = Modifier.size(40.dp),
//                            painter = painterResource(R.drawable.dots),
//                            contentDescription = null
//                        )
                        AssetImage(
                            fileName = "tab_dots.png",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                }
                //endregion
                //region BottomAppBar 2
                BottomAppBar(
                    modifier = Modifier
                        .height(appbarHeight.dp)
                        .background(color = Color(0xFFE6E6E6))
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

                        NavigationBarItem(
                            selected = selected,
                            onClick = {if (!selected) { navigationState.navigateTo(item.screen.route)}},
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier,
                                ) {

                                    Icon(
                                        bitmap = GetAssetBitmap(fileName = item.iconFileName),
//                                        painter = painterResource(item.icon),
                                        contentDescription = null,
                                        modifier = Modifier
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
                                        //region Удобная штучка
//                                        .then(
//                                            if (selected)
//                                                Modifier
//                                                    .border(0.dp, Color.Transparent, shape = CircleShape)
//                                                    .border(3.dp, Color.Red, shape = RectangleShape)
//                                            else
//                                                Modifier
//                                        )
                                        //endregion
                                    )

                                }
                            },
                            colors = NavigationBarItemDefaults
                                .colors(
                                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                        LocalAbsoluteTonalElevation.current
                                    )
                                )
                        )

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

            }
        }
    ) { paddingValues ->

        // Настоятельно рекомендуется не передавать сложные объекты данных при навигации, а например уникальный идентификатор
        AppNavGraph(
            navHostController = navigationState.navHostController,
            mainMenuScreenContent = { MainMenuScreen(paddingValues) },
            friendsScreenContent = {
                Box(modifier = Modifier.fillMaxSize().background(color = Color.Blue)) {}
            },
            achievementsScreenContent = {
                Box(modifier = Modifier.fillMaxSize().background(color = Color.Yellow)) {}
            },
            statisticScreenContent = {
                Box(modifier = Modifier.fillMaxSize().background(color = Color.Black)) {}
            },
            gamesScreenContent = {
                Box(modifier = Modifier.fillMaxSize().background(color = Color.Green)) {}
            }
        )

    }
}
