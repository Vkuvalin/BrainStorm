package com.kuvalin.brainstorm.domain.repository

import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import kotlinx.coroutines.flow.StateFlow


interface BrainStormRepository {

    /* ####################################### DATABASE ####################################### */
    
    // ADD
    suspend fun addUserInfo(userInfo: UserInfo, initialState: Boolean = false)

    suspend fun addFriend(friend: Friend, initialState: Boolean = false)

    suspend fun addChatInfo(chatInfo: ChatInfo, initialState: Boolean = false)

    suspend fun addGameResult(gameResult: GameResult)
    suspend fun addWarResult(warResult: WarResult)

    suspend fun addAppSettings(appSettings: AppSettings)
    suspend fun addAppCurrency(appCurrency: AppCurrency, initialState: Boolean = false)

    suspend fun addSocialData(socialData: SocialData, initialState: Boolean = false)


    // GET
    suspend fun getUserInfo(uid: String): UserInfo?

    suspend fun getFriend(uid: String): Friend
    suspend fun getFriendList(uid: String): List<Friend>?

    suspend fun getGameStatistic(uid: String, gameName: String): GameStatistic
    suspend fun getListGamesStatistics(uid: String): List<GameStatistic>

    suspend fun getWarStatistic(uid: String): WarStatistics?

    suspend fun getAppSettings(): AppSettings
    suspend fun getAppCurrency(): AppCurrency

    suspend fun getSocialData(uid: String): SocialData?

    //endregion ################################################################################# */




    /* ##################################### FIREBASE ########################################### */
    /* ####################################### AUTH ############################################# */
    suspend fun singIn(email: String, password: String): Pair<Boolean, String>
    suspend fun singUp(email: String, password: String): Pair<Boolean, String>
    suspend fun resetPassword(email: String): Pair<Boolean, String>
    suspend fun authorizationCheck(): Boolean
    suspend fun signOutFirebase()
    suspend fun getUserUid(): String
    //endregion ################################################################################# */

    /* ####################################### GET ############################################## */
    suspend fun getUserInfoFB(uid: String): UserInfo?
    suspend fun getGameStatisticFB(uid: String): List<GameStatistic>?
    suspend fun getWarStatisticsFB(uid: String): WarStatistics?

    suspend fun getUserRequestsFB(): List<UserRequest>?
    suspend fun getListMessages(chatId: String): StateFlow<List<Message>>
    //endregion ################################################################################# */


    /* ###################################### SEND ############################################## */
    suspend fun sendMessageToFirestore(message: Message, chatId: String)
    suspend fun updateUserRequestFB(uidFriend: String, friendState: Boolean)
    //endregion ################################################################################# */

    /* ##################################### DELETE ############################################# */
    suspend fun deleteUserRequestFB(uidFriend: String)
    //endregion ################################################################################# */



    /* ####################################### GAME ############################################# */
    suspend fun findTheGame(): Triple<Boolean, String, String>

    suspend fun updateUserScopeInWarGame(sessionId: String, gameName: String, scope: Int)
    suspend fun getActualOpponentScopeFromWarGame(sessionId: String, gameName: String): StateFlow<Int>
    suspend fun getScopeFromWarGame(sessionId: String, gameName: String, type: String): Int
    suspend fun addFriendInGame(sessionId: String)
    //endregion ################################################################################# */
}