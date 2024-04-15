package com.kuvalin.brainstorm.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("friend_info")
data class FriendDbModel(
    @PrimaryKey
    val uid: String,
    val name: String,
    val avatar: Uri? = null,
    val language: String? = null,
    val listOfMessages: List<String>? = null,
    val gameStatistic: GameStatisticDbModel,
    val warStatistics: WarStatisticsDbModel
)
