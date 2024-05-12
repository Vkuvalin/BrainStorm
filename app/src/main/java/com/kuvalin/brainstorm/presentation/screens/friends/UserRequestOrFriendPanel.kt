package com.kuvalin.brainstorm.presentation.screens.friends

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import kotlin.random.Random

@Composable
fun UserRequestOrFriendPanel(
    userInfo: UserInfo,
    onPressPanel: () -> Unit
) {

    val name = userInfo.name

//    val avatar = userInfo.avatar - Будет заглушкой
//    val country = userInfo.country - Будет заглушкой
//    val grade = // Тут другой класс
//    val lastLogin = // И тут другой класс

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val panelHeight = screenWidth/4

    // Аватар
    var uriAvatar by remember { mutableStateOf<Uri?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(panelHeight)
            .padding(10.dp)
            .noRippleClickable {
                onPressPanel()
            }
    ) {

        //region Аватарка
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {

            Box {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape),
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
        //endregion
        //region UserInfo
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
                .padding(start = 10.dp)
        ) {
            Text(
                text = name!!, // TODO
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400
            )

            Text(
                text = "Cat",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )

            Text(
                text = "Last login: ${Random.nextInt(1,59)} min ago", // TODO
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        }
        //endregion
        //region Флаг
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f)
        ) {
            AssetImage(
                fileName = "ic_profile_russia.png",
                modifier = Modifier.size(40.dp)
            )
        }
        //endregion

    }

}



