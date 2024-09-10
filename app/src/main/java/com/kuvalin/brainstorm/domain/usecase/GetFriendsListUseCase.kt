package com.kuvalin.brainstorm.domain.usecase

import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetFriendsListUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository,
    private val getUserUidUseCase: GetUserUidUseCase
) {
    suspend operator fun invoke(uid: String = ""): List<Friend>? {
        val currentUid = uid.ifEmpty { getUserUidUseCase.invoke() }
        return brainStormRepository.getFriendList(currentUid)
    }
}