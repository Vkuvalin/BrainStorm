package com.kuvalin.brainstorm.domain.repository

import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserINTERNET
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarStatistics


interface BrainStormRepository {

    // ADD
    suspend fun addUserInfo(userInfo: UserInfo)

    // Сюда будет поставляться UserInternet + ещё пока не понятно, как именно это будет происходить
    suspend fun addFriend(userINTERNET: UserINTERNET)
    // Но фундамент сформирован!

    suspend fun addListOfMessages(listOfMessages: ListOfMessages)

    suspend fun addGameResult(gameResult: GameResult)
//    suspend fun addGameStatistic(gameStatistic: GameStatistic)
    suspend fun addWarStatistic(warStatistics: WarStatistics)

    suspend fun addAppSettings(appSettings: AppSettings)
    suspend fun addAppCurrency(appCurrency: AppCurrency)

    suspend fun addSocialData(socialData: SocialData)

    // GET
    suspend fun getUserInfo(uid: String): UserInfo?

    suspend fun getFriend(uid: String): Friend // Точно ли будет нужно?
    suspend fun getFriendList(): List<Friend> // Ведь я могу отсюда доставить всех

    suspend fun getGameStatistic(uid: String, gameName: String): GameStatistic
    suspend fun getListGamesStatistics(uid: String): List<GameStatistic>

    suspend fun getWarStatistic(uid: String): WarStatistics

    suspend fun getAppSettings(): AppSettings
    suspend fun getAppCurrency(): AppCurrency

    suspend fun getSocialData(uid: String): SocialData?


}