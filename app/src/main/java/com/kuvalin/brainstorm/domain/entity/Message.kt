package com.kuvalin.brainstorm.domain.entity

data class Message(
    val senderUid: String = "",
    val text: String,
    val timestamp: Long // По времени буду определять, последовательность
)