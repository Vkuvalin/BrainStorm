package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity("users_request")
data class UserRequestDbModel(
    @PrimaryKey
    val uid: String,
    val answerState: Boolean = false,
    val friendState: Boolean = false
)

