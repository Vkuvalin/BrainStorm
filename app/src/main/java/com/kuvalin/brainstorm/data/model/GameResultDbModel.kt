package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results")
data class GameResultDbModel(
    val uid: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val gameName: String,
    val scope: Int,
    val accuracy: Float
)










