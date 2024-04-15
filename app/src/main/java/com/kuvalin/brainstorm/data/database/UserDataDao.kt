package com.kuvalin.brainstorm.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.FriendDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.UserDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel

@Dao
interface UserDataDao {

    // User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userDbModel: UserDbModel)
    @Query("SELECT * FROM user_info")
    suspend fun getUser(): UserDbModel


    // Friend
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFriend(friendDbModel: FriendDbModel)
    @Query("SELECT * FROM friend_info WHERE uid=:uid")
    suspend fun getFriend(uid: String): FriendDbModel
    @Query("SELECT * FROM friend_info")
    suspend fun getListFriend(): List<FriendDbModel>



    // GameStatistics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameStatistic(gameStatisticDbModel: GameStatisticDbModel)
    @Query("SELECT * FROM games_statistics WHERE gameName=:gameName")
    suspend fun getGameStatistic(gameName: String): GameStatisticDbModel
    @Query("SELECT * FROM games_statistics")
    suspend fun getGamesStatistics(): List<GameStatisticDbModel>



    // AppSettings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppSettings(appSettingsDbModel: AppSettingsDbModel)
    @Query("SELECT * FROM app_settings")
    suspend fun getAppSettings(): AppSettingsDbModel



    // WarsStatistics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWarStatistic(warStatisticsDbModel: WarStatisticsDbModel)
    @Query("SELECT * FROM wars_statistics")
    suspend fun getWarStatistic(): WarStatisticsDbModel



    // AppCurrency
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAppCurrency(appCurrencyDbModel: AppCurrencyDbModel)
    @Query("SELECT * FROM app_currency")
    suspend fun getAppCurrency(): AppCurrencyDbModel

}