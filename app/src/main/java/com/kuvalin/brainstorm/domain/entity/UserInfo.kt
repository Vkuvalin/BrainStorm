package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class UserInfo(
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val avatar: Uri? = null,
    val country: String? = null
)
