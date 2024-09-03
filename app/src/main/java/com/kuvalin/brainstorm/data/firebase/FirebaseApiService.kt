package com.kuvalin.brainstorm.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.data.mapper.BrainStormMapper
import com.kuvalin.brainstorm.data.model.GameStatisticDbModel
import com.kuvalin.brainstorm.domain.entity.AppCurrency
import com.kuvalin.brainstorm.domain.entity.ChatInfo
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.GameStatistic
import com.kuvalin.brainstorm.domain.entity.Message
import com.kuvalin.brainstorm.domain.entity.SocialData
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.entity.UserRequest
import com.kuvalin.brainstorm.domain.entity.WarStatistics
import com.kuvalin.brainstorm.globalClasses.GlobalConstVal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.random.Random

class FirebaseApiService @Inject constructor(
    private val fireBase: Firebase,
    private val mapper: BrainStormMapper
) : ApiService {


    /* ######################################### AUTH ########################################### */

    //region singInFirebase
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
    //endregion

    //region signUpFirebase
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
    //endregion

    //region resetPasswordFirebase
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
    //endregion

    override suspend fun authorizationCheckFirebase(): Boolean {
        return fireBase.auth.currentUser != null
    }

    override suspend fun signOutFirebase() {
        fireBase.auth.signOut()
    }

    override suspend fun getUserUid(): String {
        return fireBase.auth.uid.toString() // TODO ОБработать случай, когда ещё нет реги
    }

    //endregion ################################################################################# */




    /* ####################################### FIRESTORE ######################################## */

    // ###################### FIRESTORE_PATH
    //region getFireStorePath
    private fun getFireStorePath(
        userUid: String,
        pathName: String,
        gameName: String = "",
        uid: String = "", // Уникальное значение-идентификатор для коллекций
        chatId: String = ""
    ): String{
        return when(pathName){
            "userInfo" -> { "users/${userUid}/user_data/user_info/"}
            "socialData" -> {"users/${userUid}/user_data/social_data/"}
            "appCurrency" -> {"users/${userUid}/user_data/app_currency/"}
            "friendsInfo" -> {"users/${userUid}/friend_info/${uid}"}
            "userChats" -> {"users/${userUid}/chats/${uid}"}
            "chatFromFriend" -> {"chats/${chatId}/messages/"}
            "usersRequest" -> {"users/${userUid}/users_request/${uid}"}
            "gameStatistic" -> {"users/${userUid}/game/game_statistics/${uid}/${gameName}"}
            "gameStatistics" -> {"users/${userUid}/game/game_statistics/${uid}/"}
            "warStatistics" -> {"users/${userUid}/game/war_statistics/${uid}/war_statistics"}
            "gameResults" -> {"users/${userUid}/game/game_results/user_game_results/"}
            else -> ""
        }
    }
    //endregion
    // ######################



    // ###################### SEND

    // UserInfo
    //region sendUserInfoToFirestore
    override suspend fun sendUserInfoToFirestore(userInfo: UserInfo) {
        if (fireBase.auth.uid != null){
            val userInfoPath = getFireStorePath(fireBase.auth.uid.toString(), "userInfo")

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
    //endregion

    // SocialData
    //region sendSocialDataToFirestore
    override suspend fun sendSocialDataToFirestore(socialData: SocialData) {
        if (fireBase.auth.uid != null){
            val socialDataPath = getFireStorePath(fireBase.auth.uid.toString(), "socialData")

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
    //endregion

    // AppCurrency
    //region sendAppCurrencyToFirestore
    override suspend fun sendAppCurrencyToFirestore(appCurrency: AppCurrency) {
        if (fireBase.auth.uid != null){
            val appCurrencyPath = getFireStorePath(fireBase.auth.uid.toString(), "appCurrency")

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
    //endregion


    // ListFriends
    /**
     * Здесь я буду, как и в базе, разбивать данные на разные таблички, собирая их при получении.
     */
    //region sendFriendsToFirestore
    override suspend fun sendFriendsToFirestore(friend: Friend) {
        if (fireBase.auth.uid != null){
            val userUid = fireBase.auth.uid.toString()
            val friendInfoPath = getFireStorePath(
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


            friend.chatInfo?.let {
                sendChatInfoToFirestore(it)
            }

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
    //endregion


    // Chat
    //region sendChatInfoToFirestore
    override suspend fun sendChatInfoToFirestore(chatInfo: ChatInfo) {
        if (fireBase.auth.uid != null){
            val chatPath = getFireStorePath(
                userUid = fireBase.auth.uid.toString(),
                pathName = "userChats",
                uid = chatInfo.uid
            )

            try {
                fireBase.firestore.document(chatPath)
                    .set(mapper.mapEntityToFirebaseHashMapChat(chatInfo))
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
    //endregionп
    //region sendChatInfoToFirestore
    override suspend fun sendMessageToFirestore(message: Message, chatId: String) {

        val userUid = fireBase.auth.uid
        if (userUid != null){
            val chatPath = getFireStorePath(
                userUid = userUid, // не используется
                pathName = "chatFromFriend",
                chatId = chatId
            )

            try {
                fireBase.firestore.collection(chatPath)
                    .add(message)
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



    // GameStatistic
    //region sendGameStatisticToFirestore
    override suspend fun sendGameStatisticToFirestore(gameStatisticDbModel: GameStatisticDbModel) {
        if (fireBase.auth.uid != null){
            val gameStatisticPath = getFireStorePath(
                userUid = fireBase.auth.uid.toString(),
                pathName = "gameStatistic",
                uid = gameStatisticDbModel.uid,
                gameName = mapper.convertToAnalogGameName(gameStatisticDbModel.gameName)
            )

            try {
                fireBase.firestore.document(gameStatisticPath)
                    .set(mapper.mapDbModelToFirebaseHashMapGameStatistic(gameStatisticDbModel))
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


    // WarStatistic
    //region sendWarStatisticToFirestore
    override suspend fun sendWarStatisticToFirestore(warStatistics: WarStatistics) {

        if (fireBase.auth.uid != null){
            val warStatisticsPath = getFireStorePath(
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
    //endregion


    // Посылает решение о принятии дружбы другому пользователю
    //region updateUserRequest
    override suspend fun updateUserRequest(uidFriend: String, friendState: Boolean){

        val scope = CoroutineScope(Dispatchers.IO)

        val userUid = fireBase.auth.uid
        if (userUid != null) {

            val userRequestFromFriendPath = getFireStorePath(
                userUid = uidFriend, pathName = "usersRequest", uid = userUid
            )

            try {
                fireBase.firestore.document(userRequestFromFriendPath)
                    .update("answerState", true, "friendState", friendState)
                    //region Успех/ошибка
                    .addOnSuccessListener { documentReference ->
                        scope.launch { deleteUserRequest(uidFriend) }
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





    // ###################### GET
    //region getUserInfoFB
    override suspend fun getUserInfoFB(uid: String): UserInfo? {

        if (fireBase.auth.uid != null){
            val userInfoPath = getFireStorePath(uid, "userInfo")

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
    //endregion

    //region getFriendsFB
    override suspend fun getFriendsFB(uid: String): List<Friend>? { // Локаль

        val resultList = mutableListOf<Friend>()

        val userUid = fireBase.auth.uid
        if (userUid != null) {
            try {
                val friendInfoPath = getFireStorePath(userUid.toString(), "friendsInfo")
                val listFriendInfo = fireBase.firestore.collection(friendInfoPath).get().await()

                if (listFriendInfo.documents.size == 0) { return null }

                for (friendInfo in listFriendInfo){

                    val friendUid = friendInfo.data["uid"].toString()

                    Log.d("TEST_TEST", "$friendUid   <---- getFriendsFB -> friendUid")

                    val gameStatistic = getGameStatisticFB(friendUid)
                    val warStatistics = getWarStatisticsFB(friendUid)
                    val chatInfo = getChatInfoFB(friendUid)

                    Log.d("TEST_TEST", "$chatInfo   <---- getFriendsFB -> chatInfo")

                    resultList.add(
                        Friend(
                            uid = friendUid,
                            ownerUid = friendInfo.data["ownerUid"].toString(),
                            name = friendInfo.data["name"].toString(),
                            email = friendInfo.data["email"].toString(),
                            avatar = null, // TODO
                            country = friendInfo.data["country"].toString(),
                            chatInfo = chatInfo,
                            gameStatistic = gameStatistic,
                            warStatistics = warStatistics
                        )
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }

        }

        return if (resultList.size == 0) null else resultList
    }
    //endregion

    //region getGameStatisticFB
    override suspend fun getGameStatisticFB(uid: String): List<GameStatistic>? {

        val resultList = mutableListOf<GameStatistic>()
        if (fireBase.auth.uid != null){
            val userGameStatisticsPath = getFireStorePath(
                userUid = uid,
                pathName = "gameStatistics",
                uid = uid
            )

            try {
                val listGameStatistics = fireBase.firestore.collection(userGameStatisticsPath).get().await()

                if (listGameStatistics.documents.size == 0) { return null }

                for (document in listGameStatistics){
                    resultList.add(
                        GameStatistic(
                            uid = document.data["uid"].toString(),
                            gameName = document.data["gameName"].toString(),
                            maxGameScore = document.data["maxGameScore"].toString().toInt(),
                            avgGameScore = document.data["avgGameScore"].toString().toInt()
                        )
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }else {
            return null
        }

        return resultList
    }
    //endregion
    //region getWarStatisticsFB
    override suspend fun getWarStatisticsFB(uid: String): WarStatistics? {

        if (fireBase.auth.uid != null){
            val userWarStatisticsPath = getFireStorePath(
                userUid = uid,
                pathName = "warStatistics",
                uid = uid
            )

            try {
                val warStatistics = fireBase.firestore.document(userWarStatisticsPath).get().await().data

                if (!warStatistics.isNullOrEmpty()){
                    return WarStatistics(
                        uid = warStatistics["uid"].toString(),
                        winRate = warStatistics["winRate"].toString().toFloat(),
                        wins = warStatistics["wins"].toString().toInt(),
                        losses = warStatistics["losses"].toString().toInt(),
                        draws = warStatistics["draws"].toString().toInt(),
                        highestScore = warStatistics["highestScore"].toString().toInt()
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }

        return null
    }
    //endregion
    //region getUserRequests
    override suspend fun getUserRequests(): List<UserRequest>? {

        val userUid = fireBase.auth.uid
        val resultList = mutableListOf<UserRequest>()
        if (userUid != null){
            val userInfoPath = getFireStorePath(userUid, "usersRequest")

            try {
                // Получаем список запросов к пользователю в друзья
                val listUserRequests = fireBase.firestore.collection(userInfoPath).get().await()

                if (listUserRequests.documents.size == 0) { return null }

                // Собираем список запросов
                for (document in listUserRequests){
                    resultList.add(
                        UserRequest(
                            uid = document.data["uid"] as String,
                            sender = document.data["sender"] as Boolean,
                            chatId = document.data["chatId"] as String,
                            answerState = document.data["answerState"] as Boolean,
                            friendState = document.data["friendState"] as Boolean
                        )
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
                return null
            }
        }

        return resultList
    }
    //endregion

    //region getChatInfoFB
    override suspend fun getChatInfoFB(uid: String): ChatInfo? {

        // TODO Так-с, тут как-то я хреново оформил
        // СДЕЛАТЬ НУЛАБЕЛЬНЫМ И возвращать null с последующей ошибкой
        // Да и вообще как, блять, лучше это делать?!

        val userUid = fireBase.auth.uid
        if (userUid != null) {

            try {
                val chatInfoPath = getFireStorePath(userUid = userUid, pathName = "userChats", uid =  uid)
                val chatInfo = fireBase.firestore.document(chatInfoPath).get().await().data

                Log.d("TEST_TEST", "$chatInfo   <---- getChatInfoFB")

                return if (!chatInfo.isNullOrEmpty()){
                    ChatInfo(
                        uid = chatInfo["uid"].toString(),
                        chatId = chatInfo["chat_id"].toString()
                    )
                }else { null }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
                return null
            }

        } else { return null }

    }
    //endregion

    //region getAppCurrencyFB
    override suspend fun getAppCurrencyFB(uid: String): AppCurrency? {

        val userUid = fireBase.auth.uid
        if (userUid != null) {
            try {
                val appCurrencyPath = getFireStorePath(userUid.toString(), "appCurrency")
                val appCurrency = fireBase.firestore.document(appCurrencyPath).get().await().data

                if (!appCurrency.isNullOrEmpty()){
                    return AppCurrency(
                        id = GlobalConstVal.UNDEFINED_ID,
                        numberOfLives = GlobalConstVal.DEFAULT_LIVE_VALUE,
                        numberOfCoins = appCurrency["numberOfCoins"].toString().toInt()
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }

        }

        return null
    }
    //endregion
    //region getSocialDataFB
    override suspend fun getSocialDataFB(uid: String): SocialData? {

        val userUid = fireBase.auth.uid
        if (userUid != null) {

            try {
                val socialDataPath = getFireStorePath(userUid.toString(), "socialData")
                val socialData = fireBase.firestore.document(socialDataPath).get().await().data

                if (!socialData.isNullOrEmpty()){
                    return SocialData(
                        uid = socialData["uid"].toString(),
                        twitter = socialData["twitter"].toString(),
                        vk = socialData["vk"].toString(),
                        facebookConnect = socialData["facebookConnect"].toString().toBoolean(),
                    )
                }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }

        }

        return null
    }
    //endregion


    //region getListMessages
    override suspend fun getListMessages(chatId: String): StateFlow<List<Message>> {

        val userUid = fireBase.auth.uid
        val messagesFlow = MutableStateFlow(emptyList<Message>())

        if (userUid != null){
            val chatPath = getFireStorePath(
                userUid = userUid, // не используется
                pathName = "chatFromFriend",
                chatId = chatId
            )

            try {
                // Получаем список запросов к пользователю в друзья
                fireBase.firestore.collection(chatPath)
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            // Обработка ошибки
                            return@addSnapshotListener
                        }

                        val messages = mutableListOf<Message>()
                        snapshot?.documents?.forEach { message ->
                            messages.add(
                                Message(
                                    senderUid = message.data?.get("senderUid") as String,
                                    text = message.data!!["text"] as String,
                                    timestamp = message.data!!["timestamp"] as Long
                                )
                            )
                        }

                        messagesFlow.value = messages
                    }

            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error adding document", e)
            }
        }

        return messagesFlow
    }
    //endregion
    // ######################


    // ###################### DELETE
    // Удаляет запрос в дружбу (при любом ответе и успешном обновлении информации у другого юзера)
    //region deleteUserRequest
    override suspend fun deleteUserRequest(uidFriend: String){

        val userUid = fireBase.auth.uid
        if (userUid != null) {

            val userRequestFromUserPath = getFireStorePath(
                userUid = userUid, pathName = "usersRequest", uid = uidFriend
            )

            try {
                fireBase.firestore.document(userRequestFromUserPath)
                    .delete()
                    //region Успех/ошибка
                    .addOnSuccessListener {
                        Log.w("FIRESTORE_SEND", "Documents has been deleted")
                    }
                    .addOnFailureListener { e ->
                        Log.w("FIRESTORE_SEND", "Error delete document", e)
                    }
                //endregion
            } catch (e: Exception) {
                Log.w("FIRESTORE_SEND", "Error delete document", e)
            }
        }
    }
    //endregion
    // ######################



    // ###################### GAME

    //region FindTheGame
    override suspend fun findTheGame(): Triple<Boolean, String, String> {

        // ###################### ПЕРЕМЕННЫЕ
        var findFailed = false
        var gameSessionId = ""
        var opponentUid = ""

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
                return Triple(false, "", opponentUid)
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
                        listGames = value.data?.get("list_games") as MutableList<String>
                        opponentUid = (
                                if (findFailed)
                                    value.data?.get("pink_user")
                                else
                                    value.data?.get("cyan_user")
                                ).toString()

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

                return Triple(true, gameSessionId, opponentUid)
            }
            delay(1000)
            elapsedTime += 1000
        }

        // Если время истекло, отменяем прослушивание и возвращаем false и пустой id
        listenerRegistration.remove()
        return Triple(false, "", opponentUid)
    }
    //endregion

    // Отправляет в firebase актуальные значения SCORE (во время каждого изменения score)
    //region updateUserScopeInWarGame
    override suspend fun updateUserScopeInWarGame(sessionId: String, gameName: String, scope: Int){
        fireBase.firestore
            .document("games/$sessionId/game/${mapper.convertToAnalogGameName(gameName)}_${fireBase.auth.uid}")
            .set(hashMapOf("scope" to "$scope"))
            .await()
    }
    //endregion


    // Получает из firebase актуальные значения SCORE оппонента (во время каждого изменения score)
    //region getActualOpponentScopeFromWarGame
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
                value?.data?.get("scope")?.toString()?.toIntOrNull()?.let {newScope ->
                    mutableStateFlow.value = newScope
                }
            }
        // TODO Потом понять, как перевести в обычный поток. Да и вообще глянуть, где у меня поточная инфа
        return mutableStateFlow
    }
    //endregion


    // Нужна для получения конечных результатов игры в WarGameResults
    //region getScopeFromWarGame
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
    //endregion


    //region addFriendInGame
    override suspend fun addFriendInGame(sessionId: String) {

        val userUid = fireBase.auth.uid
        if (userUid != null){

            val session = fireBase.firestore.document("games/$sessionId/").get().await()
            val opponentUid = (
                    if (session.data?.get("pink_user") != userUid) session.data?.get("pink_user")
                    else session.data?.get("cyan_user")
            ) as String
            val chatId = "${userUid.substring(0, userUid.length/2)}${opponentUid.substring(opponentUid.length/2)}"


            // For Friend
            val userRequestFromFriend = hashMapOf(
                "uid" to "$userUid",
                "sender" to false,
                "chatId" to chatId,
                "answerState" to false,
                "friendState" to false
            )

            val userRequestFromFriendPath = getFireStorePath(
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
                "sender" to true,
                "chatId" to chatId,
                "answerState" to false,
                "friendState" to false
            )

            val userRequestFromUserPath = getFireStorePath(
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

    //endregion ################################################################################# */

}






