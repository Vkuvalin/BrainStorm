package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor


@Composable
fun TopWarBar(
    topBarHeight: Int,
    uriAvatar: Uri?,
    scopeCyanPlayer: Int,
    timer: Int,
    scopePinkPlayer: Int,
    ratio: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight.dp)
            .background(color = Color(0xFF373737))
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 7.dp)
        ) {
            Avatar(uriAvatar = uriAvatar, color = CyanAppColor)
            Scope(scope = scopeCyanPlayer, style = "left")

            Time(time = timer)

            Scope(scope = scopePinkPlayer, style = "right")
            Avatar(uriAvatar = uriAvatar, color = PinkAppColor)
        }

    }

    GameProgressIndicator(ratio = ratio)
}

//region Time
@Composable
private fun Time(
    time: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            text = "TIME",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2f),
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
        Text(
            text = if (time < 10) "0$time" else "$time",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(5f),
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }
}
//endregion
//region Scope
@Composable
private fun Scope(
    scope: Int,
    style: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxHeight()
            .let {
                when (style) {
                    "left" -> it.padding(bottom = 5.dp, end = 20.dp)
                    "right" -> it.padding(bottom = 5.dp, start = 20.dp)
                    else -> it.padding(bottom = 5.dp)
                }
            }
    ) {
        Text(
            text = "SCORE",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
        )
        Text(
            text = "$scope",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}
//endregion


