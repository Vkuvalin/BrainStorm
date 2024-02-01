package com.kuvalin.brainstorm.presentation.screens.friends

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.navigation.FriendsNavigationItem
import com.kuvalin.brainstorm.navigation.FriendsScreenNavGraph
import com.kuvalin.brainstorm.navigation.staticsClasses.rememberNavigationState


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FriendsMainScreen(
    paddingValuesParent: PaddingValues
) {
    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */
    val navigationState = rememberNavigationState()


    // TopAppBar
    val appbarHeight = 50

    /* ########################################################################################## */

    Scaffold(
        modifier = Modifier
            .padding(top = paddingValuesParent.calculateTopPadding()),

        //region TopBar
        topBar = {

            Column(
                modifier = Modifier
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(appbarHeight.dp)
                ) {

                    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                    val items = listOf(
                        FriendsNavigationItem.ListFriends,
                        FriendsNavigationItem.Messages,
                        FriendsNavigationItem.Requests
                    )
                    items.forEachIndexed { index, item ->

                        val selected = navBackStackEntry?.destination?.hierarchy?.any {
                            it.route == item.screen.route
                        } ?: false

                        NavigationBarItem(
                            selected = selected,
                            onClick = {},
                            icon = {},
                            label = {
                                BoxWithConstraints(
                                    modifier = Modifier.offset(y = 16.dp), // Интересно даже...
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.sectionName,
                                        color = if (selected) Color(0xFF00BBBA) else Color.White,
                                        fontSize = 20.sp,
                                        softWrap = false,
                                        fontWeight = if (selected) FontWeight.W400 else FontWeight.W300,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .noRippleClickable { if (!selected) { navigationState.navigateTo(item.screen.route)} }
                                            .requiredWidth(maxWidth + 22.dp)
                                            .requiredHeight(maxHeight + 20.dp)
                                            .fillMaxHeight()
                                            .background(color = if (selected) Color(0xFFE6E6E6) else Color(0xFF00BBBA))
                                            .wrapContentWidth(unbounded = true)
                                            .wrapContentHeight(Alignment.CenterVertically)
                                            .zIndex(-1f)

                                    )
                                }
                            }
                            ,
                            colors = NavigationBarItemDefaults
                                .colors(
                                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                        LocalAbsoluteTonalElevation.current
                                    )
                                )
                        )

                        if (index < items.size - 1) {
                            Column (
                                modifier = Modifier.zIndex(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .offset(y = (-2.5).dp)
                                        .fillMaxHeight()
                                        .width(8.dp)
                                        .background(Color(0xFFE6E6E6))
                                        .border((3.5).dp, color = Color(0xFF00BBBA))
                                        .requiredHeight(50.dp)

                                )
                            }
                        }

                    }
                }

            }
        }
        //endregion

    ) { paddingValues ->

        FriendsScreenNavGraph(
            navHostController = navigationState.navHostController,
            requestsScreenContent = { Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFE6E6E6))) {} },
            messagesScreenContent = { Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFE6E6E6))) {} },
            listFriendsScreenContent = { Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFE6E6E6))) {} }
        )

    }
}

