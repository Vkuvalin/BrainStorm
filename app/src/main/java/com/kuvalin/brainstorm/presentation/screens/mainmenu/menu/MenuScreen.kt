package com.kuvalin.brainstorm.presentation.screens.mainmenu.menu

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.getApplicationComponent
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.Companion.UNDEFINED_ID
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.globalClasses.presentation.GlobalStates
import com.kuvalin.brainstorm.globalClasses.presentation.MusicPlayer
import com.kuvalin.brainstorm.presentation.viewmodels.MainMenuViewModel
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
import com.kuvalin.brainstorm.ui.theme.LinearTrackColor
import com.kuvalin.brainstorm.ui.theme.PinkAppColor
import com.kuvalin.brainstorm.ui.theme.checkedBorderColor
import com.kuvalin.brainstorm.ui.theme.checkedIconColor
import com.kuvalin.brainstorm.ui.theme.checkedThumbColor
import com.kuvalin.brainstorm.ui.theme.checkedTrackColor
import com.kuvalin.brainstorm.ui.theme.disabledCheckedBorderColor
import com.kuvalin.brainstorm.ui.theme.disabledCheckedIconColor
import com.kuvalin.brainstorm.ui.theme.disabledCheckedThumbColor
import com.kuvalin.brainstorm.ui.theme.disabledCheckedTrackColor
import com.kuvalin.brainstorm.ui.theme.disabledUncheckedBorderColor
import com.kuvalin.brainstorm.ui.theme.disabledUncheckedIconColor
import com.kuvalin.brainstorm.ui.theme.disabledUncheckedThumbColor
import com.kuvalin.brainstorm.ui.theme.disabledUncheckedTrackColor
import com.kuvalin.brainstorm.ui.theme.uncheckedBorderColor
import com.kuvalin.brainstorm.ui.theme.uncheckedIconColor
import com.kuvalin.brainstorm.ui.theme.uncheckedThumbColor
import com.kuvalin.brainstorm.ui.theme.uncheckedTrackColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MenuScreen(){

    var clickNavigation by remember { mutableStateOf(false) }
    if (clickNavigation){ GlobalStates.AnimLoadState(350){ clickNavigation = false } }
    BackHandler { clickNavigation = true }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    //Buttons
    val dynamicRowWidth = (screenWidth/1.5).toInt()

    //region Modifiers
    val modifierForCloseButton = Modifier
        .offset(x = (10).dp, y = 5.dp)
        .size(30.dp)
        .clip(CircleShape)
        .border(width = 2.dp, color = Color.White, shape = CircleShape)
        .background(color = Color.White)

    val modifierForCloseButton2 = Modifier
        .offset(x = (10).dp, y = (-10).dp)
        .size(30.dp)
        .clip(CircleShape)
        .border(width = 2.dp, color = Color.White, shape = CircleShape)
        .background(color = Color.White)
    //endregion

    var announcementButtonState by remember { mutableStateOf(false) }
    var settingsButtonState by remember { mutableStateOf(false) }
    var informationButtonState by remember { mutableStateOf(false) }
    var contactsButtonState by remember { mutableStateOf(false) }
    var accountButtonState by remember { mutableStateOf(false) }


    var clickButtonState by remember { mutableStateOf(false) }
    clickButtonState = announcementButtonState || settingsButtonState || informationButtonState
            || contactsButtonState || accountButtonState

    //region Кнопки
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            MenuText(
                clickButtonState = clickButtonState,
                text = "Announcement",
                backgroundColor = Color(0xFF439AD3),
                width = dynamicRowWidth
            ){
                announcementButtonState = true
            }

            Spacer(modifier = Modifier.height(12.dp))
            MenuText(
                clickButtonState = clickButtonState,
                text = "Settings",
                backgroundColor = Color(0xFFFE5FA6),
                width = dynamicRowWidth
            ){
                settingsButtonState = true
            }

            Spacer(modifier = Modifier.height(12.dp))
            MenuText(
                clickButtonState = clickButtonState,
                text = "Information",
                backgroundColor = Color(0xFFFFAA01),
                width = dynamicRowWidth
            ){
//                informationButtonState = true
                announcementButtonState = true
            }

            Spacer(modifier = Modifier.height(12.dp))
            MenuText(
                clickButtonState = clickButtonState,
                text = "Contact Us",
                backgroundColor = Color(0xFF595959),
                width = dynamicRowWidth
            ) {
//                contactsButtonState = true
                settingsButtonState = true
            }

            Spacer(modifier = Modifier.height(12.dp))
            MenuText(
                clickButtonState = clickButtonState,
                text = "Account",
                backgroundColor = Color(0xFF009688),
                width = dynamicRowWidth
            ) {
                accountButtonState = true
            }
        }

        if (announcementButtonState) {
            AnnouncementContent(modifierForCloseButton) { announcementButtonState = false}
        }
        if (settingsButtonState) {
            SettingsContent(modifierForCloseButton2) { settingsButtonState = false }
        }
        if (accountButtonState) {
            AccountContent() { accountButtonState = false }
        }

    }
    //endregion
}

