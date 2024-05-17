package com.kuvalin.brainstorm.data.repository

import com.kuvalin.brainstorm.data.database.UserDataDao
import com.kuvalin.brainstorm.data.firebase.ApiService
import com.kuvalin.brainstorm.data.mapper.BrainStormMapper
import com.kuvalin.brainstorm.data.model.GameResultDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BrainStormRepositoryImpl @Inject constructor(
    private val userDataDao: UserDataDao,
    private val apiService: ApiService,
    private val mapper: BrainStormMapper
): BrainStormRepository {


    /* ####################################### DATABASE ####################################### */

    // ###################### ADD
    override suspend fun addUserInfo(userInfo: UserInfo, initialState: Boolean) {
        userDataDao.addUserInfo(mapper.mapEntityToDbModelUserInfo(userInfo))
        if (!initialState){
            apiService.sendUserInfoToFirestore(userInfo)
        }
    }


    //region addFriend
    override suspend fun addFriend(friend: Friend, initialState: Boolean) {
        userDataDao.addFriendInfo(
            mapper.mapEntityToDbModelFriendInfo(
                friend.uid, friend.name, friend.email, friend.avatar, friend.country
            )
        )
        friend.gameStatistic?.map {
            userDataDao.addGameStatistic(mapper.mapEntityToDbModelGamesStatistic(it))
        }
        userDataDao.addWarStatistic(mapper.mapEntityToDbModelWarStatistics(friend.warStatistics))
        userDataDao.addListOfMessages(mapper.mapEntityToDbModelListOfMessage(friend.listOfMessages))

        if (!initialState){
            // Добавление в Firebase
            apiService.sendFriendsToFirestore(getFriend(friend.uid))
        }
    }
    //endregion


    /*
    Наверно неправильно будет во френдах хранить список сообщений, коли он нужен только в чатах;
    Я буду просто отдельно держать и получать по uid
    */

    override suspend fun addListOfMessages(listOfMessages: ListOfMessages, initialState: Boolean) {
        userDataDao.addListOfMessages(mapper.mapEntityToDbModelListOfMessage(listOfMessages))
        if (!initialState){
            apiService.sendChatToFirestore(listOfMessages)
        }
    }


    //region addGameResult
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

    }
    //endregion
    //region addGameStatistic
    private suspend fun addGameStatistic(
        userUid: String,
        gameName: String,
        listGameResult: List<GameResultDbModel>,
        initialState: Boolean = false
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
    //endregion


    //region addWarResult
    override suspend fun addWarResult(warResult: WarResult) {
        userDataDao.addWarResult(mapper.mapEntityToDbModelWarResult(warResult))

        var countWins = 0
        var countLoss = 0
        var countDraws = 0
        val warScope = mutableListOf<Int>()

        userDataDao.getWarResults(warResult.uid).map {
            when(it.result){
                "win" -> {countWins++}
                "loss" -> {countLoss++}
                "draw" -> {countDraws++}
                else -> {}
            }
            warScope.add(it.scope)
        }


        addWarStatistic( // TODO Тоже проебался с типами. Вообще потом проверить всё
            WarStatistics(
                uid = warResult.uid,
                winRate = (countWins/(countWins + countLoss)).toFloat(),
                wins = countWins,
                losses = countLoss,
                draws = countDraws,
                highestScore = warScope.max()
            )
        )

    }
    //endregion

    private suspend fun addWarStatistic(warStatistics: WarStatistics, initialState: Boolean = false) {
        userDataDao.addWarStatistic(mapper.mapEntityToDbModelWarStatistics(warStatistics))

        if (!initialState){
            apiService.sendWarStatisticToFirestore(warStatistics) // TODO и вот тут
        }
    }


    override suspend fun addAppSettings(appSettings: AppSettings) {
        userDataDao.addAppSettings(mapper.mapEntityToDbModelAppSettings(appSettings))
    }
    override suspend fun addAppCurrency(appCurrency: AppCurrency, initialState: Boolean) {
        userDataDao.addAppCurrency(mapper.mapEntityToDbModelAppCurrency(appCurrency))
        if (!initialState){
            apiService.sendAppCurrencyToFirestore(appCurrency)
        }
    }


    override suspend fun addSocialData(socialData: SocialData, initialState: Boolean) {
        userDataDao.addSocialData(mapper.mapEntityToDbModelSocialData(socialData))
        if (!initialState){
            apiService.sendSocialDataToFirestore(socialData)
        }
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
    override suspend fun getFriendList(): List<Friend>? {

        return  userDataDao.getFriendsWithAllInfo().map {
            mapper.mapDbModelToEntityFriend(
                it.friendInfoDbModel,
                it.listOfMessagesDbModel,
                gameStatisticDbModel = userDataDao.getListGamesStatistics(it.friendInfoDbModel.uid),
                it.warStatisticsDbModel
            )
        }

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



    /* ##################################### FIREBASE ########################################### */
    /* ####################################### AUTH ############################################# */

    override suspend fun singIn(email: String, password: String): Pair<Boolean, String> {

        val singInResult = apiService.singInFirebase(email = email, password = password)

        CoroutineScope(Dispatchers.Default).launch {
            if (singInResult.first){
                val userUid = apiService.getUserUid()
                apiService.getUserInfoFB(userUid)?.let { addUserInfo(it, initialState = true) }
                apiService.getSocialDataFB(userUid)?.let { addSocialData(it, initialState = true) }
                apiService.getAppCurrencyFB(userUid)?.let { addAppCurrency(it, initialState = true) }
                apiService.getGameStatisticFB(userUid)?.let { listGameStatistics ->
                    listGameStatistics.map {gameStatistics ->
                        userDataDao.addGameStatistic(
                            GameStatisticDbModel(
                                uid = userUid,
                                gameName = gameStatistics.gameName,
                                maxGameScore = gameStatistics.maxGameScore,
                                avgGameScore = gameStatistics.avgGameScore
                            )
                        )
                    }
                }
                apiService.getWarStatisticsFB(userUid)?.let { addWarStatistic(it, initialState = true) }
                apiService.getFriendsFB(userUid)?.let {listFriends ->
                    listFriends.map {friend ->
                        addFriend(friend, initialState = true)
                    }
                }
            }
        }

        return singInResult
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



    /* ####################################### GET ############################################## */
    override suspend fun getUserInfoFB(uid: String): UserInfo? {
        return apiService.getUserInfoFB(uid)
    }

    override suspend fun getGameStatisticFB(uid: String): List<GameStatistic>? {
        return apiService.getGameStatisticFB(uid)
    }

    override suspend fun getWarStatisticsFB(uid: String): WarStatistics? {
        return apiService.getWarStatisticsFB(uid)
    }

    override suspend fun getUserRequestsFB(): List<UserRequest>? {
        return apiService.getUserRequests()
    }
    /* ########################################################################################## */


    /* ###################################### SEND ############################################## */
    override suspend fun updateUserRequestFB(uidFriend: String, friendState: Boolean) {
        apiService.updateUserRequest(uidFriend, friendState)
    }
    /* ########################################################################################## */


    /* ##################################### DELETE ############################################# */
    override suspend fun deleteUserRequestFB(uidFriend: String) {
        apiService.deleteUserRequest(uidFriend)
    }
    /* ########################################################################################## */




    /* ####################################### GAME ############################################# */

    override suspend fun findTheGame(): Pair<Boolean, String> {
        return apiService.findTheGame()
    }


    override suspend fun updateUserScopeInWarGame(sessionId: String, gameName: String, scope: Int){
        apiService.updateUserScopeInWarGame(sessionId, gameName, scope)
    }
    override suspend fun getActualOpponentScopeFromWarGame(sessionId: String, gameName: String): StateFlow<Int>{
        return apiService.getActualOpponentScopeFromWarGame(sessionId, gameName)
    }

    override suspend fun getScopeFromWarGame(sessionId: String, gameName: String, type: String): Int {
        return apiService.getScopeFromWarGame(sessionId, gameName, type)
    }

    override suspend fun addFriendInGame(sessionId: String) {
        apiService.addFriendInGame(sessionId)
    }

    /* ########################################################################################## */

}












