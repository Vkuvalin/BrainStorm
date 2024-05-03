package com.kuvalin.brainstorm.data.repository

import com.kuvalin.brainstorm.data.database.UserDataDao
import com.kuvalin.brainstorm.data.firebase.ApiService
import com.kuvalin.brainstorm.data.mapper.BrainStormMapper
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class BrainStormRepositoryImpl @Inject constructor(
    private val userDataDao: UserDataDao,
    private val apiService: ApiService,
    private val mapper: BrainStormMapper
): BrainStormRepository {


    /* ####################################### DATABASE ####################################### */

    // ###################### ADD
    override suspend fun addUserInfo(userInfo: UserInfo) {
        userDataDao.addUserInfo(mapper.mapEntityToDbModelUserInfo(userInfo))
        apiService.sendUserInfoToFirestore(userInfo)
    }


    override suspend fun addFriend(friend: UserRequest) {
        userDataDao.addFriendInfo(
            mapper.mapEntityToDbModelFriendInfo(
                friend.uid, friend.name, friend.email, friend.avatar, friend.country
            )
        )
        friend.gameStatistic?.map {
            userDataDao.addGameStatistic(mapper.mapEntityToDbModelGamesStatistic(it))
        }
        userDataDao.addWarStatistic(mapper.mapEntityToDbModelWarStatistics(friend.warStatistics))

        // Добавление в Firebase
        apiService.sendFriendsToFirestore(getFriend(friend.uid))
    }


    /*
    Наверно неправильно будет во френдах хранить список сообщений, коли он нужен только в чатах;
    Я буду просто отдельно держать и получать по uid
    */

    override suspend fun addListOfMessages(listOfMessages: ListOfMessages) {
        userDataDao.addListOfMessages(mapper.mapEntityToDbModelListOfMessage(listOfMessages))
        apiService.sendChatToFirestore(listOfMessages)
    }


    override suspend fun addGameResult(gameResult: GameResult) {

        // Добавляем инфу об игре в базу
        userDataDao.addGameResult(mapper.mapEntityToDbModelGameResult(gameResult))

        // Тут же уже получаем обновленный список всех игр из game_result и пихаем его в game_statistic
        // Все эти манипуляции нужно, чтобы избежать лишних пересчетов
        addGameStatistic(
            gameResult.uid,
            gameResult.gameName,
            userDataDao.getGameResults(gameResult.uid, gameResult.gameName)
        )

        //TODO apiService.addGameResult - нужно ли? Так-то я могу обработать это и по другому
    }
    private suspend fun addGameStatistic(
        userUid: String,
        gameName: String,
        listGameResult: List<GameResult>
    ) {
        val gameScope = mutableListOf<Int>()
        listGameResult.map { gameScope.add(it.scope) }
        val maxGameScope = gameScope.max()

        userDataDao.addGameStatistic(GameStatisticDbModel(
            uid = userUid,
            gameName = gameName,
            maxGameScore = maxGameScope,
            avgGameScore = gameScope.average().toInt()
        ))

        apiService.sendGameStatisticToFirestore(userDataDao.getGameStatistic(userUid, gameName))
    }


    override suspend fun addWarStatistic(warStatistics: WarStatistics) {
        userDataDao.addWarStatistic(mapper.mapEntityToDbModelWarStatistics(warStatistics))
        apiService.sendWarStatisticToFirestore(warStatistics)
    }


    override suspend fun addAppSettings(appSettings: AppSettings) {
        userDataDao.addAppSettings(mapper.mapEntityToDbModelAppSettings(appSettings))
    }
    override suspend fun addAppCurrency(appCurrency: AppCurrency) {
        userDataDao.addAppCurrency(mapper.mapEntityToDbModelAppCurrency(appCurrency))
        apiService.sendAppCurrencyToFirestore(appCurrency)
    }


    override suspend fun addSocialData(socialData: SocialData) {
        userDataDao.addSocialData(mapper.mapEntityToDbModelSocialData(socialData))
        apiService.sendSocialDataToFirestore(socialData)
    }
    // ######################




    // ###################### GET
    override suspend fun getUserInfo(uid: String): UserInfo? {
        return mapper.mapDbModelToEntityUserInfo(userDataDao.getUserInfo(uid))
    }

    override suspend fun getFriend(uid: String): Friend {
        return mapper.mapDbModelToEntityFriend(
            userDataDao.getFriendInfo(uid),
            userDataDao.getListOfMessages(uid),
            userDataDao.getListGamesStatistics(uid),
            userDataDao.getWarStatistic(uid)
        )
    }
    override suspend fun getFriendList(): List<Friend> {
        return  userDataDao.getFriendsWithAllInfo().map {
            mapper.mapDbModelToEntityFriend(
                it.friendInfoDbModel,
                it.listOfMessagesDbModel,
                it.gameStatisticDbModel,
                it.warStatisticsDbModel
            )
        }.toList()
    }


    override suspend fun getGameStatistic(uid: String, gameName: String): GameStatistic {
        return mapper.mapDbModelToEntityGamesStatistic(userDataDao.getGameStatistic(uid, gameName))
    }
    override suspend fun getListGamesStatistics(uid: String): List<GameStatistic> {
        return mapper.mapListDbModelToListEntityGameStatistics(userDataDao.getListGamesStatistics(uid))
    }


    override suspend fun getAppSettings(): AppSettings {
        return mapper.mapDbModelToEntityAppSettings(userDataDao.getAppSettings())
    }


    override suspend fun getWarStatistic(uid: String): WarStatistics {
        return mapper.mapDbModelToEntityWarsStatistics(userDataDao.getWarStatistic(uid))
    }


    override suspend fun getAppCurrency(): AppCurrency {
        return mapper.mapDbModelToEntityAppCurrency(userDataDao.getAppCurrency())
    }

    override suspend fun getSocialData(uid: String): SocialData? {
        return mapper.mapDbModelToEntitySocialData(userDataDao.getSocialData(uid))
    }

    // Дальше сюда добавится также полностью зависимый от инета функционал GAMES для совместки
    // ######################

    /* ########################################################################################## */



    /* ##################################### FIREBASE - AUTH #################################### */

    override suspend fun singIn(email: String, password: String): Pair<Boolean, String> {
        return apiService.singInFirebase(email = email, password = password)
    }

    override suspend fun singUp(email: String, password: String): Pair<Boolean, String> {
        return apiService.signUpFirebase(email = email, password = password)
    }

    override suspend fun resetPassword(email: String): Pair<Boolean, String> {
        return apiService.resetPasswordFirebase(email = email)
    }

    override suspend fun authorizationCheck(): Boolean {
        return apiService.authorizationCheckFirebase()
    }

    /* ########################################################################################## */




    /* ########################################## GAME ########################################## */

    override suspend fun findTheGame(): Boolean {
        return apiService.findTheGame()
    }

    /* ########################################################################################## */

}












