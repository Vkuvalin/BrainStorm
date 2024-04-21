package com.kuvalin.brainstorm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.FriendInfoDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.ListOfMessagesDbModel
import com.kuvalin.brainstorm.data.model.SocialDataDbModel
import com.kuvalin.brainstorm.data.model.converters.UriTypeConverter
import com.kuvalin.brainstorm.data.model.UserInfoDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel
import com.kuvalin.brainstorm.data.model.converters.ListStringConverter


/**
 * Зачем нужен synchronized?
 *
 * 1. Если вдруг 2 потока одновременно вызвали данный метод (getInstance)
 * 2. Оба потока сделали проверку выше и оказалось, что INSTANCE == null
 * 3. Тогда оба потока дальше дойдут до synchronized и ОДИН из них войдет в данный блок
 * 4. И если не повторить проверку, то может возникнуть ситуация, когда будут созданы 2 экземпляра базы данных.
 *    Данная проверка называется "Дабл Чек"
 *
 *  *** Аннотация @Synchronized - работает по другому (добавлю комментарий Сумина):
 *      В данном примере если бд уже была проинициализирована, то её экземпляр могут
 *      получить все потоки одновременно, а в случае с @Synchronized каждый поток
 *      должен будет ждать, пока другой поток освободит монитор.
 */

@Database(entities = [
    AppSettingsDbModel::class,
    AppCurrencyDbModel::class,
    FriendInfoDbModel::class,
    GameStatisticDbModel::class,
    ListOfMessagesDbModel::class,
    SocialDataDbModel::class,
    UserInfoDbModel::class,
    WarStatisticsDbModel::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    UriTypeConverter::class,
    ListStringConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any() // Базы данных должны быть синхронизированы
        private const val DB_NAME = "user_data.db"
//        private val scope = CoroutineScope(Dispatchers.IO)

        fun getInstance(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(LOCK){
                Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }

        }

    }

    abstract fun userDataDao(): UserDataDao
}
