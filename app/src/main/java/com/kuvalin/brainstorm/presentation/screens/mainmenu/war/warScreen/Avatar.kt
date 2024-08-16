package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.warScreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.globalClasses.AssetImage

@Composable
fun Avatar(
    uriAvatar: Uri?,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = Color.White)
                .border(width = 7.dp, color = color, shape = CircleShape)
            ,
            contentAlignment = Alignment.Center
        ) {
            if (uriAvatar == null) {
                AssetImage(fileName = "av_user.png")
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = uriAvatar),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }
    }
}