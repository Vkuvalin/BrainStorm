package com.kuvalin.brainstorm.data.mapper

import android.net.Uri
import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.FriendInfoDbModel
import com.kuvalin.brainstorm.data.model.GameResultDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.ListOfMessagesDbModel
import com.kuvalin.brainstorm.data.model.SocialDataDbModel
import com.kuvalin.brainstorm.data.model.UserInfoDbModel
import com.kuvalin.brainstorm.data.model.WarResultDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarResult
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import javax.inject.Inject



class BrainStormMapper @Inject constructor() {


    /* ####################################### DATABASE ####################################### */

    // UserInfo
    fun mapEntityToDbModelUserInfo(userInfo: UserInfo): UserInfoDbModel {
        return UserInfoDbModel(
            userInfo.uid,
            userInfo.name,
            userInfo.email,
            userInfo.avatar,
            userInfo.country
        )
    }
    fun mapDbModelToEntityUserInfo(userInfoDbModel: UserInfoDbModel?): UserInfo? {
        if (userInfoDbModel != null) {
            return UserInfo(
                userInfoDbModel.uid,
                userInfoDbModel.name,
                userInfoDbModel.email,
                userInfoDbModel.avatar,
                userInfoDbModel.country
            )
        }else{
            return null
        }
    }



    // Friend
    fun mapEntityToDbModelFriendInfo(
            uid: String, name: String?, email: String?, avatar: Uri?, language: String?
        ): FriendInfoDbModel{
        return FriendInfoDbModel(uid, name, email, avatar, language)
    }
    fun mapDbModelToEntityFriend(
        friendInfoDbModel: FriendInfoDbModel,
        listOfMessagesDbModel: ListOfMessagesDbModel,
        gameStatisticDbModel: List<GameStatisticDbModel>,
        warStatisticsDbModel: WarStatisticsDbModel,
    ): Friend {
        return Friend(
            friendInfoDbModel.uid,
            friendInfoDbModel.name,
            friendInfoDbModel.email,
            friendInfoDbModel.avatar,
            friendInfoDbModel.country,
            listOfMessagesDbModel.listOfMessages,
            mapListDbModelToListEntityGameStatistics(gameStatisticDbModel),
            mapDbModelToEntityWarsStatistics(warStatisticsDbModel)
        )
    }
    //region Больше не нужная хрень, но пока оставлю
    //    fun mapListDbModelToListEntityFriend(
//        listFriendInfo: List<FriendInfoDbModel>,
//        listOfMessagesDbModel: List<ListOfMessagesDbModel>,
//        listGameStatisticDbModel: List<List<GameStatisticDbModel>>,
//        listWarStatisticsDbModel: List<WarStatisticsDbModel>
//    ): List<Friend> {
//        val listOfFriends = mutableListOf<Friend>()
//
//        // Проверка на одинаковую длину списков, чтобы избежать ошибок
//        if (listFriendInfo.size != listOfMessagesDbModel.size ||
//            listFriendInfo.size != listGameStatisticDbModel.size ||
//            listFriendInfo.size != listWarStatisticsDbModel.size
//        ) {
//            throw IllegalArgumentException("Списки моделей данных имеют разную длину")
//        }
//
//        // Итерация по спискам моделей данных и создание объектов Friend
//        for (i in listFriendInfo.indices) {
//            val friend = mapDbModelToEntityFriend(
//                listFriendInfo[i],
//                listOfMessagesDbModel[i],
//                listGameStatisticDbModel[i],
//                listWarStatisticsDbModel[i]
//            )
//            listOfFriends.add(friend)
//        }
//
//        return listOfFriends.toList()
//    }
    //endregion



    // ListOfMessages
    fun mapEntityToDbModelListOfMessage(listOfMessages: ListOfMessages): ListOfMessagesDbModel {
        return ListOfMessagesDbModel(
            listOfMessages.uid,
            listOfMessages.listOfMessages ?: listOf("")
        )
    }



    // GameResult
    fun mapEntityToDbModelGameResult(gameResult: GameResult): GameResultDbModel {
        return GameResultDbModel(
            gameResult.uid,
            gameResult.id,
            gameResult.gameName,
            gameResult.scope,
            gameResult.accuracy
        )
    }

    // GamesStatistics
    fun mapEntityToDbModelGamesStatistic(gameStatistic: GameStatistic): GameStatisticDbModel{
        return GameStatisticDbModel(
            gameStatistic.uid,
            gameStatistic.gameName,
            gameStatistic.maxGameScore,
            gameStatistic.avgGameScore
        )
    }
    fun mapDbModelToEntityGamesStatistic(gameStatisticDbModel: GameStatisticDbModel): GameStatistic {
        return GameStatistic(
            gameStatisticDbModel.uid,
            gameStatisticDbModel.gameName,
            gameStatisticDbModel.maxGameScore,
            gameStatisticDbModel.avgGameScore
        )
    }
    fun mapListEntityToListDbModelGameStatistics(list:List<GameStatistic>) = list.map {
        mapEntityToDbModelGamesStatistic(it)
    }
    fun mapListDbModelToListEntityGameStatistics(list:List<GameStatisticDbModel>) = list.map {
        mapDbModelToEntityGamesStatistic(it)
    }



    // AppSettings
    fun mapEntityToDbModelAppSettings(appSettings: AppSettings): AppSettingsDbModel{
        return AppSettingsDbModel(
            appSettings.id,
            appSettings.musicState,
            appSettings.vibrateState
        )
    }
    fun mapDbModelToEntityAppSettings(appSettingsDbModel: AppSettingsDbModel): AppSettings{
        return AppSettings(
            appSettingsDbModel.id,
            appSettingsDbModel.musicState,
            appSettingsDbModel.vibrateState
        )
    }


