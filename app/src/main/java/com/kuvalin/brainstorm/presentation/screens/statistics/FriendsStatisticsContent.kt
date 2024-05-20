package com.kuvalin.brainstorm.presentation.screens.statistics


import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.presentation.screens.friends.UserInfoDialog
import com.kuvalin.brainstorm.presentation.viewmodels.StatisticsViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun FriendsStatisticsContent(paddingParent: PaddingValues) {


    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: StatisticsViewModel = viewModel(factory = component.getViewModelFactory())

    // Список друзей
    var friendsList by remember { mutableStateOf(mutableStateListOf<Friend>()) }

    // Динамический размер текста
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val dynamicFontSize = (screenWidth/19) // == 20.sp
    // TODO Придумать позже универсальную функцию, что будет принимать screenWidth и желаемый результат

    LaunchedEffect(Unit) {
        val temporaryList = mutableStateListOf<Friend>()
        viewModel.getFriendsList.invoke()?.map { temporaryList.add(it) }
        friendsList = temporaryList
    }

    /* ########################################################################################## */


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingParent.calculateTopPadding())
            .background(color = Color(0xFFE6E6E6))
    ){

        if (friendsList.size != 0){
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFE6E6E6))
                    .then(Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
                ,
                columns = GridCells.Adaptive(100.dp)
            ) {

                items(friendsList.size) { position ->
                    RoundCircleFriendsStatisticIndicator(friendsList[position])
                }

            }
        }else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text("No information about friends.", fontSize = dynamicFontSize.sp)
            }
        }

    }

}


//region RoundCircleIndicator
@Composable
private fun RoundCircleFriendsStatisticIndicator(
    friend: Friend,
) {

    val wins = friend.warStatistics?.wins ?: 0
    val losses = friend.warStatistics?.losses ?: 0
    val winRate = (wins/(wins+losses).toFloat())

    // Аватар //TODO не забыть подтянуть
    var uriAvatar by remember { mutableStateOf<Uri?>(null) }


    var clickState by remember { mutableStateOf(false) }
    if (clickState){
        UserInfoDialog(
            userInfo = UserInfo(friend.uid, friend.name, friend.email, friend.avatar, friend.country),
            type = 2
        ) { clickState = false}
    }

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













