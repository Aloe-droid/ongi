package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.UserRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FindOrCreateUserUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
    private val userRepository: UserRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Unit> = userStoreRepository.getUser().map { savedUser: User? ->
        savedUser?.address ?: UUID.randomUUID().toString()
    }.flatMapLatest { address: String ->
        userRepository.findOrCreateUser(address = address)
    }.map { user: User ->
        userStoreRepository.saveUser(user)
    }
}
