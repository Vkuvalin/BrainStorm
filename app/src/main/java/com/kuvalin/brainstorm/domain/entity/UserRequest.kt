package com.kuvalin.brainstorm.domain.entity

import android.net.Uri


// По сути эта хуйня будет только приходить как UserRequest и собираться в инете
// Данные все таки нужно переливать в инет, чтобы затем была возможность легкого перевода на другое устр-во


data class UserRequest( // Теперь это станет UserRequest; gameStatistic тогда обновленный нужно будет присылать отдельно?
    val uid: String,
    val name: String? = null,
    val email: String? = null,
    val avatar: Uri? = null,
    val country: String? = null,
    val gameStatistic: List<GameStatistic>? = null,
    val warStatistics: WarStatistics? = null
)

