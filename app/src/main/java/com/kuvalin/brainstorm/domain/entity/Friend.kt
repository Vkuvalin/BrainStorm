package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class Friend(
    val uid: String,
    val name: String,
    val avatar: Uri? = null,
    val language: String? = null,
    val listOfMessages: List<String>? = null,
    val gameStatistic: GameStatistic,
    val warStatistics: WarStatistics
)
