package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal

@Entity("wars_statistics")
data class WarStatisticsDbModel(
    @PrimaryKey()
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val winRate: Float = 0f,
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
    val highestScore: Int
)
