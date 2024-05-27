package com.kuvalin.brainstorm.data.mapper

import android.net.Uri
import android.util.Log
import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.FriendInfoDbModel
import com.kuvalin.brainstorm.data.model.GameResultDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.ChatInfoDbModel
import com.kuvalin.brainstorm.data.model.SocialDataDbModel
import com.kuvalin.brainstorm.data.model.UserInfoDbModel
import com.kuvalin.brainstorm.data.model.WarResultDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameResult
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.ChatInfo
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
        return if (userInfoDbModel != null) {
            UserInfo(
                userInfoDbModel.uid,
                userInfoDbModel.name,
                userInfoDbModel.email,
                userInfoDbModel.avatar,
                userInfoDbModel.country
            )
        }else{
            null
        }
    }



    // Friend
    fun mapEntityToDbModelFriendInfo(
            uid: String, ownerUid: String, name: String?, email: String?, avatar: Uri?, language: String?
        ): FriendInfoDbModel{
        return FriendInfoDbModel(uid, ownerUid, name, email, avatar, language)
    }
    fun mapDbModelToEntityFriend(
        friendInfoDbModel: FriendInfoDbModel,
        chatInfoDbModel: ChatInfoDbModel,
        gameStatisticDbModel: List<GameStatisticDbModel>,
        warStatisticsDbModel: WarStatisticsDbModel?,
    ): Friend {
        return Friend(
            friendInfoDbModel.uid,
            friendInfoDbModel.ownerUid,
            friendInfoDbModel.name,
            friendInfoDbModel.email,
            friendInfoDbModel.avatar,
            friendInfoDbModel.country,
            mapDbModelToEntityChatInfo(chatInfoDbModel),
            mapListDbModelToListEntityGameStatistics(gameStatisticDbModel),
            mapDbModelToEntityWarsStatistics(warStatisticsDbModel)
        )
    }



    // ChatInfo
    fun mapEntityToDbModelListOfMessage(chatInfo: ChatInfo?): ChatInfoDbModel? {
        Log.d("TEST_TEST", "$chatInfo   <---- mapEntityToDbModelListOfMessage -> chatInfo")
        return if (chatInfo != null) {
            ChatInfoDbModel(
                chatInfo.uid,
                chatInfo.chatId
            )
        }else {
            null
        }
    }
    private fun mapDbModelToEntityChatInfo(chatInfoDbModel: ChatInfoDbModel): ChatInfo {
        return ChatInfo(
            uid = chatInfoDbModel.uid,
            chatId = chatInfoDbModel.chatId
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
        return if (warStatistics != null) {
            WarStatisticsDbModel(
                warStatistics.uid,
                warStatistics.winRate,
                warStatistics.wins,
                warStatistics.losses,
                warStatistics.draws,
                warStatistics.highestScore
            )
        }else {
            null
        }
    }
    fun mapDbModelToEntityWarsStatistics(warStatisticsDbModel: WarStatisticsDbModel?): WarStatistics?{
        return if (warStatisticsDbModel != null) {
            WarStatistics(
                warStatisticsDbModel.uid,
                warStatisticsDbModel.winRate,
                warStatisticsDbModel.wins,
                warStatisticsDbModel.losses,
                warStatisticsDbModel.draws,
                warStatisticsDbModel.highestScore
            )
        }else {
            null
        }

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
            "ownerUid" to (friendInfo.ownerUid),
            "name" to (friendInfo.name ?: ""),
            "email" to (friendInfo.email ?: ""),
            "avatar" to "", // TODO Пока не учился хранить файлы
            "country" to (friendInfo.country ?: "")
        )
    }

    // Chat
    fun mapEntityToFirebaseHashMapChat(chatInfo: ChatInfo): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["uid"] = chatInfo.uid
        map["chat_id"] = chatInfo.chatId
        return map
    }

    // GameStatistics
    fun mapDbModelToFirebaseHashMapGameStatistic(
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