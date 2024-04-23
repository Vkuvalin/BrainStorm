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
import com.kuvalin.brainstorm.data.model.ListOfMessagesDbModel
import com.kuvalin.brainstorm.data.model.SocialDataDbModel
import com.kuvalin.brainstorm.data.model.UserInfoDbModel
import com.kuvalin.brainstorm.data.model.UserWithAllInfo
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel
import com.kuvalin.brainstorm.domain.entity.GameResult

@Dao
interface UserDataDao {

    // User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfoDbModel: UserInfoDbModel)
    @Query("SELECT * FROM user_info WHERE uid=:uid")
    suspend fun getUserInfo(uid: String): UserInfoDbModel

    @Transaction
    @Query(
        """
        SELECT user_info.uid,
            user_info.name,
            user_info.email,
            user_info.avatar,
            user_info.country,
            list_of_messages.listOfMessages,
            games_statistics.gameName,
            games_statistics.maxGameScore,
            games_statistics.avgGameScore,
            wars_statistics.winRate,
            wars_statistics.wins,
            wars_statistics.losses,
            wars_statistics.draws,
            wars_statistics.highestScore
        FROM 
            user_info
        LEFT JOIN 
            list_of_messages ON user_info.uid = list_of_messages.uid
        LEFT JOIN 
            games_statistics ON user_info.uid = games_statistics.uid
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

    @Transaction
    @Query(
        """
        SELECT friend_info.uid,
            friend_info.name,
            friend_info.avatar,
            friend_info.email,
            friend_info.country,
            list_of_messages.listOfMessages,
            games_statistics.gameName,
            games_statistics.maxGameScore,
            games_statistics.avgGameScore,
            wars_statistics.winRate,
            wars_statistics.wins,
            wars_statistics.losses,
            wars_statistics.draws,
            wars_statistics.highestScore
        FROM 
            friend_info
        LEFT JOIN 
            list_of_messages ON friend_info.uid = list_of_messages.uid
        LEFT JOIN 
            games_statistics ON friend_info.uid = games_statistics.uid
        LEFT JOIN 
            wars_statistics ON friend_info.uid = wars_statistics.uid
    """
    )
    suspend fun getFriendsWithAllInfo(): List<FriendWithAllInfo>





    // ListOfMessages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfMessages(listOfMessages: ListOfMessagesDbModel)
    @Query("SELECT * FROM list_of_messages WHERE uid=:uid")
    suspend fun getListOfMessages(uid: String): ListOfMessagesDbModel



    // GameResults
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameResult(gameResultDbModel: GameResultDbModel)
    @Query("SELECT * FROM game_results WHERE uid=:uid AND gameName=:gameName")
    suspend fun getGameResults(uid: String, gameName: String): List<GameResult>

    // GameStatistics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameStatistic(gameStatisticDbModel: GameStatisticDbModel)
    @Query("SELECT * FROM games_statistics WHERE uid=:uid AND gameName=:gameName")
    suspend fun getGameStatistic(uid: String, gameName: String): GameStatisticDbModel // Теперь здесь будет list
    @Query("SELECT * FROM games_statistics WHERE uid=:uid")
    suspend fun getListGamesStatistics(uid: String): List<GameStatisticDbModel>



    // WarsStatistics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWarStatistic(warStatisticsDbModel: WarStatisticsDbModel?)
    @Query("SELECT * FROM wars_statistics WHERE uid=:uid LIMIT 1")
    suspend fun getWarStatistic(uid: String): WarStatisticsDbModel



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