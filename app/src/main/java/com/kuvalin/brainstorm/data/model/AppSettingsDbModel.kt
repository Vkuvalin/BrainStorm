package com.kuvalin.brainstorm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.UNDEFINED_ID


@Entity("app_settings")
data class AppSettingsDbModel(
    @PrimaryKey()
    val id: Int = UNDEFINED_ID,
    val musicState: Boolean,
    val vibrateState: Boolean,
)
