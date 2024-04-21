package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class UserINTERNET(
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val avatar: Uri? = null,
    val language: String? = null,
    val gameStatistic: List<GameStatistic>? = null,
    val warStatistics: WarStatistics? = null
)
