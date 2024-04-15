package com.kuvalin.brainstorm.data.mapper

import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.FriendDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.UserDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.User
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import javax.inject.Inject

class BrainStormMapper @Inject constructor() {


    // User
    fun mapEntityToDbModelUser(user: User): UserDbModel {
        return UserDbModel(
            user.uid,
            user.name,
            user.avatar,
            user.language,
            user.listOfMessages,
            mapEntityToDbModelGamesStatistics(user.gameStatistic),
            mapEntityToDbModelWarStatistics(user.warStatistics)
        )
    }
    fun mapDbModelToEntityUser(userDbModel: UserDbModel): User {
        return User(
            userDbModel.uid,
            userDbModel.name,
            userDbModel.avatar,
            userDbModel.language,
            userDbModel.listOfMessages,
            mapDbModelToEntityGamesStatistics(userDbModel.gameStatistic),
            mapDbModelToEntityWarsStatistics(userDbModel.warStatistics)
        )
    }



    // Friend
    fun mapEntityToDbModelFriend(user: User): FriendDbModel{
        return FriendDbModel(
            user.uid,
            user.name,
            user.avatar,
            user.language,
            user.listOfMessages,
            mapEntityToDbModelGamesStatistics(user.gameStatistic),
            mapEntityToDbModelWarStatistics(user.warStatistics)
        )
    }
    fun mapDbModelToEntityFriend(friendDbModel: FriendDbModel): Friend {
        return Friend(
            friendDbModel.uid,
            friendDbModel.name,
            friendDbModel.avatar,
            friendDbModel.language,
            friendDbModel.listOfMessages,
            mapDbModelToEntityGamesStatistics(friendDbModel.gameStatistic),
            mapDbModelToEntityWarsStatistics(friendDbModel.warStatistics)
        )
    }
    fun mapListDbModelToListEntityFriend(list:List<FriendDbModel>) = list.map {
        mapDbModelToEntityFriend(it)
    }



    // GamesStatistics
    fun mapEntityToDbModelGamesStatistics(gameStatistic: GameStatistic): GameStatisticDbModel{
        return GameStatisticDbModel(
            gameStatistic.gameName,
            gameStatistic.gameIconName,
            gameStatistic.maxGameScore,
            gameStatistic.avgGameScore
        )
    }
    fun mapDbModelToEntityGamesStatistics(gameStatisticDbModel: GameStatisticDbModel): GameStatistic {
        return GameStatistic(
            gameStatisticDbModel.gameName,
            gameStatisticDbModel.gameIconName,
            gameStatisticDbModel.maxGameScore,
            gameStatisticDbModel.avgGameScore
        )
    }
    fun mapListDbModelToListEntityGameStatistic(list:List<GameStatisticDbModel>) = list.map {
        mapDbModelToEntityGamesStatistics(it)
    }




    // AppSettings
    fun mapEntityToDbModelAppSettings(appSettings: AppSettings): AppSettingsDbModel{
        return AppSettingsDbModel(appSettings.musicState, appSettings.musicState)
    }
    fun mapDbModelToEntityAppSettings(appSettingsDbModel: AppSettingsDbModel): AppSettings{
        return AppSettings(appSettingsDbModel.musicState, appSettingsDbModel.vibrateState)
    }



    // WarsStatistics
    fun mapEntityToDbModelWarStatistics(warStatistics: WarStatistics): WarStatisticsDbModel {
        return WarStatisticsDbModel(
            warStatistics.winRate,
            warStatistics.wins,
            warStatistics.losses,
            warStatistics.draws,
            warStatistics.highestScore
        )

    }
    fun mapDbModelToEntityWarsStatistics(warStatisticsDbModel: WarStatisticsDbModel): WarStatistics{
        return WarStatistics(
            warStatisticsDbModel.winRate,
            warStatisticsDbModel.wins,
            warStatisticsDbModel.losses,
            warStatisticsDbModel.draws,
            warStatisticsDbModel.highestScore
        )
    }



    // AppCurrency
    fun mapEntityToDbModelAppCurrency(appCurrency: AppCurrency): AppCurrencyDbModel{
        return AppCurrencyDbModel(
            appCurrency.numberOfCoins,
            appCurrency.numberOfCoins
        )
    }
    fun mapDbModelToEntityAppCurrency(appCurrencyDbModel: AppCurrencyDbModel): AppCurrency{
        return AppCurrency(
            appCurrencyDbModel.numberOfCoins,
            appCurrencyDbModel.numberOfCoins
        )
    }


}