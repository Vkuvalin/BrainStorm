package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class User(
    val uid: String,
    val name: String,
    val avatar: Uri? = null,
    val language: String? = null,
    val listOfMessages: List<String> = listOf(),
    val gameStatistic: GameStatistic,
    val warStatistics: WarStatistics
)
