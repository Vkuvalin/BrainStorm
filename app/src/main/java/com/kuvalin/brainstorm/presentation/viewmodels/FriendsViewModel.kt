package com.kuvalin.brainstorm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.entity.ListOfMessages
import com.kuvalin.brainstorm.domain.entity.UserInfo
import com.kuvalin.brainstorm.domain.usecase.AddFriendUseCase
import com.kuvalin.brainstorm.domain.usecase.DeleteUserRequestFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetFriendsListUseCase
import com.kuvalin.brainstorm.domain.usecase.GetGameStatisticsFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserInfoFBUseCase
import com.kuvalin.brainstorm.domain.usecase.GetUserRequestsUseCase
import com.kuvalin.brainstorm.domain.usecase.GetWarStatisticFBUseCase
import com.kuvalin.brainstorm.domain.usecase.UpdateUserRequestFBUseCase
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    private val getUserRequestsUseCase: GetUserRequestsUseCase,
    private val getUserInfoFBUseCase: GetUserInfoFBUseCase,

    private val getGameStatisticsFBUseCase: GetGameStatisticsFBUseCase,
    private val getWarStatisticFBUseCase: GetWarStatisticFBUseCase,
    private val getFriendsListUseCase: GetFriendsListUseCase,

    private val addFriendUseCase: AddFriendUseCase,
    private val updateUserRequestFBUseCase: UpdateUserRequestFBUseCase,

    private val deleteUserRequestFBUseCase: DeleteUserRequestFBUseCase
): ViewModel() {

    val getUserRequests = getUserRequestsUseCase
    val getUserInfoFB = getUserInfoFBUseCase

    private val addFriend = addFriendUseCase
    private val getGameStatisticsFB = getGameStatisticsFBUseCase
    private val getWarStatisticFB = getWarStatisticFBUseCase

    val getFriendsList = getFriendsListUseCase

    val updateUserRequestFB = updateUserRequestFBUseCase
    val deleteUserRequestFB = deleteUserRequestFBUseCase

    suspend fun addFriend(userInfo: UserInfo) {

        addFriend.invoke(
            Friend(
                uid = userInfo.uid,
                name = userInfo.name,
                email = userInfo.email,
                avatar = null, // TODO
                country = userInfo.country,
                listOfMessages = ListOfMessages(userInfo.uid, null),
                gameStatistic = getGameStatisticsFB.invoke(userInfo.uid),
                warStatistics = getWarStatisticFB.invoke(userInfo.uid)
            )
        )
    }



}



