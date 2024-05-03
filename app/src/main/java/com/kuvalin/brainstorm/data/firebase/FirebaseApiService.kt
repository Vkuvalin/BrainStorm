package com.kuvalin.brainstorm.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.data.mapper.BrainStormMapper
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseApiService @Inject constructor(
    private val fireBase: Firebase,
    private val mapper: BrainStormMapper
) : ApiService {


    /* ######################################### AUTH ########################################### */

    override suspend fun singInFirebase(email: String, password: String): Pair<Boolean, String> {
        return try {
            val authResult = fireBase.auth.signInWithEmailAndPassword(email, password).await()
            var result = Pair(false, "Возникла непредвиденная ошибка...")

            // Этот код выполнится только в случае успешной аутентификации
            authResult.user?.let {
                result = Pair(true, "Авторизация прошла успешно!")
            }
            result
        } catch (e: FirebaseAuthInvalidUserException) {
            // Почему-то сюда не попадет, но и без не работает. Ща разбираться не буду
            Pair(false, "Неверный email. Проверьте правильность написания.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Pair(false, "Неверный пароль. Проверьте правильность написания.")
        } catch (e: Exception) {
            Pair(false, "Возникла непредвиденная ошибка...")
        }
    }

    override suspend fun signUpFirebase(email: String, password: String): Pair<Boolean, String> {
        return try {
            fireBase.auth.createUserWithEmailAndPassword(email, password).await()
            Pair(true, "Регистрация прошла успешно!")
        } catch (e: FirebaseAuthWeakPasswordException) {
            Pair(false, "Слабый пароль. Пожалуйста, используйте более надежный пароль.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Pair(false, "Неверный формат email. Пожалуйста, проверьте правильность написания.")
        } catch (e: FirebaseAuthUserCollisionException) {
            Pair(false, "Пользователь с таким email уже зарегистрирован.")
        } catch (e: Exception) {
            Pair(false, "Возникла непредвиденная ошибка...")
        }
    }

    override suspend fun resetPasswordFirebase(email: String): Pair<Boolean, String> {
        return try {
            fireBase.auth.sendPasswordResetEmail(email).await()
            Pair(true, "Ссылка для восстановления пароля отправлена на ваш email.")
        } catch (e: FirebaseAuthInvalidUserException) {
            Pair(false, "Пользователь с таким email не найден.")
        } catch (e: Exception) {
            Pair(false, "Возникла непредвиденная ошибка...")
        }
    }

    override suspend fun authorizationCheckFirebase(): Boolean {
        return fireBase.auth.currentUser != null
    }
    /* ########################################################################################## */




    /* ####################################### FIRESTORE ######################################## */

    /**
     * Дальнейшие действия *

        Я по путям, описанным в тетради, делаю методы заливки данных, которые будут дублировать
     все обновления. Инфу свою я буду черпать из памяти телефона.

    * Инфа в инете будет служить 2 целям:
        - бекапу данных, на случай потери или переходу на новое устр-во;
        - перекачка данных для друзей и других юзеров;

     * Есть доп записи в UserRequest дописать метод, который будет передовать user_uid, актуальный

    */

    // ###################### FIRESTORE_PATH
    private fun getFireStoreUserPath(
        userUid: String,
        pathName: String,
        gameName: String = "",
        uid: String = "" // Уникальное значение-идентификатор для коллекций
    ): String{
        return when(pathName){
            "userInfo" -> { "users/${userUid}/user_data/user_info/"}
            "socialData" -> {"users/${userUid}/user_data/social_data/"}
            "appCurrency" -> {"users/${userUid}/user_data/app_currency/"}
            "friendsInfo" -> {"users/${userUid}/friend_info/${uid}"}
            "chats" -> {"users/${userUid}/chats/${uid}"}
            "usersRequest" -> {"users/${userUid}/users_request"}
            "gameStatistics" -> {"users/${userUid}/game/game_statistics/${uid}/${gameName}"}
            "warsStatistics" -> {"users/${userUid}/game/wars_statistics/${uid}/wars_statistics"}
            "gameResults" -> {"users/${userUid}/game/game_results/user_game_results/"}
            else -> ""
        }
    }
    // ######################





    // ###################### SEND

    // UserInfo
    override suspend fun sendUserInfoToFirestore(userInfo: UserInfo) {
        if (fireBase.auth.uid != null){
            val userInfoPath = getFireStoreUserPath(fireBase.auth.uid.toString(), "userInfo")

            try {
                fireBase.firestore.document(userInfoPath)
                    .set(mapper.mapEntityToFirebaseHashMapUserInfo(userInfo))
                //region Успех/ошибка
                .addOnSuccessListener { documentReference ->
                    Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Log.w("FIRESTORE_SEND", "Error adding document", e)
                }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }
    }

    // SocialData
    override suspend fun sendSocialDataToFirestore(socialData: SocialData) {
        if (fireBase.auth.uid != null){
            val socialDataPath = getFireStoreUserPath(fireBase.auth.uid.toString(), "socialData")

            try {
                fireBase.firestore.document(socialDataPath)
                    .set(mapper.mapEntityToFirebaseHashMapSocialData(socialData))
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error adding document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }
    }

    // AppCurrency
    override suspend fun sendAppCurrencyToFirestore(appCurrency: AppCurrency) {
        if (fireBase.auth.uid != null){
            val appCurrencyPath = getFireStoreUserPath(fireBase.auth.uid.toString(), "appCurrency")

            try {
                fireBase.firestore.document(appCurrencyPath)
                    .set(mapper.mapEntityToFirebaseHashMapAppCurrency(appCurrency))
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error adding document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }
    }


    // ListFriends
    /**
     * Здесь я буду, как и в базе, разбивать данные на разные таблички, собирая их при получении.
     */
    override suspend fun sendFriendsToFirestore(friend: Friend) {
        if (fireBase.auth.uid != null){
            val userUid = fireBase.auth.uid.toString()
            val friendInfoPath = getFireStoreUserPath(
                userUid = userUid,
                pathName = "friendsInfo",
                uid = friend.uid
                )

            try {
                fireBase.firestore.document(friendInfoPath)
                    .set(mapper.mapEntityToFirebaseHashMapFriendInfo(friend))
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error adding document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }


            /**
             * Короче дальше я ебану преобразование в класс Chat, GameStat и WarStat, но мне кажется,
             * можно было бы лучше. Вообще позже поинтересоваться, на сколько затратным действием
             * является преобразование в класс и подобные вещи.
             */


            sendChatToFirestore(
                ListOfMessages( uid = friend.uid, listOfMessages = friend.listOfMessages)
            )

            if (friend.gameStatistic != null){
                for(game in friend.gameStatistic){
                    // Тут вот тоже можно было бы ебануть просто 2 функции, но экономлю время
                    sendGameStatisticToFirestore(mapper.mapEntityToDbModelGamesStatistic(game))
                }
            }

            if (friend.warStatistics != null){
                sendWarStatisticToFirestore(friend.warStatistics)
            }


        }
    }


    // Chat
    override suspend fun sendChatToFirestore(listOfMessages: ListOfMessages) {
        if (fireBase.auth.uid != null){
            val chatPath = getFireStoreUserPath(
                userUid = fireBase.auth.uid.toString(),
                pathName = "chats",
                uid = listOfMessages.uid
            )

            try {
                fireBase.firestore.document(chatPath)
                    .set(mapper.mapEntityToFirebaseHashMapChat(listOfMessages))
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error adding document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }
    }


    // GameStatistic
    override suspend fun sendGameStatisticToFirestore(gameStatisticDbModel: GameStatisticDbModel) {
        if (fireBase.auth.uid != null){
            val gameStatisticsPath = getFireStoreUserPath(
                userUid = fireBase.auth.uid.toString(),
                pathName = "gameStatistics",
                uid = gameStatisticDbModel.uid,
                gameName = mapper.convertToAnalogGameName(gameStatisticDbModel.gameName)
            )

            try {
                fireBase.firestore.document(gameStatisticsPath)
                    .set(mapper.mapDbModelToFirebaseHashMapGameStatistics(gameStatisticDbModel))
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error adding document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }
    }


    // WarStatistic
    override suspend fun sendWarStatisticToFirestore(warStatistics: WarStatistics) {
        if (fireBase.auth.uid != null){
            val warStatisticsPath = getFireStoreUserPath(
                userUid = fireBase.auth.uid.toString(),
                pathName = "warStatistics",
                uid = warStatistics.uid
            )

            try {
                fireBase.firestore.document(warStatisticsPath)
                    .set(mapper.mapEntityToFirebaseHashMapWarStatistics(warStatistics))
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        Log.w("FIRESTORE_SEND", "DocumentSnapshot added with ID: $documentReference")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error adding document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }


    }
    // ######################




    // ###################### GET
    override suspend fun getDataFromFirestore(documentPath: String): Any? {
        return try {
            fireBase.firestore.document(documentPath).get().await().data
        } catch (e: Exception) {
            null
        }
    }
    // ######################




    // ###################### GAME
    /*
    Логика игры будет такая:
        1. Кто-то нажимает "начать игру"
        2. Идет проверка игровых сессий на наличие пустого user_uid_2
        2.1.1 Допустим мы не нашли, то создаем свою пустую сессию с рандомным номером и играми,
        записываем себя в user_uid_1, заполняем list_games
        (сессии после игры смысла хранить нет, поэтому можно их просто потом удалять)
        2.1.2 Дальше идет циклическая проверка с таймером и шагом в 1 секунду, проверяя не появился ли второй игрок
        (пусть будет длиться 1 минуту, а после сессия будет закрываться) А нахуя, если есть liveUpdate?
        2.1.3 Второй игрок, присоединившись проставляет true в user_uid_ready
        (также можно сделать 1-2 секундную провеку, что именно тот второй попал в игру, если нет, то продолжает поиск)
        2.1.4 После того, как значения от двух игроков user_uid_ready == true, начинается цепочка игр

        2.1.5 После каждой игры будет выбрасывать на определенный экран как в браин варс:
    */

    /*
        Вторую аву и доп.инфу я буду тащить из инета. Нужно научиться сохранять файлы в firebase,
        а затем их как-то тут раскрывать.

        Avatar, name, grade, rank
    */

    // ######################
    override suspend fun findTheGame(): Boolean {
        val result = fireBase.firestore.collection("games").get().await()

        // Если нет ни одной свободной игры, то возвращаем false, чтобы создать самим
        if (result.size() == 0) { return false }

        for (document in result){
            Log.d("DATABASE", "${document.id} => FIRST")
            if (document.data["pink_user"] == ""){
                Log.d("DATABASE", "${document.id} => SECOND")
                Log.d("DATABASE", "${document.data["cyan_user"]} => CYAN USER")
            }
        }

        return true


//            .addOnSuccessListener {result ->
//                if (result.size() == 0){
//                    return@addOnSuccessListener
//                }
//                for (document in result){
//                    Log.d("DATABASE", "${document.id} => ${document.data}")
//                    Log.d("DATABASE", "${document.data["email"]}")
//                }
//            }
//            .addOnFailureListener {
//                Log.d("DATABASE", "$it")
//            }
    }


    /* ########################################################################################## */

}










/*
// $$$$$$$$$$$$$$$$$$$$$$$$$ FIREBASE $$$$$$$$$$$$$$$$$$$$$$$$$
val auth = Firebase.auth

// DataStore -> Firebase
val db = Firebase.firestore
// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$



Пример отправки данных:
val user = hashMapOf(
    "first" to "Vlad3",
    "last" to "Kuvalin3",
    "born" to 1996,
    "email" to userEmail,
    "pass" to userPassword,
    "language" to "Russia"
)

val appCurrency = hashMapOf(
    "life" to 3,
    "coins" to 250
)



########################## Первый вариант ##########################

#Вставляем
db.collection("users") // Дальше бы мне пришлось чередовать .document и .collection
    .add(user)
    .addOnSuccessListener { documentReference ->
        Log.d("DATABASE", "DocumentSnapshot added with ID: ${documentReference.id}")
    }
    .addOnFailureListener { e ->
        Log.w("DATABASE", "Error adding document", e)
    }


#Получаем
db.collection("users")
    .get()
    .addOnSuccessListener {result ->
        for (document in result){
            Log.d("DATABASE", "${document.id} => ${document.data}")
            Log.d("DATABASE", "${document.data["email"]}")
        }
    }
    .addOnFailureListener {
        Log.d("DATABASE", "$it")
    }
####################################################################




########################## Второй вариант ##########################

#Вставляем
// auth.uid -> т.к. в rules я указал именно такие настройки
db.document("users/${auth.uid}/user_data/personal_data/") // TODO Я ща просто большие пути всюду ебану
    .set(user)
    .addOnSuccessListener { documentReference ->
        Log.d("DATABASE", "DocumentSnapshot added with ID: $documentReference")
    }
    .addOnFailureListener { e ->
        Log.w("DATABASE", "Error adding document", e)
    }

db.document("users/${auth.uid}/user_data/app_currency") // TODO Я ща просто большие пути всюду ебану
    .set(appCurrency)
    .addOnSuccessListener { documentReference ->
        Log.d("DATABASE", "DocumentSnapshot added with ID: $documentReference")
    }
    .addOnFailureListener { e ->
        Log.w("DATABASE", "Error adding document", e)
    }


#Получаем
TODO-коммент ПОЛУЧЕНИЕ ДАННЫХ (живое)
    /*
    Это live-update его просто прописать разок и обновляться
    оно уже будет самостоятельно напрямую в базу.
    */

db.document("users/${auth.uid}/user_data/app_currency/")
    .addSnapshotListener { value, error ->
        Log.d("DATABASE", "${value?.data?.get("coins")}")
        Log.d("DATABASE", "${value?.data}")
    }



####################################################################


*/





