package com.kuvalin.brainstorm.presentation.screens.mainmenu.menu

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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
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

@Composable
fun MenuScreen(){
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


    var clickButtonState by remember { mutableStateOf(false) }
    clickButtonState = announcementButtonState || settingsButtonState || informationButtonState || contactsButtonState

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
        }

        if (announcementButtonState) { AnnouncementContent(modifierForCloseButton) { announcementButtonState = false} }
        if (settingsButtonState) { SettingsContent(modifierForCloseButton2) { settingsButtonState = false } }
        if (announcementButtonState) { AnnouncementContent(modifierForCloseButton) { announcementButtonState = false} }
        if (announcementButtonState) { AnnouncementContent(modifierForCloseButton) { announcementButtonState = false} }

    }
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
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = text,
            fontSize = 24.sp,
            color = if (clickButtonState) Color(0xFFFFFFFF) else Color(0xFFE6E6E6),
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

    Dialog(
        onDismissRequest = { onClickDismiss() },
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
                                color = Color(0xFF00BBBA),
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

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenWidthDp

    var musicState by remember { mutableStateOf(false) }
    var seState by remember { mutableStateOf(false) }
    var vibrationState by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onClickDismiss() },
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
                        .noRippleClickable { onClickDismiss() }
                )

                LabelText("Settings")
                Spacer(modifier = Modifier.height(10.dp))

                SettingItem("Music", musicState){ musicState = !musicState }
                Spacer(modifier = Modifier.height(10.dp))

                SettingItem("SE", seState){ seState = !seState }
                Spacer(modifier = Modifier.height(10.dp))

                SettingItem("Vibrate", vibrationState){ vibrationState = !vibrationState }
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

//region LabelText
@Composable
private fun LabelText(text: String) {
    Text(
        text = text,
        color = Color(0xFF00BBBA),
        fontSize = 26.sp,
        softWrap = false,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
    )
}
//endregion


