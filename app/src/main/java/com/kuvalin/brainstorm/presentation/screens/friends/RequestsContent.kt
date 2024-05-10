package com.kuvalin.brainstorm.presentation.screens.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kuvalin.brainstorm.domain.entity.UserInfo

@Preview
@Composable
fun RequestsContent(){

    val listUsers = listOf(
        UserInfo("1111", name = "Vitaly"),
        UserInfo("2222", name = "Liza"),
        UserInfo("3333", name = "Karen"),
        UserInfo("4444", name = "Evgeny")
    )

    var clickUserRequestPanel by remember { mutableStateOf(false) }
    var dynamicUserInfo by remember { mutableStateOf(UserInfo(uid = "123")) }
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 1) {
            clickUserRequestPanel = false
        }
    }

    val userInfoDialog: @Composable (userInfo: UserInfo) -> Unit = {userInfo ->

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
//                .padding(top = paddingParent.calculateTopPadding())
                .background(color = Color(0xFFE6E6E6)),
        ) {
            items(listUsers.size) {position ->
                UserRequestPanel(userInfo = listUsers[position]) {
                    dynamicUserInfo = listUsers[position]
                    clickUserRequestPanel = true
                }
            }
        }

    }
}