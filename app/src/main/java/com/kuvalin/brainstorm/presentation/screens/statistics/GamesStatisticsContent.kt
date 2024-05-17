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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.navigation.games.GamesNavigationItem
import com.kuvalin.brainstorm.presentation.viewmodels.StatisticsViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun GamesStatisticsContent(paddingParent: PaddingValues) {

    // Компонент
    val component = getApplicationComponent()
    val viewModel: StatisticsViewModel = viewModel(factory = component.getViewModelFactory())

    var gamesStatistics by remember { mutableStateOf(mutableStateListOf<GameStatistic>()) }

    // Все игры
    val items = GamesNavigationItem::class.sealedSubclasses.mapNotNull { it.objectInstance }

    LaunchedEffect(Unit) {
        /* Данная конструкция решает сразу 2 задачи:
        1. Нельзя сразу почему-то засунуть полученный список gamesStatistics;
        2. Таким образом будет произведена лишь 1 рекомпозиция;
        // TODO распространить на другие места, с подобной проблемой
        */
        val temporaryList = mutableStateListOf<GameStatistic>()
        viewModel.getListGamesStatistics.invoke().map { temporaryList.add(it) }
        gamesStatistics = temporaryList
    }


    Box(modifier = Modifier.fillMaxSize()
    ){
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingParent.calculateTopPadding())
                .background(color = Color(0xFFE6E6E6))
                .then(Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
            ,
            columns = GridCells.Fixed(2)
        ) {
            items(items.size) { position ->
                val item = items[position]
                var find = false

                for (game in gamesStatistics) {
                    if (game.gameName == item.sectionName) {
                        GamesStatisticsItem(gamesInfo = item, gameStatistic = game)
                        find = true
                        break
                    }
                }
                if (!find) { GamesStatisticsItem(gamesInfo = item, gameStatistic = null) }
            }
        }
    }

}



//region GamesStatisticsItem
@Composable
fun GamesStatisticsItem(
    gamesInfo: GamesNavigationItem,
    gameStatistic: GameStatistic?
) {
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
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "${gameStatistic?.maxGameScore ?: 0}", fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Avg. ${gameStatistic?.avgGameScore ?: 0}", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
//endregion




