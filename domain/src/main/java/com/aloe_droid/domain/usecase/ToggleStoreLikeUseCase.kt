package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.UserRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import java.util.UUID
import javax.inject.Inject

class ToggleStoreLikeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userStoreRepository: UserStoreRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(storeId: UUID): Flow<Boolean> = userStoreRepository.getUser()
        .filterNotNull()
        .flatMapLatest { user: User ->
            val userId: UUID = user.id
            userRepository.toggleStoreLike(userId = userId, storeId = storeId)
        }

}
