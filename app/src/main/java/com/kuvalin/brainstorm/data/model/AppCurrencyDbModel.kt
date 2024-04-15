package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.Companion.DEFAULT_COIN_VALUE
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.Companion.DEFAULT_LIVE_VALUE


@Entity("app_currency")
data class AppCurrencyDbModel(
    @PrimaryKey()
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val numberOfLives: Int = DEFAULT_LIVE_VALUE,
    val numberOfCoins: Int = DEFAULT_COIN_VALUE
)
