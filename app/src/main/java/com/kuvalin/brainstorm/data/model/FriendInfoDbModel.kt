package com.kuvalin.brainstorm.data.model

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("friend_info")
data class FriendInfoDbModel(
    @PrimaryKey
    val uid: String,
    val ownerUid: String, // TODO я не стал переделывать связи, чтобы оптимизировать запрос в базу
    val name: String? = null,
    val email: String? = null,
    val avatar: Uri? = null,
    val country: String? = null
)


data class FriendWithAllInfo(
    @Embedded
    val friendInfoDbModel: FriendInfoDbModel,

    @Relation(parentColumn = "uid", entityColumn = "uid")
    val chatInfoDbModel: ChatInfoDbModel,

    @Relation( parentColumn = "uid", entityColumn = "uid")
    val warStatisticsDbModel: WarStatisticsDbModel
)