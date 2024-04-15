package com.kuvalin.brainstorm.domain.entity



data class AppCurrency(
    val numberOfLives: Int = DEFAULT_LIVE_VALUE,
    val numberOfCoins: Int = DEFAULT_COIN_VALUE
){

    companion object{
        const val DEFAULT_LIVE_VALUE = 5
        const val DEFAULT_COIN_VALUE = 0
    }
}
