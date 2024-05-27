package com.kuvalin.brainstorm.data.firebase

import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


/**
 * Должны ли Entity из Domain быть связаны лишь только с RepositoryImpl,
 * и тут мне уже нужно работать чисто с сущностями репозитория?

 * Но пока для экономии времени я свяжу на чистую, не вижу в этом проблемы.
*/

interface ApiService {
    // Методы для взаимодействия с Firebase.

    // Auth
    suspend fun singInFirebase(email: String, password: String): Pair<Boolean, String>
    suspend fun signUpFirebase(email: String, password: String): Pair<Boolean, String>
    suspend fun resetPasswordFirebase(email: String): Pair<Boolean, String>
    suspend fun authorizationCheckFirebase(): Boolean
    suspend fun getUserUid(): String

    // Firestore
    // send
    suspend fun sendUserInfoToFirestore(userInfo: UserInfo)
    suspend fun sendFriendsToFirestore(friend: Friend)
    suspend fun sendChatInfoToFirestore(chatInfo: ChatInfo)
    suspend fun sendMessageToFirestore(message: Message, chatId: String)
    suspend fun sendGameStatisticToFirestore(gameStatisticDbModel: GameStatisticDbModel)
    suspend fun sendWarStatisticToFirestore(warStatistics: WarStatistics)
    suspend fun sendAppCurrencyToFirestore(appCurrency: AppCurrency)
    suspend fun sendSocialDataToFirestore(socialData: SocialData)
    suspend fun updateUserRequest(uidFriend: String, friendState: Boolean)

    // get
    suspend fun getUserInfoFB(uid: String): UserInfo?
    suspend fun getFriendsFB(uid: String): List<Friend>?
    suspend fun getGameStatisticFB(uid: String): List<GameStatistic>?
    suspend fun getWarStatisticsFB(uid: String): WarStatistics?
    suspend fun getChatInfoFB(uid: String): ChatInfo?
    suspend fun getAppCurrencyFB(uid: String): AppCurrency?
    suspend fun getSocialDataFB(uid: String): SocialData?
    suspend fun getUserRequests(): List<UserRequest>?
    suspend fun getListMessages(chatId: String): StateFlow<List<Message>>


    // Удаление
    suspend fun deleteUserRequest(uidFriend: String)



    // Game
    suspend fun findTheGame(): Pair<Boolean, String>

    suspend fun updateUserScopeInWarGame(sessionId: String, gameName: String, scope: Int)
    suspend fun getActualOpponentScopeFromWarGame(sessionId: String, gameName: String): StateFlow<Int>
    suspend fun getScopeFromWarGame(sessionId: String, gameName: String, type: String): Int

    suspend fun addFriendInGame(sessionId: String)


}