package com.kuvalin.brainstorm.data.repository

import android.content.Context
import com.kuvalin.brainstorm.data.database.UserDataDao
import com.kuvalin.brainstorm.data.mapper.BrainStormMapper
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.AppSettings
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.User
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject

class BrainStormRepositoryImpl @Inject constructor(
    private val context: Context,
    private val userDataDao: UserDataDao,
    private val mapper: BrainStormMapper
): BrainStormRepository {



    /*
    Типы данных и виды их хранения:
    1 - только на устройстве
    2 - на устройстве и в firebase
    3 - только в firebase
    */

    // ADD
    override suspend fun addUser(user: User) { // 2
        userDataDao.addUser(mapper.mapEntityToDbModelUser(user))
    }
    override suspend fun addFriend(user: User) { // 2
        userDataDao.addFriend(mapper.mapEntityToDbModelFriend(user))
    } // Будет работать только с подключенным интернетом
    override suspend fun addGameStatistic(gameStatistic: GameStatistic) { // 2
        userDataDao.addGameStatistic(mapper.mapEntityToDbModelGamesStatistics(gameStatistic))
    }
    override suspend fun addAppSettings(appSettings: AppSettings) { // 1
        userDataDao.addAppSettings(mapper.mapEntityToDbModelAppSettings(appSettings))
    }
    override suspend fun addWarStatistic(warStatistics: WarStatistics) { // 2
        userDataDao.addWarStatistic(mapper.mapEntityToDbModelWarStatistics(warStatistics))
    }
    override suspend fun addAppCurrency(appCurrency: AppCurrency) { // 1 (разумно 2?)
        userDataDao.addAppCurrency(mapper.mapEntityToDbModelAppCurrency(appCurrency))
    }







    // GET
    override suspend fun getUser(): User { // 2
        return mapper.mapDbModelToEntityUser(userDataDao.getUser())
    }


    override suspend fun getFriend(uid: String): Friend { // 2
        return mapper.mapDbModelToEntityFriend(userDataDao.getFriend(uid))
    }
    override suspend fun getFriendList(): List<Friend> { // 2
        return mapper.mapListDbModelToListEntityFriend(userDataDao.getListFriend())
    }


    override suspend fun getGameStatistic(gameName: String): GameStatistic { // 2
        return mapper.mapDbModelToEntityGamesStatistics(userDataDao.getGameStatistic(gameName))
    }
    // Подумать: раз я получаю список объектов, из которого я могу вытащить объект, то
    // для чего мне тогда одиночный вызов getGameStatistic и налогичные getFriend
    // Вот getUserInfo понятно: когда я листал бы списки пользователей. Их же нет в других коллекциях
    override suspend fun getListGamesStatistics(): List<GameStatistic> { // 2
        return mapper.mapListDbModelToListEntityGameStatistic(userDataDao.getGamesStatistics())
    }


    override suspend fun getAppSettings(): AppSettings { // 1
        return mapper.mapDbModelToEntityAppSettings(userDataDao.getAppSettings())
    }


    override suspend fun getWarStatistic(): WarStatistics { // 2
        return mapper.mapDbModelToEntityWarsStatistics(userDataDao.getWarStatistic())
    }


    override suspend fun getAppCurrency(): AppCurrency { // 1 (разумно 2?)
        return mapper.mapDbModelToEntityAppCurrency(userDataDao.getAppCurrency())
    }

    // Дальше сюда добавится также полностью зависимый от инета функционал GAMES для совместки
}