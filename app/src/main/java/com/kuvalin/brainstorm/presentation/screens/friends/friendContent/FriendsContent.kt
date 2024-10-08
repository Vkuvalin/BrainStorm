package com.kuvalin.brainstorm.presentation.screens.friends.friendContent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.presentation.screens.friends.AddFriendsButtonContent
import com.kuvalin.brainstorm.presentation.screens.friends.UserInfoDialog
import com.kuvalin.brainstorm.presentation.screens.friends.UserRequestOrFriendPanel
import com.kuvalin.brainstorm.presentation.viewmodels.friends.FriendsContentViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor

@Composable
fun FriendsContent(
    paddingValues: PaddingValues
){

    //region ############# 🧮 ################## ПЕРЕМЕННЫЕ ################## 🧮 ############## */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: FriendsContentViewModel = viewModel(factory = component.getViewModelFactory())


    // Настройки ширины кнопки
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicRowWidth = (screenWidth/1.5).toInt()


    // Обработка нажатие по кнопке "добавить друга"
    var onClickButtonState by remember { mutableStateOf(false) }
    if (onClickButtonState){ AddFriendsButtonContent(){ onClickButtonState = false } }

    // Анимация мозга
    val animBrainLoadState by GlobalStates.animBrainLoadState.collectAsState()

    // Список друзей
    val listFriendsUserInfo by viewModel.listFriendsUserInfo.collectAsState()

    // Обработка нажатия по кнопке "подробная информация о пользователе (аватар)"
    val clickUserRequestPanel by viewModel.clickUserRequestPanel.collectAsState()
    val dynamicUserInfo by viewModel.dynamicUserInfo.collectAsState()
    if (clickUserRequestPanel){
        UserInfoDialog(dynamicUserInfo, type = 2) { viewModel.onUserRequestPanelDismiss() }
    }

    //endregion ################################################################################# */

    //region ############# 🟢 ############### ОСНОВНЫЕ ФУНКЦИИ ################# 🟢 ############# */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
            .background(color = BackgroundAppColor)
    ) {

        if (listFriendsUserInfo.isNotEmpty() && !animBrainLoadState){
            //region Список друзей
            LazyColumn( modifier = Modifier.fillMaxSize()) {
                items(
                    items = listFriendsUserInfo,
                    key = { it.uid } // Используем уникальный идентификатор пользователя как ключ
                ) { userInfo ->
                    UserRequestOrFriendPanel(userInfo = userInfo) {
                        viewModel.onUserRequestPanelClick(userInfo)
                    }
                }
            }
            //endregion
        }else {
            if (!animBrainLoadState){
                AddFriendButton(screenWidth, dynamicRowWidth){ onClickButtonState = true }
            }
        }

    }
    //endregion ################################################################################# */

}

//region ############# 🟡 ############ ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ############ 🟡 ############## */
//region Кнопка добавления
@Composable
private fun AddFriendButton(
    screenWidth: Int,
    dynamicRowWidth: Int,
    onClickButtonState: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Challenge your friends!",
            fontSize = DynamicFontSize(screenWidth, 20f)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(dynamicRowWidth.dp)
                .clip(RoundedCornerShape(14))
                .background(color = PinkAppColor)
                .border(
                    width = 1.dp,
                    color = PinkAppColor,
                    shape = RoundedCornerShape(14)
                )
                .noRippleClickable {
                    onClickButtonState()
                }
        ) {
            Text(
                text = "Add Friends",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
        }
    }
}
//endregion
//endregion ################################################################################# */




