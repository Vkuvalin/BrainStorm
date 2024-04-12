package com.kuvalin.brainstorm.domain.entity

data class GamesStatistics(
    val gameName: String,
    val gameIconName: String, // Буду создавать через свой ассетс
    val maxGameScore: Int,
    val avgGameScore: Int
)