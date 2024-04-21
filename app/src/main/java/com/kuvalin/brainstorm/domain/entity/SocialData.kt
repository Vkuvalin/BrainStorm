package com.kuvalin.brainstorm.domain.entity

data class SocialData(
    val uid: String,
    val twitter: String? = null,
    val vk: String? = null,
    val facebookConnect: Boolean = false
)