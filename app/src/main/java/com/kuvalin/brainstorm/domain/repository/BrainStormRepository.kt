package com.kuvalin.brainstorm.domain.repository

import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.User
import com.kuvalin.brainstorm.domain.entity.WarStatistics


interface BrainStormRepository {

    // ADD
    suspend fun addUser(user: User)
    suspend fun addFriend(user: User)
    suspend fun addGameStatistic(gameStatistic: GameStatistic)
    suspend fun addAppSettings(appSettings: AppSettings)
    suspend fun addWarStatistic(warStatistics: WarStatistics)
    suspend fun addAppCurrency(appCurrency: AppCurrency)


    // GET
    suspend fun getUser(): User

    suspend fun getFriend(uid: String): Friend
    suspend fun getFriendList(): List<Friend>

    suspend fun getGameStatistic(gameName: String): GameStatistic
    suspend fun getListGamesStatistics(): List<GameStatistic> // онлайн

    suspend fun getAppSettings(): AppSettings

    suspend fun getWarStatistic(): WarStatistics

    suspend fun getAppCurrency(): AppCurrency


}