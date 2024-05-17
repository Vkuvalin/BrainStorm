package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("list_of_messages")
data class ListOfMessagesDbModel(
    @PrimaryKey
    val uid: String,
    val listOfMessages: List<String>?
)
