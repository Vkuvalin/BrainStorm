package com.kuvalin.brainstorm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kuvalin.brainstorm.data.model.AppCurrencyDbModel
import com.kuvalin.brainstorm.data.model.AppSettingsDbModel
import com.kuvalin.brainstorm.data.model.ChatInfoDbModel
import com.kuvalin.brainstorm.data.model.FriendInfoDbModel
import com.kuvalin.brainstorm.data.model.GameResultDbModel
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.data.model.SocialDataDbModel
import com.kuvalin.brainstorm.data.model.UserInfoDbModel
import com.kuvalin.brainstorm.data.model.WarResultDbModel
import com.kuvalin.brainstorm.data.model.WarStatisticsDbModel
import com.kuvalin.brainstorm.data.model.converters.ListStringConverter
import com.kuvalin.brainstorm.data.model.converters.UriTypeConverter
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal.UNDEFINED_ID

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
    GameResultDbModel::class,
    GameStatisticDbModel::class,
    ChatInfoDbModel::class,
    SocialDataDbModel::class,
    UserInfoDbModel::class,
    WarResultDbModel::class,
    WarStatisticsDbModel::class,
    ],
    version = 10,
    exportSchema = false
)
@TypeConverters(
    UriTypeConverter::class,
    ListStringConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any() // Базы данных должны быть синхронизированы
        private const val DB_NAME = "user_data.db"
        private val scope = CoroutineScope(Dispatchers.IO)

        fun getInstance(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(LOCK){
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME) // applicationContext для предотвращения утечек
                    .addCallback(object : Callback() { // Добавление данных при создании БД
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            scope.launch {
                                withContext(NonCancellable) {// Безопасность при отмене корутин
                                    try {
                                        val dao = getInstance(context).userDataDao()
                                        dao.addAppSettings(
                                            AppSettingsDbModel(
                                                UNDEFINED_ID,
                                                musicState = true,
                                                vibrateState = true
                                            )
                                        )
                                    } catch (e: Exception) {
                                        println("Error adding initial app settings: ${e.message}")
                                    }

                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }

        }

    }

    abstract fun userDataDao(): UserDataDao
}




