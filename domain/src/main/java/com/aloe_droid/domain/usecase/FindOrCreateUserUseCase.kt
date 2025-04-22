package com.aloe_droid.domain.usecase

import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.UserRepository
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FindOrCreateUserUseCase @Inject constructor(
    private val userStoreRepository: UserStoreRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Unit> = flow {
        val savedUser: User? = userStoreRepository.getUser()
        val userAddress: String = savedUser?.address ?: UUID.randomUUID().toString()
        val flow: Flow<Unit> = userRepository.findOrCreateUser(userAddress).map { user: User ->
            userStoreRepository.saveUser(user)
        }
        emitAll(flow)
    }
}
