package com.kuvalin.brainstorm.presentation.screens.mainmenu.profile

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.globalClasses.AssetImage
import com.kuvalin.brainstorm.globalClasses.GetAssetBitmap
import com.kuvalin.brainstorm.globalClasses.noRippleClickable
import com.kuvalin.brainstorm.ui.theme.CyanAppColor
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


@SuppressLint("Recycle")
@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues
) {

    var facebookConnectState by remember { mutableStateOf(false) }


    // Open Gallery
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { newUri: Uri? ->
        if (newUri == null) return@rememberLauncherForActivityResult

        val input = context.contentResolver.openInputStream(newUri)
            ?: return@rememberLauncherForActivityResult
        val outputFile = context.filesDir.resolve("profilePic${(1..1_000_000).random()}.jpg")
        input.copyTo(outputFile.outputStream())
        selectedImageUri = outputFile.toUri()
    }


    var onClickAvatar by remember { mutableStateOf(false) }
    if (onClickAvatar) { onClickAvatar = false }


    Column(
        modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(color = CyanAppColor),
            contentAlignment = Alignment.Center
        ) {
            //region Avatar
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .noRippleClickable {
                        onClickAvatar = true
                    }
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(color = Color.White)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .noRippleClickable { getContent.launch("image/*") }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri == null){
                        AssetImage(fileName = "av_user.png")
                    }else{
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedImageUri),
                            contentDescription = null,
                            modifier = Modifier
                        )
                    }
                }

                //region Camera
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(10))
                        .border(
                            width = 0.5.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10)
                        )
                        .background(color = Color.White)
                        .align(alignment = Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = GetAssetBitmap(fileName = "ic_profile_camera.png"),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(PinkAppColor),
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                //endregion
            }
            //endregion
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFE6E6E6)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileItem(iconFileName = "ic_profile_human.png", placeholder = "Name")
            ProfileItem(iconFileName = "ic_profile_email.png", placeholder = "Email Address")
            ProfileItem(iconFileName = "ic_profile_russia.png", placeholder = "Russian Federation")
            ProfileItem(iconFileName = "ic_twitter.png", placeholder = "Twitter @username")
            //region Facebook
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                AssetImage(
                    fileName = "ic_facebook.png",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(15.dp))
                Text(text = "Facebook Connect", fontSize = 18.sp, color = Color.Black)
                SwitchButton(checkedState = facebookConnectState){
                    facebookConnectState = !facebookConnectState
                }
            }
            //endregion

            SaveButton(){  }
        }

    }
}


//region ProfileItem
@Composable
fun ProfileItem(
    iconFileName: String,
    placeholder: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        AssetImage(
            fileName = iconFileName, modifier = Modifier
            .size(60.dp)
            .padding(15.dp))
        CustomTextFieldProfileScreen(placeholder = placeholder)
    }
}
//endregion
//region CustomTextField
@Composable
private fun CustomTextFieldProfileScreen(placeholder: String) {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
            isFocused = true
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
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
                    .padding(horizontal = 16.dp, vertical = 10.dp), // inner padding
            ) {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = placeholder,
                        fontSize = 18.sp,
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
//region MenuText
@Composable
private fun SaveButton(
    onPressButton: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(14))
            .background(color = CyanAppColor)
            .border(
                width = 1.dp,
                color = CyanAppColor,
                shape = RoundedCornerShape(14)
            )
            .noRippleClickable { onPressButton() }
    ){
        Text(
            text = "Save",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
    }

}
//endregion