    // WarResult
    fun mapEntityToDbModelWarResult(warResult: WarResult): WarResultDbModel {
        return WarResultDbModel(
            warResult.uid,
            warResult.id,
            warResult.scope,
            warResult.result
        )
    }

    // WarsStatistics
    fun mapEntityToDbModelWarStatistics(warStatistics: WarStatistics?): WarStatisticsDbModel? {
        if (warStatistics != null) {
            return WarStatisticsDbModel(
                warStatistics.uid,
                warStatistics.winRate,
                warStatistics.wins,
                warStatistics.losses,
                warStatistics.draws,
                warStatistics.highestScore
            )
        }else {
            return null
        }
    }
    fun mapDbModelToEntityWarsStatistics(warStatisticsDbModel: WarStatisticsDbModel): WarStatistics{
        return WarStatistics(
            warStatisticsDbModel.uid,
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



    // SocialData
    fun mapEntityToDbModelSocialData(socialData: SocialData): SocialDataDbModel {
        return SocialDataDbModel(
            socialData.uid,
            socialData.twitter,
            socialData.vk,
            socialData.facebookConnect
        )
    }
    fun mapDbModelToEntitySocialData(socialDataDbModel: SocialDataDbModel?): SocialData? {
        if (socialDataDbModel != null) {
            return SocialData(
                socialDataDbModel.uid,
                socialDataDbModel.twitter,
                socialDataDbModel.vk,
                socialDataDbModel.facebookConnect
            )
        }else {
            return null
        }
    }
    /* ########################################################################################## */





    /* ######################################## FIREBASE ######################################## */

    // UserInfo
    fun mapEntityToFirebaseHashMapUserInfo(userInfo: UserInfo): HashMap<String, String> {
        return hashMapOf(
            "uid" to userInfo.uid,
            "name" to (userInfo.name ?: ""),
            "email" to (userInfo.email ?: ""),
            "avatar" to "", // Пока не учился хранить файлы
            "country" to (userInfo.country ?: "")
        )
    }

    // SocialData
    fun mapEntityToFirebaseHashMapSocialData(socialData: SocialData): HashMap<String, String> {
        return hashMapOf(
            "uid" to socialData.uid,
            "twitter" to (socialData.twitter ?: ""),
            "vk" to (socialData.vk ?: ""),
            "facebookConnect" to (socialData.facebookConnect.toString())
        )
    }

    // AppCurrency
    fun mapEntityToFirebaseHashMapAppCurrency(appCurrency: AppCurrency): HashMap<String, String> {
        return hashMapOf(
            // id и жизни тут бессмысленно хранить, коли это будет занимать только лишнее место
            "numberOfCoins" to appCurrency.numberOfCoins.toString()
        )
    }

    // Friend
    fun mapEntityToFirebaseHashMapFriendInfo(friendInfo: Friend): HashMap<String, String> {
        return hashMapOf(
            "uid" to friendInfo.uid,
            "name" to (friendInfo.name ?: ""),
            "email" to (friendInfo.email ?: ""),
            "avatar" to "", // Пока не учился хранить файлы
            "country" to (friendInfo.country ?: "")
        )
    }

    // Chat
    fun mapEntityToFirebaseHashMapChat(listOfMessages: ListOfMessages): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["uid"] = listOfMessages.uid
        map["list_of_messages"] = listOfMessages.listOfMessages ?: listOf("")
        return map
    }

    // GameStatistics
    fun mapDbModelToFirebaseHashMapGameStatistics(
        gameStatisticDbModel: GameStatisticDbModel
    ): HashMap<String, String> {
        return hashMapOf(
            "uid" to gameStatisticDbModel.uid,
            "gameName" to gameStatisticDbModel.gameName,
            "maxGameScore" to gameStatisticDbModel.maxGameScore.toString(),
            "avgGameScore" to gameStatisticDbModel.avgGameScore.toString()
        )
    }

    val dictionary = mapOf(
        "Flick Master" to "flick_master",
        "Addition Addiction" to "addition_addiction",
        "Reflection" to "reflection",
        "Path To Safety" to "path_to_safety",
        "Rapid Sorting" to "rapid_sorting",
        "Make10" to "make10",
        "Break The Block" to "break_the_block",
        "Hexa Chain" to "hexa_chain",
        "Color Switch" to "color_switch"
    )
    // Преобразование строки в аналогичную форму
    fun convertToAnalogGameName(original: String)
    = dictionary[original] ?: throw IllegalArgumentException("Ошибка: Строка '$original' не найдена в словаре.")

    // Обратное преобразование строки
    fun convertToOriginalGameName(analog: String)
    = dictionary.entries.find {
        it.value == analog
    }?.key ?: throw IllegalArgumentException("Ошибка: Строка '$analog' не найдена в словаре.")








    // WarStatistics
    fun mapEntityToFirebaseHashMapWarStatistics(warStatistics: WarStatistics): HashMap<String, String> {
        return hashMapOf(
            "uid" to warStatistics.uid,
            "winRate" to warStatistics.winRate.toString(),
            "wins" to warStatistics.wins.toString(),
            "losses" to warStatistics.losses.toString(),
            "draws" to warStatistics.draws.toString(),
            "highestScore" to warStatistics.highestScore.toString()
        )
    }

    /* ########################################################################################## */

}