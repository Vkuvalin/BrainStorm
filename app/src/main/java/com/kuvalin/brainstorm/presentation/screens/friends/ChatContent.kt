package com.kuvalin.brainstorm.presentation.screens.friends

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.navigation.staticsClasses.animationStates.AnimationTopBarState
import com.kuvalin.brainstorm.presentation.viewmodels.FriendsViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ChatContent(
    userName: String,
    chatId: String,
    onBackButtonClick: () -> Unit
){
    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(310){ clickNavigation = false } }

    LaunchedEffect(Unit) { GlobalStates.putScreenState("runGameScreenState", true) }
    BackHandler {
        clickNavigation = true
        onBackButtonClick()
        GlobalStates.putScreenState("runGameScreenState", false)
    }


    /* ####################################### ПЕРЕМЕННЫЕ ####################################### */

    // Компонент
    val component = getApplicationComponent()
    val viewModel: FriendsViewModel = viewModel(factory = component.getViewModelFactory())

    var userUid by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getListMessages(chatId)
        userUid = viewModel.getUserUid.invoke()
    }

    val listMessage by viewModel.messages.collectAsState()



    // TODO - почитать
//    val listState = rememberLazyListState()
//    val listState = rememberLazyListState(initialFirstVisibleItemIndex = maxOf(listMessage.size - 10, listMessage.size - 1))


    /* ########################################################################################## */


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ChatTopBar(userName = userName) {
            GlobalStates.putScreenState("runGameScreenState", false)
            onBackButtonClick()
        }
//        LaunchedEffect(listMessage.size) { // TODO - почитать
//            listState.animateScrollToItem(listMessage.size)
//        }

        LazyColumn(
//            state = listState, // TODO - почитать
            reverseLayout = true,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE6E6E6))
                .weight(1f)
        ) {

            items(listMessage.size) {position ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (listMessage[position].senderUid == userUid) Arrangement.End else Arrangement.Start
                ) {
                    Row(
                        horizontalArrangement = if (listMessage[position].senderUid == userUid) Arrangement.End else Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(horizontal = 10.dp, vertical = 8.dp)

                    ) {
                        Text(
                            text = listMessage[position].text,
                            modifier = Modifier
                                .clip(RoundedCornerShape(size = 10.dp))
                                .background(if (listMessage[position].senderUid == userUid) CyanAppColor else PinkAppColor)
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }

            }

        }
        CustomTextFieldChatContent(placeholder = "Enter your message"){message ->
            if (message.isNotEmpty()){
                CoroutineScope(Dispatchers.Default).launch {
                    viewModel.addMessageToFB.invoke(
                        message = message,
                        chatId = chatId
                    )
                }
            }
        }
    }
}



//region CustomTextField
@Composable
private fun CustomTextFieldChatContent(
    placeholder: String,
    inputText: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
            isFocused = true
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        ),
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .background(Color(0xFFE6E6E6))
            .padding(bottom = 5.dp)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 40.dp)
                    .clip(RoundedCornerShape(size = 10.dp))
                    .background(color = Color.White)
                    .border(
                        width = 2.dp,
                        color = Color(0xFFAAE9E6),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding
            ) {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray
                    )
                }
                innerTextField()
                Image(
                    bitmap = GetAssetBitmap(fileName = "tab_arrow_button.png"),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.DarkGray),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .rotate(180f)
                        .width(15.dp)
                        .noRippleClickable {
                            inputText(value)
                            value = ""
                        }
                )
            }
        }
    )
}
//endregion


/*
Image(
    bitmap = GetAssetBitmap(fileName = "tab_arrow_button.png"),
    contentDescription = null,
    colorFilter = ColorFilter.tint(Color.DarkGray),
    modifier = Modifier
        .width(50.dp)
        .padding(vertical = 10.dp, horizontal = 10.dp)
        .noRippleClickable { onBackButtonClick() }
)
*/