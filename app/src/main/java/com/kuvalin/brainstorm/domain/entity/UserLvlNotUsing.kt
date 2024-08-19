package com.kuvalin.brainstorm.domain.entity

import com.kuvalin.brainstorm.globalClasses.GlobalConstVal

data class UserLvlNotUsing(
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val league: String,
    val rank: Int
)
