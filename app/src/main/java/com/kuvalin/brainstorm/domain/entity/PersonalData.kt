package com.kuvalin.brainstorm.domain.entity

import android.net.Uri

data class PersonalData(
    val name: String? = null,
    val email: String? = null,
    val language: String? = null,
    var avatar: Uri? = null
)
