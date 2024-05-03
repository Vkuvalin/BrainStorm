package com.kuvalin.brainstorm.data.firebase

import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarStatistics


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

    // Firestore
    suspend fun sendUserInfoToFirestore(userInfo: UserInfo)
    suspend fun sendFriendsToFirestore(friend: Friend)
    suspend fun sendChatToFirestore(listOfMessages: ListOfMessages)
    suspend fun sendGameStatisticToFirestore(gameStatisticDbModel: GameStatisticDbModel)
    suspend fun sendWarStatisticToFirestore(warStatistics: WarStatistics)
    suspend fun sendAppCurrencyToFirestore(appCurrency: AppCurrency)
    suspend fun sendSocialDataToFirestore(socialData: SocialData)


    // Получение (перенос данных на другое уст-во делать не буду пока что)
    suspend fun getDataFromFirestore(documentPath: String): Any?

    // Game
    suspend fun findTheGame(): Boolean
}