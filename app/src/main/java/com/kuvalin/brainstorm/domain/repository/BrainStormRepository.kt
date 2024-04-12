package com.kuvalin.brainstorm.domain.repository

import com.kuvalin.brainstorm.domain.entity.User

interface BrainStormRepository {
    
    suspend fun addFriendUseCase(user: User)
    suspend fun addGameStatisticUseCase()
    suspend fun addMessageFromFriendUseCase()
    suspend fun addSettingsMusicUseCase()
    suspend fun addSettingsVibrateUseCase()
    suspend fun addUserInfoUseCase()
    suspend fun addUserPhotoUseCase()
    suspend fun addWarStatisticUseCase()
    
    suspend fun getFriendsListUseCase(): List<User>
    suspend fun getFriendStatisticUseCase()
    suspend fun getFriendUseCase(uid: String): User
    suspend fun getListChatsUseCase()
    suspend fun getListFriendsStatisticsUseCase()
    suspend fun getListGamesStatisticsUseCase()
    suspend fun getListRequestsUseCase()
    suspend fun getMessageFromFriendUseCase()
    suspend fun getSettingsMusicUseCase()
    suspend fun getSettingsVibrateUseCase()
    suspend fun getUserInfoUseCase()
    suspend fun getUserPhotoUseCase()
    suspend fun getWarsStatisticUseCase()

}