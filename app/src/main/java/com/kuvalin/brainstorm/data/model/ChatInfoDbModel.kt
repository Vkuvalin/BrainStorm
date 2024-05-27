package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("chat_info")
data class ChatInfoDbModel(
    @PrimaryKey
    val uid: String,
    val chatId: String
)
