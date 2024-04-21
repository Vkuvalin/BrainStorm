package com.kuvalin.brainstorm.data.model

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity("user_info")
data class UserInfoDbModel(
    @PrimaryKey
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val avatar: Uri? = null,
    val country: String? = null
)



data class UserWithAllInfo(
    @Embedded
    val userInfoDbModel: UserInfoDbModel,

    @Relation( parentColumn = "uid", entityColumn = "uid")
    val listOfMessagesDbModel: ListOfMessagesDbModel,

    @Relation( parentColumn = "uid", entityColumn = "uid")
    val gameStatisticDbModel: List<GameStatisticDbModel>,

    @Relation( parentColumn = "uid", entityColumn = "uid")
    val warStatisticsDbModel: WarStatisticsDbModel
)
