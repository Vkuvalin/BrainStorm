package com.kuvalin.brainstorm.domain.entity

import com.kuvalin.brainstorm.globalClasses.GlobalConstVal


data class AppCurrency(
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val numberOfLives: Int = GlobalConstVal.DEFAULT_LIVE_VALUE,
    val numberOfCoins: Int = GlobalConstVal.DEFAULT_COIN_VALUE
)
