package com.kuvalin.brainstorm.data.model

import androidx.room.Entity


@Entity("app_settings")
data class AppSettingsDbModel(
    val musicState: Boolean = true,
    val vibrateState: Boolean = true,
)
