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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.random.Random

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
            "usersRequest" -> {"users/${userUid}/users_request/${uid}"}
            "gameStatistics" -> {"users/${userUid}/game/game_statistics/${uid}/${gameName}"}
            "warStatistics" -> {"users/${userUid}/game/war_statistics/${uid}/war_statistics"}
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
                sendWarStatisticToFirestore(friend.warStatistics) // TODO Бля, проебался с типами, кажись
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
    private suspend fun getUserInfo(userUid: String): UserInfo? {

        if (fireBase.auth.uid != null){
            val userInfoPath = getFireStoreUserPath(userUid, "userInfo")

            try {
                val userInfo = fireBase.firestore.document(userInfoPath).get().await().data

                if (!userInfo.isNullOrEmpty()){
                    return UserInfo(
                         uid = userInfo["uid"].toString(),
                         name = userInfo["name"].toString(),
                         email = userInfo["email"].toString(),
                         avatar = null, // TODO
                         country = userInfo["country"].toString()
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }

        return null
    }
    // ######################





    // ###################### GAME

    //region FindTheGame
    override suspend fun findTheGame(): Pair<Boolean, String> {

        // ###################### ПЕРЕМЕННЫЕ
        var findFailed = false
        var gameSessionId = ""

        var gameReady = false
        var elapsedTime = 0L // Переменная для отслеживания прошедшего времени
        val timeoutMillis = 30 * 1000 // 30 секунд в миллисекундах

        var listGames = mutableListOf<String>()
        // ######################


        // Получаем список активных игры
        val gamesCollections = fireBase.firestore.collection("games").get().await()

        // Если нет ни одной свободной игры, то будем создавать её сами
        if (gamesCollections.documents.size == 0) { findFailed = true }

        if (!findFailed) {
            // Если активные игры есть, то ищем среди них свободную игру
            for (document in gamesCollections){
                if (document.data["pink_user"] == ""){
                    gameSessionId = document.id
                }
            }

            // Если всё-таки не нашли свободную игру, то идем создавать свою
            if (gameSessionId == "") {findFailed = true}
        }


        if (findFailed){
            // Идем создавать свою игру

            val newGame = hashMapOf(
                "cyan_user" to "${fireBase.auth.uid}",
                "pink_user" to "",
                "list_games" to listOf("Flick Master", "Path To Safety", "Make10"), // TODO если добавятся игрушки
                "cyan_ready" to true,
                "pink_ready" to false
            )
            val newGameSessionId = "session_${Random.nextLong(100000,999999999999999999)}"

            try {
                fireBase.firestore.document("games/$newGameSessionId").set(newGame).await()
                gameSessionId = newGameSessionId
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
                return Pair(false, "")
            }

        }else{
            // Тут мы уже нашли свободную игру, где в pink_uid запишем свой uid и проставим "ready"
            fireBase.firestore.document("games/$gameSessionId")
                .update("pink_user", fireBase.auth.uid).await()
            fireBase.firestore.document("games/$gameSessionId")
                .update("pink_ready", true).await()
        }


        // Финальный этап поиска: ждем, когда оба игрока подтвердят игру.
        val listenerRegistration = fireBase.firestore.document("games/$gameSessionId")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    if (value.data?.get("pink_ready") == true
                        &&
                        value.data?.get("cyan_ready") == true
                    ) {
//                        opponentUid = if (findFailed) value.data?.get("pink_user") as String
//                        else value.data?.get("cyan_user") as String
                        listGames = value.data?.get("list_games") as MutableList<String>
                        gameReady = true
                    }
                }
            }

        // Цикл while для ожидания ответа в течение 30 секунд
        while (elapsedTime < timeoutMillis) {
            if (gameReady) {
                // Если игра готова, отменяем прослушивание и возвращаем id игры
                listenerRegistration.remove()

                for (game in listGames){
                    fireBase.firestore
                        .document("games/$gameSessionId/game/${mapper.convertToAnalogGameName(game)}_${fireBase.auth.uid}")
                        .set(hashMapOf("scope" to "0"))
                        .await()
                }

                return Pair(true, gameSessionId)
            }
            delay(1000)
            elapsedTime += 1000
        }

        // Если время истекло, отменяем прослушивание и возвращаем false и пустой id
        listenerRegistration.remove()
        return Pair(false, "")
    }
    //endregion

    // Отправляет в firebase актуальные значения SCORE
    override suspend fun updateUserScopeInWarGame(sessionId: String, gameName: String, scope: Int){
        fireBase.firestore
            .document("games/$sessionId/game/${mapper.convertToAnalogGameName(gameName)}_${fireBase.auth.uid}")
            .set(hashMapOf("scope" to "$scope"))
            .await()
    }



    // Получает из firebase актуальные значения SCOPE оппонента
    override suspend fun getActualOpponentScopeFromWarGame(sessionId: String, gameName: String): StateFlow<Int> {
        val session = fireBase.firestore.document("games/$sessionId/").get().await()
        val opponentUid = (
                if (session.data?.get("pink_user") != fireBase.auth.uid) session.data?.get("pink_user")
                else session.data?.get("cyan_user")
        ) as String

        val mutableStateFlow = MutableStateFlow(0)

        fireBase.firestore
            .document("games/$sessionId/game/${mapper.convertToAnalogGameName(gameName)}_${opponentUid}")
            .addSnapshotListener { value, _ ->
                val newScope = value?.data?.get("scope").toString()
                mutableStateFlow.value = newScope.toInt()
            }
        // TODO Потом понять, как перевести в обычный поток. Да и вообще глянуть, где у меня поточная инфа
        return mutableStateFlow
    }


    // Нужна для получения конечных результатов игры в WarGameResults
    override suspend fun getScopeFromWarGame(sessionId: String, gameName: String, type: String): Int {
        // Для экономии времени введу 3-й параметр.

        val userUid = fireBase.auth.uid
        val session = fireBase.firestore.document("games/$sessionId/").get().await()
        val opponentUid = (
                if (session.data?.get("pink_user") != userUid) session.data?.get("pink_user")
                else session.data?.get("cyan_user")
        ) as String


        return fireBase.firestore
            .document("games/$sessionId/game/${
                mapper.convertToAnalogGameName(gameName)
            }_${if (type == "user") userUid else opponentUid}")
            .get().await().data?.get("scope").toString().toInt()
    }



    //region addFriendInGame
    override suspend fun addFriendInGame(sessionId: String) {

        val userUid = fireBase.auth.uid
        if (userUid != null){

            val session = fireBase.firestore.document("games/$sessionId/").get().await()
            val opponentUid = (
                    if (session.data?.get("pink_user") != userUid) session.data?.get("pink_user")
                    else session.data?.get("cyan_user")
            ) as String

            // For Friend
            val userRequestFromFriend = hashMapOf(
                "uid" to "$userUid",
                "answerState" to false,
                "friendState" to false
            )

            val userRequestFromFriendPath = getFireStoreUserPath(
                userUid = opponentUid, pathName = "usersRequest", uid = userUid
            )

            try {
                fireBase.firestore.document(userRequestFromFriendPath)
                    .set(userRequestFromFriend)
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


            // For User
            val userRequestFromUser = hashMapOf(
                "uid" to opponentUid,
                "answerState" to false,
                "friendState" to false
            )

            val userRequestFromUserPath = getFireStoreUserPath(
                userUid = userUid, pathName = "usersRequest", uid = opponentUid
            )

            try {
                fireBase.firestore.document(userRequestFromUserPath)
                    .set(userRequestFromUser)
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
    //endregion

    // ######################

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





