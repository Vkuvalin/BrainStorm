package com.kuvalin.brainstorm.domain.entity

import com.kuvalin.brainstorm.globalClasses.GlobalConstVal
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.Companion.DEFAULT_COIN_VALUE
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.Companion.DEFAULT_LIVE_VALUE


data class AppCurrency(
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val numberOfLives: Int = DEFAULT_LIVE_VALUE,
    val numberOfCoins: Int = DEFAULT_COIN_VALUE
)
