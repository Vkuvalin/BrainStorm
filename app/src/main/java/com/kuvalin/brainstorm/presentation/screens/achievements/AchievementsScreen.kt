package com.kuvalin.brainstorm.presentation.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.globalClasses.AssetImage

@Composable
fun AchievementsScreen(
    paddingParent: PaddingValues
) {

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = (screenWidth/25)

    // Можно сделать в 2 массива фото-подпись
    val achievementsList = mutableListOf(
        mutableListOf("ic_3000 points.png", "3000 баллов"),
        mutableListOf("ic_knowledge_base.png", "База знаний"),
        mutableListOf("ic_accuracy.png", "Не промахнусь"),
        mutableListOf("ic_thinking.png", "Дофига логичный"),
        mutableListOf("ic_calculate.png", "Калькулятор"),
        mutableListOf("ic_deft.png", "Ловкий"),
        mutableListOf("ic_invincible.png", "Непобедимый"),
        mutableListOf("ic_observation.png", "Никто не скроется"),
        mutableListOf("ic_conqueror.png", "Покоритель"),
        mutableListOf("ic_speed.png", "Флеш не ровня"),
        mutableListOf("ic_top_1.png", "Топ 1")
    )

    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingParent.calculateTopPadding())
            .background(color = Color(0xFFE6E6E6))
            .then(Modifier.padding(12.dp))
        ,
        columns = GridCells.Fixed(2) // .Adaptive(minSize = 100.dp)
    ) {
        items(achievementsList.size) { position ->


            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(color = Color(0xFFE6E6E6))
                    .padding(15.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AssetImage(
                    fileName = achievementsList[position][0],
                    modifier = Modifier
                        .fillMaxSize()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = achievementsList[position][1],
                    color = Color(0xFF00ACAB),
                    fontWeight = FontWeight.W500,
                    fontSize = dynamicFontSize.sp
                )
            }

        }
    }

}