package com.kuvalin.brainstorm.presentation.screens.friends

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.screens.mainmenu.DrawingChart
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.GameLevelAColor
import com.kuvalin.brainstorm.ui.theme.GameLevelSColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UserInfoDialog(
    userInfo: UserInfo,
    type: Int, // 1 для request, 2 для friends // TODO подумать потом, как это лучше оформить
    onClickDismiss: () -> Unit
) {

    // Аватарка
    var uriAvatar by remember { mutableStateOf<Uri?>(null) }
    val name = userInfo.name


    // Проигрывание музыки
    val context = LocalContext.current
    val scope = CoroutineScope(Dispatchers.Default)


    // Получаем нужные размеры экрана
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val panelHeight = screenWidth.dp/4


    // Функция добавления/удаления в друзья
    var clickAddDeleteUserButton by remember { mutableStateOf(false) }
    if (clickAddDeleteUserButton){
        AddDeleteUser(userInfo, type) { clickAddDeleteUserButton = false }
    }


    Dialog(
        onDismissRequest = {
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {

                //region Крестик
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .zIndex(2f)
                        .offset(x = (10).dp, y = (20).dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .background(color = Color.White)
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            scope.launch {
                                MusicPlayer(context = context).run {
                                    playChoiceClick()
                                    delay(3000)
                                    release()
                                }
                            }
                            onClickDismiss()
                        }
                )
                //endregion

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height((screenWidth * 1.2).dp) // TODO TODO TODO TODO TODO TODO TODO TODO
                        .clip(RoundedCornerShape(3))
                        .background(color = Color(0xFFE6E6E6))
                ) {
                    //region Name
                    Text(
                        text = name!!, // TODO
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    //endregion
                    //region UserRow
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(panelHeight)
                            .padding(10.dp)
                    ) {
                        //region Аватарка
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            AssetImage(
                                fileName = "ic_profile_russia.png",
                                modifier = Modifier
                                    .zIndex(2f)
                                    .offset(x = (-5).dp, y = (-5).dp)
                                    .size(30.dp)
                                    .align(Alignment.BottomEnd)
                            )

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = Color.White)
                                    .border(
                                        width = 3.dp,
                                        color = GameLevelAColor,
                                        shape = CircleShape
                                    ),
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
                                text = "Bear",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400
                            )

                            Text(
                                text = "World Rank: 12,284th",
                                color = GameLevelSColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            )

                            Text(
                                text = "A-League: 1,574th",
                                color = GameLevelAColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400
                            )
                        }
                        //endregion
                    }
                    //endregion
                    //region График
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(3f)
                            .offset(y = 5.dp),
                    ){
                        Box(modifier = Modifier.scale(0.8f)){ DrawingChart(workMode = 4) }
                    }
                    //endregion
                    //region Кнопки
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    ){
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)){
                            AddDeleteUserButton(type) { clickAddDeleteUserButton = true }
                        }
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            .weight(2f)
                            .padding(4.dp)){ // TODO padding подогнал
                            ButtonChallenge{ }
                        }
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)){
                            GoChat {}
                        }
                    }
                    //endregion
                }
            }
        },
    )

}



// Но я только удаление реализую только
//region AddDeleteUserButton
@Composable
private fun AddDeleteUserButton(
    type: Int,
    onPressButton: () -> Unit
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14))
            .background(color = CyanAppColor)
            .border(
                width = 1.dp,
                color = CyanAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { }
    ){
        Icon(
            bitmap = GetAssetBitmap(fileName = "tab_friends.png"),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .background(CyanAppColor)
                .noRippleClickable { onPressButton() }
        )

        Icon(
            // Если юзера ещё нет, будет add_friend.png
            bitmap = GetAssetBitmap(fileName = if (type == 1) "add_friend.png" else "ic_cancel.png"),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .offset(x = (-5).dp, y = (-5).dp)
                .size(15.dp)
                .clip(CircleShape)
                .background(color = CyanAppColor)
                .align(Alignment.BottomEnd)
        )

    }

}
//endregion
//region ButtonChallenge
@Composable
private fun ButtonChallenge(
    onButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(25))
            .background(color = CyanAppColor)
            .border(width = 1.dp, color = Color(0xFFE6E6E6), shape = RoundedCornerShape(14))
            .noRippleClickable {
                onButtonClick()
            },
    ) {
        Text(
            text = "Challenge",
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            modifier = Modifier
        )
    }
}
//endregion
//region AddDeleteUserButton
@Composable
private fun GoChat(
    onPressButton: () -> Unit
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14))
            .background(color = CyanAppColor)
            .border(
                width = 1.dp,
                color = CyanAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Icon(
            bitmap = GetAssetBitmap(fileName = "message.png"),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .background(CyanAppColor)
                .noRippleClickable {}
        )
    }

}
//endregion


//region AddDeleteUser
@Composable
fun AddDeleteUser(
    userInfo: UserInfo,
    type: Int = 1, // 1 == Add 2 == Delete
    onClickDismiss: () -> Unit
){

    val scope = CoroutineScope(Dispatchers.IO)


    // Динамический размер текста
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = (screenWidth/19) // == 20.sp

    // Проигрывание звуков
    val context = LocalContext.current
    val musicScope = CoroutineScope(Dispatchers.Default)


    // Компонент
    val component = getApplicationComponent()
    val viewModel: FriendsViewModel = viewModel(factory = component.getViewModelFactory())


    Dialog(
        onDismissRequest = { onClickDismiss() },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {

                //region Крестик
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .zIndex(2f)
                        .offset(x = (10).dp, y = (20).dp)
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .background(color = Color.White)
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            musicScope.launch {
                                MusicPlayer(context = context).run {
                                    playChoiceClick()
                                    delay(3000)
                                    release()
                                }
                            }
                            onClickDismiss()
                        }
                )
                //endregion

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(3))
                        .background(color = Color(0xFFE6E6E6))
                        .padding(30.dp)
                ) {
                    //region Name
                    Text(
                        text = if (type == 1) "Do you want to add a user?" else "Delete a friend?",
                        color = Color.Black,
                        fontSize = dynamicFontSize.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    //endregion

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        YesNoButton(type = "yes"){
                            if (type == 1){
                                // Принимаем запрос дружбы
                                scope.launch {
                                    viewModel.addFriend(userInfo)
                                    viewModel.updateUserRequestFB.invoke(userInfo.uid,true)
                                    onClickDismiss()
                                }
                            }else {
                                // Удаляем друга
                            }
                        }
                        Spacer(modifier = Modifier.width(30.dp))
                        YesNoButton(type = "no"){
                            if (type == 1){
                                scope.launch {
                                    // Отказываемся от дружбы
                                    viewModel.updateUserRequestFB.invoke(userInfo.uid,false)
                                    onClickDismiss()
                                }
                            }else {
                                // Не удаляем друга, а просто закрываем
                                onClickDismiss()
                            }
                        }
                    }
                }
            }
        },
    )
}
//endregion

//region YesNoButton
@Composable
private fun YesNoButton(
    type: String, // TODO
    onPressButton: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(14))
            .background(color = if (type == "yes") CyanAppColor else PinkAppColor)
            .border(
                width = 1.dp,
                color = if (type == "yes") CyanAppColor else PinkAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = if (type == "yes") " Yes " else " No ",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )
    }

}
//endregion
