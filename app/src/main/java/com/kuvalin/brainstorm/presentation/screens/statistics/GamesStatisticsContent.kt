package com.kuvalin.brainstorm.presentation.screens.statistics

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.presentation.viewmodels.statistics.GamesStatisticsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor


@SuppressLint("MutableCollectionMutableState")
@Composable
fun GamesStatisticsContent(
    paddingParent: PaddingValues,
    uid: String = "",
    parentWidth: Int? = null,
    statisticsType: StatisticsType = StatisticsType.GAMES
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */

    // ViewModel
    val viewModel: GamesStatisticsViewModel = viewModel(factory = getApplicationComponent().getViewModelFactory())
    LaunchedEffect(Unit) { viewModel.getListGamesStatistics(uid) }

    // Все игры
    val items = GamesNavigationItem::class.sealedSubclasses.mapNotNull { it.objectInstance }

    // endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingParent.calculateTopPadding())
                .background(color = BackgroundAppColor)
                .then(Modifier.padding(horizontal = 10.dp, vertical = 10.dp)),
            columns = GridCells.Fixed(2)
        ) {
            items(items.size) { position ->
                val item = items[position]
                val gameStatistic = viewModel.getGameStatistic(item.sectionName)

                GamesStatisticsItem(
                    gamesInfo = item,
                    parentWidth = parentWidth,
                    statisticsType = statisticsType,
                    gameStatistic = gameStatistic
                )
            }
        }
    }
    //endregion ################################################################################## */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region GamesStatisticsItem
@Composable
fun GamesStatisticsItem(
    gamesInfo: GamesNavigationItem,
    parentWidth: Int?,
    statisticsType: StatisticsType,
    gameStatistic: GameStatistic?
) {
    val screenWidth = parentWidth ?: LocalConfiguration.current.screenWidthDp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Color(0xFFF3F3F3))
            .padding(4.dp)
    ) {
        AssetImage(
            fileName = gamesInfo.miniatureGameImage,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = Color.White)
                .padding(2.dp)
        )
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = gamesInfo.sectionName,
                fontSize = DynamicFontSize(screenWidth,
                    if (statisticsType == StatisticsType.GAMES) 12f else 9f),
                color = Color.DarkGray
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${gameStatistic?.maxGameScore ?: 0}",
                    fontSize = DynamicFontSize(screenWidth,
                        if (statisticsType == StatisticsType.GAMES) 16f else 12f),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Avg. ${gameStatistic?.avgGameScore ?: 0}",
                    fontSize = DynamicFontSize(screenWidth,
                        if (statisticsType == StatisticsType.GAMES) 12f else 9f),
                    color = Color.Gray
                )
            }
        }
    }
}
//endregion

// Enum class для типа статистики
enum class StatisticsType { GAMES, FRIENDS }
//endregion ################################################################################## */
