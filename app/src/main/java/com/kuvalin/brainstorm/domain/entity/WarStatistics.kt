package com.kuvalin.brainstorm.domain.entity

import com.kuvalin.brainstorm.globalClasses.GlobalConstVal

data class WarStatistics(
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val winRate: Float = 0f,
    val wins: Int = 0,
    val losses: Int = 0,
    val draws: Int = 0,
    val highestScore: Int
)
