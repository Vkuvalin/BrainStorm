    package com.kuvalin.brainstorm.presentation.screens.friends.messageContent

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer

    @Composable
fun UserMessagePanel(
    userInfo: UserInfo,
    onPressAvatar: () -> Unit,
    onPressRightPart: () -> Unit
) {

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */
    val displayName by remember { derivedStateOf { userInfo.name ?: "Unknown" } }

//    val avatar = userInfo.avatar - Будет заглушкой
//    val country = userInfo.country - Будет заглушкой
//    val grade = // Тут другой класс
//    val lastLogin = // И тут другой класс

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val panelHeight = remember { screenWidth/4 }

    // Для проигрывания звуков
    val context = LocalContext.current

    // Аватар
    var uriAvatar by remember { mutableStateOf<Uri?>(null) }

    // Заглушка ласт сообщения
    //endregion ################################################################################# */



    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(panelHeight)
            .padding(10.dp)
    ) {

        //region Аватарка
        Box(
            modifier = Modifier
                .weight(1f)
                .noRippleClickable {
                    MusicPlayer(context = context).playChoiceClick()
                    onPressAvatar()
                }
            ,
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
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxHeight()
                .weight(2.5f)
                .padding(start = 10.dp)
                .noRippleClickable { onPressRightPart() }
        ) {
            Text(
                text = displayName,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400
            )

            Text(
                text = "Последнее сообщение",
                maxLines = 1,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        }
        //endregion

    }
    //endregion ################################################################################# */


}



