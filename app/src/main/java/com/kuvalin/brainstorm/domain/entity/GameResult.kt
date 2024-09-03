package com.kuvalin.brainstorm.domain.entity

import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.UNDEFINED_ID

data class GameResult(
    val uid: String,
    val id: Int = UNDEFINED_ID,
    val gameName: String,
    val scope: Int,
    val accuracy: Float
)