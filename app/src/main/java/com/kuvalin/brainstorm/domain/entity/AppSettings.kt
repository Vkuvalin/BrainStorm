package com.kuvalin.brainstorm.domain.entity


import com.kuvalin.brainstorm.globalClasses.GlobalConstVal

data class AppSettings(
    val id: Int = GlobalConstVal.UNDEFINED_ID,
    val musicState: Boolean = true,
    val vibrateState: Boolean = true,
)
