package com.kuvalin.brainstorm.presentation.screens.statistics


import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.DynamicFontSize
import com.kuvalin.brainstorm.globalClasses.DynamicSize
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.statistics.StatisticsViewModel
import com.kuvalin.brainstorm.ui.theme.BackgroundAppColor
import com.kuvalin.brainstorm.ui.theme.CyanAppColor


@SuppressLint("MutableCollectionMutableState")
@Composable
fun FriendsStatisticsContent(paddingParent: PaddingValues) {


    /* ############# 🧮 ###################### ПЕРЕМЕННЫЕ #################### 🧮 ############## */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: StatisticsViewModel = viewModel(factory = component.getViewModelFactory())

    // Список друзей
    val friendsList by viewModel.friendList.collectAsState()

    // Динамический размер текста
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = DynamicFontSize(screenWidth, 20f)
    // TODO Придумать позже универсальную функцию, что будет принимать screenWidth и желаемый результат

//    LaunchedEffect(Unit) {
//        val temporaryList = mutableStateListOf<Friend>()
//        viewModel.getFriendList.invoke()?.map { temporaryList.add(it) }
//        friendsList = temporaryList
//    }

    /* ########################################################################################## */


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingParent.calculateTopPadding())
            .background(BackgroundAppColor)
    ){

        if (friendsList.isNotEmpty()){
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = BackgroundAppColor)
                    .then(Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
                ,
                columns = GridCells.Adaptive(100.dp)
            ) {

                items(friendsList.size) { position ->
                    RoundCircleFriendsStatisticIndicator(friendsList[position], dynamicFontSize)
                }

            }
        }else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text("No information about friends.", fontSize = dynamicFontSize)
            }
        }

    }

}


//region RoundCircleIndicator
@Composable
private fun RoundCircleFriendsStatisticIndicator(
    friend: Friend,
    dynamicFontSize: TextUnit
) {

    val wins = friend.warStatistics?.wins ?: 0
    val losses = friend.warStatistics?.losses ?: 0
    val winRate = (wins/(wins+losses).toFloat())

    // Аватар //TODO не забыть подтянуть
    var uriAvatar by remember { mutableStateOf<Uri?>(null) }


    var clickState by remember { mutableStateOf(false) }
    if (clickState){
        FriendStatDialog(friend) { clickState = false}
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                progress = winRate,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFE85B9D))
                    .noRippleClickable { clickState = true }
                ,
                strokeWidth = 20.dp,
                color = Color(0xFF00BAB9),
            )
            Avatar(uriAvatar){ clickState = true }
        }
        Text(
            text = "${friend.name}",
            fontSize = dynamicFontSize,
            style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
        )
    }


}
//endregion
//region Avatar
@Composable
private fun Avatar(
    uriAvatar: Uri?,
    onClick: () -> Unit
){

    Box(
        modifier = Modifier.padding(20.dp),
        contentAlignment = Alignment.Center
    ) {

        Box {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                    .noRippleClickable { onClick() }
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

}
//endregion

//region FriendStatDialog
@Composable
fun FriendStatDialog(
    friend: Friend,
    onClickDismiss: () -> Unit
){

    // Проигрывание музыки
    val context = LocalContext.current

    // Получаем нужные размеры экрана
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val dialogHeight = DynamicSize(screenWidth = screenWidth, desiredSize = (screenWidth*1.3).toFloat())
    val lazyHeight = DynamicSize(screenWidth = screenWidth, desiredSize = (screenWidth*0.9).toFloat())

    val localDensity = LocalDensity.current
    var parentWidth by remember { mutableIntStateOf(0) }



    Dialog(
        onDismissRequest = {
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .height(dialogHeight)
                    .onGloballyPositioned { coordinates ->
                        parentWidth = with(localDensity) {
                            coordinates.size.width.toDp().value.toInt()
                        }
                    }
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
                            MusicPlayer(context = context).playChoiceClick()
                            onClickDismiss()
                        }
                )
                //endregion

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(3))
                        .background(color = BackgroundAppColor)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Column {
                            LabelText(text = "${friend.name}'s statistics", 26.sp, CyanAppColor)
                            LazyColumn(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(3))
                                    .background(color = BackgroundAppColor)
                            ) {
                                item { LabelText(text = "Wars", 20.sp, Color.DarkGray) }
                                item{ WarsContent(PaddingValues(), friend.uid, parentWidth)}
                                item { Spacer(modifier = Modifier.height(10.dp)) }
                                item { Spacer(modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth(0.8f)
                                    .background(Color.LightGray)) }
                                item { Spacer(modifier = Modifier.height(10.dp)) }
                                item { LabelText(text = "Games", 20.sp, Color.DarkGray) }
                                item {
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .height(lazyHeight) )
                                    {
                                        GamesStatisticsContent(PaddingValues(), friend.uid, parentWidth, "friends")
                                    }
                                }

                            }
                        }

                    }

                }
            }
        },
    )
}
//endregion
//region LabelText
@Composable
private fun LabelText(text: String, fontSize: TextUnit, color: Color) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(vertical = 8.dp)
    )
}
//endregion









