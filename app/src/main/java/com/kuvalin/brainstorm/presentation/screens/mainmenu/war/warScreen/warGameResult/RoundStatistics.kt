package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.warGameResult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen.GameProgressIndicator

@Composable
fun RoundStatistics(
    visualType: Int = 0,
    roundNumber: String,
    gameName: String = "",
    cyanUserScore: Int,
    pinkUserScore: Int
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (visualType == 1) {
                Text(
                    text = "SCORE",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = "$cyanUserScore",
                color = Color.Black,
                fontSize = if (visualType != 2) 18.sp else 22.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$roundNumber",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                )
                Text(
                    text = "$gameName",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Row(
                modifier = Modifier
            ) {
                GameProgressIndicator(
                    ratio = ((cyanUserScore - pinkUserScore).toFloat() / 2000),
                    height = if (visualType == 2) 30 else 20
                )
            }

        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (visualType == 1) {
                Text(
                    text = "SCORE",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Text(
                text = "$pinkUserScore",
                color = Color.Black,
                fontSize = if (visualType != 2) 18.sp else 22.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            )
        }

    }
}





