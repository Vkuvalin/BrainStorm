package com.kuvalin.brainstorm.data.model

import androidx.room.Entity


@Entity(tableName = "games_statistics", primaryKeys = ["uid", "gameName"])
data class GameStatisticDbModel(
    val uid: String,
    val gameName: String,
    val maxGameScore: Int,
    val avgGameScore: Int
)