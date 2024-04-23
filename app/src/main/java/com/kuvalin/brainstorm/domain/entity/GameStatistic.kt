package com.kuvalin.brainstorm.domain.entity


data class GameStatistic(
    val uid: String,
    val gameName: String,
    val maxGameScore: Int,
    val avgGameScore: Int
)