//region MenuText
@Composable
private fun MenuText(
    clickButtonState: Boolean,
    text: String,
    backgroundColor: Color,
    width: Int,
    onPressButton: () -> Unit
) {

    // Для проигрывания звуков
    val context = LocalContext.current
    val scope = CoroutineScope(Dispatchers.Default)

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(width.dp)
            .clip(RoundedCornerShape(14))
            .background(color = if (clickButtonState) Color(0xFFE6E6E6) else backgroundColor)
            .border(
                width = 1.dp,
                color = if (clickButtonState) Color(0xFFE6E6E6) else backgroundColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable {
                scope.launch {
                    MusicPlayer(context).playChoiceClick()
                }
                onPressButton()
            }
    ){
        Text(
            text = text,
            fontSize = 24.sp,
            color = if (clickButtonState) Color(0xFFE6E6E6) else Color(0xFFFFFFFF),
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
    }

}
//endregion

//region AnnouncementContent
@Composable
fun AnnouncementContent(
    customModifier: Modifier,
    onClickDismiss: () -> Unit
){

    // Для проигрывания звуков
    val context = LocalContext.current
    val scope = CoroutineScope(Dispatchers.Default)

    Dialog(
        onDismissRequest = {
            scope.launch {
                MusicPlayer(context).playChoiceClick()
            }
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {

                    AssetImage(
                        fileName = "ic_cancel.png",
                        modifier = customModifier
                            .align(alignment = Alignment.End)
                            .noRippleClickable {
                                scope.launch {
                                    MusicPlayer(context).playChoiceClick()
                                }
                                onClickDismiss()
                            }
                    )

                    LabelText("Announcement")
                    Spacer(modifier = Modifier.height(10.dp))
                    AssetImage(
                        fileName = "im_announcement.jpg",
                        modifier = Modifier
                            .clip(RoundedCornerShape(5))
                            .border(
                                width = 0.01.dp,
                                color = CyanAppColor,
                                shape = RoundedCornerShape(5)
                            )
                    )
                }
        },
    )
}
//endregion
//region SettingsContent
@Composable
fun SettingsContent(
    customModifier: Modifier,
    onClickDismiss: () -> Unit
) {

    // Для проигрывания звуков
    val context = LocalContext.current
    val scope = CoroutineScope(Dispatchers.Default)

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenWidthDp

    var musicState by remember { mutableStateOf(true) }
    var seState by remember { mutableStateOf(false) }
    var vibrationState by remember { mutableStateOf(true) }

    // Settings
    val databaseScope = CoroutineScope(Dispatchers.IO)
    val component = getApplicationComponent()
    val viewModel: MainMenuViewModel = viewModel(factory = component.getViewModelFactory())

    LaunchedEffect(Unit) {
        databaseScope.launch {
            val appSettings = viewModel.getAppSettings.invoke()
            musicState = appSettings.musicState
            vibrationState = appSettings.vibrateState
        }
    }


    Dialog(
        onDismissRequest = {
            scope.launch {
                MusicPlayer(context).playChoiceClick()
            }
            onClickDismiss()
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .background(color = Color(0xFFE6E6E6))
                    .height(screenHeight.dp)
            ) {

                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = customModifier
                        .align(alignment = Alignment.End)
                        .noRippleClickable {
                            scope.launch {
                                MusicPlayer(context).playChoiceClick()
                            }
                            onClickDismiss()
                        }
                )

                LabelText("Settings")
                Spacer(modifier = Modifier.height(10.dp))

                SettingItem("Music", musicState){
                    musicState = !musicState
                    databaseScope.launch {
                        viewModel.addAppSettings.invoke(
                            AppSettings(
                                id = UNDEFINED_ID,
                                musicState = musicState,
                                vibrateState = vibrationState
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                SettingItem("SE", seState){ seState = !seState } // TODO убрать или придумать значение
                Spacer(modifier = Modifier.height(10.dp))

                SettingItem("Vibrate", vibrationState){
                    vibrationState = !vibrationState
                    databaseScope.launch {
                        viewModel.addAppSettings.invoke(
                            AppSettings(
                                id = UNDEFINED_ID,
                                musicState = musicState,
                                vibrateState = vibrationState
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .width(230.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Language", color = Color.Black, fontSize = 20.sp)
                    Text(text = "English", color = Color.Black, fontSize = 20.sp)
                }

            }
        },
    )
}


//region SettingItem
@Composable
private fun SettingItem(
    settingName: String,
    checkedState: Boolean,
    onClickSwitcher: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(230.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = settingName, color = Color.Black, fontSize = 20.sp)
        SwitchButton(checkedState) { onClickSwitcher() }
    }
}
//endregion
//region SwitchButton
@Composable
private fun SwitchButton(
    checkedState: Boolean = true,
    onCheckedChange: () -> Unit
) {
    Switch(
        modifier = Modifier.scale(1.2f),
        enabled = true,
        checked = checkedState,
        colors = SwitchDefaults.colors(
            disabledUncheckedBorderColor = disabledUncheckedBorderColor,
            disabledUncheckedThumbColor = disabledUncheckedThumbColor,
            disabledUncheckedTrackColor = disabledUncheckedTrackColor,
            disabledUncheckedIconColor = disabledUncheckedIconColor,
            disabledCheckedBorderColor = disabledCheckedBorderColor,
            disabledCheckedTrackColor = disabledCheckedTrackColor,
            disabledCheckedThumbColor = disabledCheckedThumbColor,
            disabledCheckedIconColor = disabledCheckedIconColor,
            uncheckedBorderColor = uncheckedBorderColor,
            uncheckedThumbColor = uncheckedThumbColor,
            uncheckedTrackColor = uncheckedTrackColor,
            uncheckedIconColor = uncheckedIconColor,
            checkedBorderColor = checkedBorderColor,
            checkedTrackColor = checkedTrackColor,
            checkedThumbColor = checkedThumbColor,
            checkedIconColor = checkedIconColor
        ),
        onCheckedChange = { mChecked ->
            onCheckedChange()
        }
    )
}
//endregion


//endregion
//region AccountContent
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AccountContent(
    onClickDismiss: () -> Unit
){
    // Component
    val component = getApplicationComponent()
    val viewModel: MainMenuViewModel = viewModel(factory = component.getViewModelFactory())

    // Для проигрывания звуков
    val context = LocalContext.current
    val scope = CoroutineScope(Dispatchers.Default)


    // Authorization -> Firebase
    val auth = Firebase.auth
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    // TODO Затем добавить в GlobalStates (чтобы подхватывать в других местах)
    var authState by remember { mutableStateOf(false) }
    if (authState){
        LaunchedEffect(Unit) {
            var count = 0
            while (userName.isEmpty() && count <= 20) {
                count++
                viewModel.getUserInfo.invoke()?.name?.let { userName = it }
                delay(100)
            }
        }
    }


    // TODO Сделать поле ввода пароля секретным/скрытным


    Dialog(
        onDismissRequest = {
            scope.launch {
                MusicPlayer(context = context).run {
                    playChoiceClick()
                    delay(3000)
                    release()
                }
            }
            onClickDismiss()
        },
        content = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = Color(0xE6E6E6E6))
            ) {
                //region Крестик
                AssetImage(
                    fileName = "ic_cancel.png",
                    modifier = Modifier
                        .offset(x = (10).dp, y = (-10).dp)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    LabelText("Account")

                    if (authState) {
                        Text(
                            text = "Рады, что вы с нами $userName",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.offset(y = (-10).dp)
                        )
                    }else {
                        Text(
                            text = "Зарегистрируйтесь для синхронизации с другими устройствами",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.offset(y = (-10).dp)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {

                        // Проверяем авторизован ли уже пользователь
                        scope.launch { // TODO проверить на 2ю рекомпозицию
                            authState = viewModel.authorizationCheck.invoke()
                        }

                        // Если авторизован, то убираем инпуты
                        //region CustomTextFieldFiendsScreen - Поля ввода
                        if (!authState) {
                            CustomTextFieldFiendsScreen(placeholder = "Enter your email"){email ->
                                userEmail = email
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            CustomTextFieldFiendsScreen(placeholder = "Enter your password"){pass ->
                                userPassword = pass
                            }
                        }
                        //endregion

                        Spacer(modifier = Modifier.height(20.dp))

                        Row( // TODO не забыть сделать динамичный размер для элементов
                            horizontalArrangement = if(!authState) Arrangement.SpaceBetween
                            else Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            if (!authState){
                                //region RegistrationButton
                                RegistrationButton(){

                                    if (userEmail.isNotEmpty() && userPassword.isNotEmpty()){

                                        scope.launch {
                                            val singUpResult = viewModel.singUp.invoke(userEmail, userPassword)

                                            if(singUpResult.first){
                                                // Сообщение об успешной регистрации
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, singUpResult.second, Toast.LENGTH_LONG).show()
                                                }

                                                // Дальнейший вход для избежания повторного ввода данных
                                                val singInResult = viewModel.singIn.invoke(userEmail, userPassword)
                                                authState = singInResult.first

                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, singInResult.second, Toast.LENGTH_LONG).show()
                                                }

                                            }else{
                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(context, singUpResult.second, Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }

                                    } else {
                                        // TODO сделать реакцию на пустые поля, непосредственно в самих полях (типа там красить их и тп)
                                        Toast.makeText(context, "Заполните поля", Toast.LENGTH_LONG).show()
                                    }



                                }
                                //endregion
                                //region ForgotPassButton
                                ForgotPassButton(){

                                    if (userEmail != ""){
                                        scope.launch {
                                            val resetPasswordResult = viewModel.resetPassword.invoke(userEmail)

                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(context, resetPasswordResult.second, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Пожалуйста, заполните поле Email",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }
                                //endregion
                            }

                            //region LogInLogOutButton
                            LogInLogOutButton(authState){
                                if (!authState) {
                                    scope.launch {
                                        val singInResult = viewModel.singIn.invoke(userEmail, userPassword)
                                        authState = singInResult.first

                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, singInResult.second, Toast.LENGTH_LONG).show()
                                        }

                                    }

                                }else{
                                    auth.signOut()
                                    userName = ""
                                    authState = false
                                }
                            }
                            //endregion
                        }
                    }

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                    )
                }
            }

        },
    )
}




//region CustomTextField
@Composable
private fun CustomTextFieldFiendsScreen(
    placeholder: String,
    inputText: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { newText ->
            inputText(newText)
            value = newText
            isFocused = true
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        ),
        modifier = Modifier
            .onFocusChanged {
                isFocused = it.isFocused
            },
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
            }
        }
    )
}
//endregion
//region RegistrationButton
@Composable
private fun RegistrationButton(onPressButton: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier

            .clip(RoundedCornerShape(14))
            .background(color = PinkAppColor)
            .border(
                width = 1.dp,
                color = PinkAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = "Создать",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
        )
    }

}
//endregion
//region LogInLogOutButton
@Composable
private fun LogInLogOutButton(
    authState: Boolean,
    onPressButton: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier

            .clip(RoundedCornerShape(14))
            .background(color = if (!authState) CyanAppColor else PinkAppColor)
            .border(
                width = 1.dp,
                color = if (!authState) CyanAppColor else PinkAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = if (!authState) "Войти" else "Выйти",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
        )
    }

}
//endregion
//region ForgotPassButton
@Composable
private fun ForgotPassButton(
    onPressButton: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier

            .clip(RoundedCornerShape(14))
            .background(color = LinearTrackColor)
            .border(
                width = 1.dp,
                color = LinearTrackColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = "Напомнить",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
        )
    }

}
//endregion

//endregion


//region LabelText
@Composable
private fun LabelText(text: String) {
    Text(
        text = text,
        color = CyanAppColor,
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .offset(y = -(20).dp)
    )
}
//endregion



















