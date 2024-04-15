package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("games_statistics")
data class GameStatisticDbModel(
    @PrimaryKey
    val gameName: String,
    val gameIconName: String, // Буду создавать через свой ассетс
    val maxGameScore: Int,
    val avgGameScore: Int
)