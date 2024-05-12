package com.kuvalin.brainstorm.domain.repository

import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import kotlinx.coroutines.flow.StateFlow


interface BrainStormRepository {

    /* ####################################### DATABASE ####################################### */
    
    // ADD
    suspend fun addUserInfo(userInfo: UserInfo)

    // Сюда будет поставляться UserInternet + ещё пока не понятно, как именно это будет происходить
    suspend fun addFriend(userRequest: Friend)
    // Но фундамент сформирован!

    suspend fun addListOfMessages(listOfMessages: ListOfMessages)

    suspend fun addGameResult(gameResult: GameResult)
    suspend fun addWarResult(warResult: WarResult)

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

    /* ########################################################################################## */




    /* ##################################### FIREBASE ########################################### */
    /* ####################################### AUTH ############################################# */
    suspend fun singIn(email: String, password: String): Pair<Boolean, String>
    suspend fun singUp(email: String, password: String): Pair<Boolean, String>
    suspend fun resetPassword(email: String): Pair<Boolean, String>
    suspend fun authorizationCheck(): Boolean
    /* ########################################################################################## */

    /* ####################################### GET ############################################## */
    suspend fun getUserInfoFB(uid: String): UserInfo?
    suspend fun getUserRequests(): List<UserRequest>?
    /* ########################################################################################## */



    /* ####################################### GAME ############################################# */
    suspend fun findTheGame(): Pair<Boolean, String>

    suspend fun updateUserScopeInWarGame(sessionId: String, gameName: String, scope: Int)
    suspend fun getActualOpponentScopeFromWarGame(sessionId: String, gameName: String): StateFlow<Int>
    suspend fun getScopeFromWarGame(sessionId: String, gameName: String, type: String): Int
    suspend fun addFriendInGame(sessionId: String)
    /* ########################################################################################## */
}