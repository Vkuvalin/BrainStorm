package com.kuvalin.brainstorm.domain.usecase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kuvalin.brainstorm.domain.entity.Friend
import com.kuvalin.brainstorm.domain.repository.BrainStormRepository
import javax.inject.Inject


class GetFriendsListUseCase @Inject constructor(
    private val brainStormRepository: BrainStormRepository
) {
    suspend operator fun invoke(uid: String = ""): List<Friend>? {
        // TODO мне нужен тут uid? Это же чисто локальный метод
        val currentUid = uid.ifEmpty { Firebase.auth.uid ?: "zero_user_uid" }
        return brainStormRepository.getFriendList(currentUid)
    }
}