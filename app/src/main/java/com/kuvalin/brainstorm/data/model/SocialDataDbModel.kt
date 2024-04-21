package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("social_data")
data class SocialDataDbModel(
    @PrimaryKey()
    val uid: String,
    val twitter: String? = null,
    val vk: String? = null,
    val facebookConnect: Boolean = false
)