package com.kuvalin.brainstorm.data.model

import androidx.room.Entity

@Entity("wars_statistics")
data class WarStatisticsDbModel(
    val winRate: Float = 0f,
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
    val highestScore: Int
)
