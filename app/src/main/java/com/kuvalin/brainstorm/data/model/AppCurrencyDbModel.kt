package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.DEFAULT_COIN_VALUE
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.DEFAULT_LIVE_VALUE
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.UNDEFINED_ID


@Entity("app_currency")
data class AppCurrencyDbModel(
    @PrimaryKey()
    val id: Int = UNDEFINED_ID,
    val numberOfLives: Int = DEFAULT_LIVE_VALUE,
    val numberOfCoins: Int = DEFAULT_COIN_VALUE
)
