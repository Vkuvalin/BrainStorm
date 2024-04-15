package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import com.kuvalin.brainstorm.domain.entity.AppCurrency.Companion.DEFAULT_COIN_VALUE
import com.kuvalin.brainstorm.domain.entity.AppCurrency.Companion.DEFAULT_LIVE_VALUE


@Entity("app_currency")
data class AppCurrencyDbModel(
    val numberOfLives: Int = DEFAULT_LIVE_VALUE,
    val numberOfCoins: Int = DEFAULT_COIN_VALUE
)
