package com.kuvalin.brainstorm.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.FriendInfoDbModel
import com.kuvalin.brainstorm.data.model.FriendWithAllInfo
import com.kuvalin.brainstorm.data.model.GameResultDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.ChatInfoDbModel
import com.kuvalin.brainstorm.data.model.SocialDataDbModel
import com.kuvalin.brainstorm.data.model.UserInfoDbModel
import com.kuvalin.brainstorm.data.model.UserWithAllInfo
import com.kuvalin.brainstorm.data.model.WarResultDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel

@Dao
interface UserDataDao {

    // User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfoDbModel: UserInfoDbModel)
    @Query("SELECT * FROM user_info WHERE uid=:uid")
    suspend fun getUserInfo(uid: String): UserInfoDbModel


//    games_statistics.gameName,
//    games_statistics.maxGameScore,
//    games_statistics.avgGameScore,
//    LEFT JOIN
//    games_statistics ON user_info.uid = games_statistics.uid
    @Transaction
    @Query(
        """
        SELECT DISTINCT user_info.uid,
            user_info.name,
            user_info.email,
            user_info.avatar,
            user_info.country,
            wars_statistics.winRate,
            wars_statistics.wins,
            wars_statistics.losses,
            wars_statistics.draws,
            wars_statistics.highestScore
        FROM
            user_info
        LEFT JOIN
            wars_statistics ON user_info.uid = wars_statistics.uid
    """
    )
    suspend fun getUserWithAllInfo(): UserWithAllInfo



    // Friend
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFriendInfo(friendInfoDbModel: FriendInfoDbModel)
    @Query("SELECT * FROM friend_info WHERE uid=:uid")
    suspend fun getFriendInfo(uid: String): FriendInfoDbModel
//    @Query("SELECT * FROM friend_info")
//    suspend fun getListFriendInfo(): List<FriendInfoDbModel>


//    games_statistics.gameName,
//    games_statistics.maxGameScore,
//    games_statistics.avgGameScore,
//    LEFT JOIN
//    games_statistics ON friend_info.uid = games_statistics.uid

    @Transaction
    @Query(
        """
        SELECT friend_info.uid,
            friend_info.ownerUid,
            friend_info.name,
            friend_info.avatar,
            friend_info.email,
            friend_info.country,
            chat_info.uid,
            chat_info.chatId,
            wars_statistics.winRate,
            wars_statistics.wins,
            wars_statistics.losses,
            wars_statistics.draws,
            wars_statistics.highestScore
        FROM 
            friend_info
        LEFT JOIN 
            chat_info ON friend_info.uid = chat_info.uid
        LEFT JOIN 
            wars_statistics ON friend_info.uid = wars_statistics.uid
        WHERE friend_info.ownerUid=:uid
    """
        // TODO owner.uid - нужно для того, чтобы хранить локальные данные корректно, коли друзья не подвязаны отдельно к определенному uid
    )
    suspend fun getFriendsWithAllInfo(uid: String): List<FriendWithAllInfo>


    // ChatInfo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChatInfo(chatInfoDbModel: ChatInfoDbModel?)
    @Query("SELECT * FROM chat_info WHERE uid=:uid")
    suspend fun getChatInfo(uid: String): ChatInfoDbModel



    // GameResults
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameResult(gameResultDbModel: GameResultDbModel)
    @Query("SELECT * FROM game_results WHERE uid=:uid AND gameName=:gameName")
    suspend fun getGameResults(uid: String, gameName: String): List<GameResultDbModel>

    // GameStatistics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameStatistic(gameStatisticDbModel: GameStatisticDbModel)
    @Query("SELECT * FROM games_statistics WHERE uid=:uid AND gameName=:gameName")
    suspend fun getGameStatistic(uid: String, gameName: String): GameStatisticDbModel
    @Query("SELECT * FROM games_statistics WHERE uid=:uid")
    suspend fun getListGamesStatistics(uid: String): List<GameStatisticDbModel>



    // WarResults
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWarResult(warResultDbModel: WarResultDbModel)
    @Query("SELECT * FROM war_results WHERE uid=:uid")
    suspend fun getWarResults(uid: String): List<WarResultDbModel>

    // WarsStatistics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWarStatistic(warStatisticsDbModel: WarStatisticsDbModel?)
    @Query("SELECT * FROM wars_statistics WHERE uid=:uid")
    suspend fun getWarStatistic(uid: String): WarStatisticsDbModel?



    // AppSettings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppSettings(appSettingsDbModel: AppSettingsDbModel)
    @Query("SELECT * FROM app_settings")
    suspend fun getAppSettings(): AppSettingsDbModel



    // AppCurrency
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppCurrency(appCurrencyDbModel: AppCurrencyDbModel)
    @Query("SELECT * FROM app_currency")
    suspend fun getAppCurrency(): AppCurrencyDbModel



    // SocialData
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSocialData(socialDataDbModel: SocialDataDbModel)
    @Query("SELECT * FROM social_data WHERE uid=:uid LIMIT 1")
    suspend fun getSocialData(uid: String): SocialDataDbModel

}