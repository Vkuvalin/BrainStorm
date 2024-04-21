package com.kuvalin.brainstorm.domain.entity


data class GameStatistic(
    val uid: String,
    val gameName: String,
    val gameIconName: String, // Буду создавать через свой ассетс
    val maxGameScore: Int,
    val avgGameScore: Int
)