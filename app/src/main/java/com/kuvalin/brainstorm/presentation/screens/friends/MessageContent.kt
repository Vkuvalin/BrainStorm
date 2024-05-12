package com.kuvalin.brainstorm.presentation.screens.friends

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.kuvalin.brainstorm.domain.entity.UserInfo

@Composable
fun MessageContent(
    paddingParent: PaddingValues
){

    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    val context = LocalContext.current

    // Динамический размер текста
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = (screenWidth/19) // == 20.sp
    // TODO Придумать позже универсальную функцию, что будет принимать screenWidth и желаемый результат


    // Список пользователей
    val listUsers = listOf( // TODO
        UserInfo("2222", name = "Liza"),
        UserInfo("3333", name = "Karen")
    )


    // Функция дополнительной информации о юзере
    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(UserInfo(uid = "123")) }
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 2) { clickUserRequestPanel = false }
    }


    /* ########################################################################################## */

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))
            .padding(top = paddingParent.calculateTopPadding())
    ) {

        if (listUsers.size != 0){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(listUsers.size) {position ->
                    UserMessagePanel(
                        userInfo = listUsers[position],
                        onPressAvatar = {
                            dynamicUserInfo = listUsers[position]
                            clickUserRequestPanel = true
                        },
                        onPressRightPart = {
                            Toast.makeText(context, "Переход в чат", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text("No new requests have been received.", fontSize = dynamicFontSize.sp)
            }
        }


    }
}