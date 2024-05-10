package com.kuvalin.brainstorm.domain.entity

import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.Companion.UNDEFINED_ID

data class WarResult(
    val uid: String,
    val id: Int = UNDEFINED_ID,
    val scope: Int,
    val result: String // win, loss, draw (не хочу ща enum делать, протаскивать там всё, конверторы и тп)
)