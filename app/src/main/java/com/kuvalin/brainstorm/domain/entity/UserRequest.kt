package com.kuvalin.brainstorm.domain.entity

data class UserRequest(
    val uid: String,
    val sender: Boolean,
    val chatId: String = "",
    val answerState: Boolean = false,
    val friendState: Boolean = false
)

