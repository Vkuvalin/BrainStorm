package com.kuvalin.brainstorm.domain.entity

data class WarStatistics(
    val uid: String,
    val winRate: Float = 0f,
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
    val highestScore: Int = 0
)
