package com.kuvalin.brainstorm.domain.entity


import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.UNDEFINED_ID

data class AppSettings(
    val id: Int = UNDEFINED_ID,
    val musicState: Boolean,
    val vibrateState: Boolean,
)
