package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "war_results")
data class WarResultDbModel(
    val uid: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val scope: Int,
    val result: String // win, loss, draw (не хочу ща enum делать, протаскивать там всё, конверторы и тп)
)