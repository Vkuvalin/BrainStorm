package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class Friend(
    val uid: String,
    val ownerUid: String,
    val name: String? = null,
    val email: String? = null,
    val avatar: Uri? = null,
    val country: String? = null,
    val listOfMessages: ListOfMessages? = null,
    val gameStatistic: List<GameStatistic>? = null,
    val warStatistics: WarStatistics? = null
)
