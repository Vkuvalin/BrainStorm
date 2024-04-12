package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class User(
    val uid: String,
    val name: String,
    val avatar: Uri? = null,
    val language: String? = null,
    val friendsList: List<String> = listOf(""),
    val listOfMessages: List<String>? = null
)
