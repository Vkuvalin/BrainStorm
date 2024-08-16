package com.kuvalin.brainstorm.presentation.screens.mainmenu.war.searchForWarScreen


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kuvalin.brainstorm.globalClasses.AssetImage
import androidx.compose.ui.text.font.FontWeight



@Composable
fun UserInfo(
    uriAvatar: Uri?,
    name: String,
    grade: String,
    rank: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .weight(2f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                //region Avatar
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Box {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color = Color.White)
                                .border(width = 2.dp, color = Color.White, shape = CircleShape),
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
                //endregion
                //region Имя + Grade/Rank
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //region Grade
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Grade",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = grade,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W300
                                )
                            }
                        }
                        //endregion
                        //region Rank
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Rank",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(end = 5.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "$rank",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W300
                                )
                            }
                        }
                        //endregion
                    }
                }
                //endregion
            }
        }

        //region Флаг
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
        ) {
            AssetImage(
                fileName = "ic_profile_russia.png",
                modifier = Modifier.size(60.dp)
            )
        }
        //endregion
    }
}
