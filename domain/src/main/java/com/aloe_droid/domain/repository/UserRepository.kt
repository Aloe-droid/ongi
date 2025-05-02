package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {

    fun findOrCreateUser(address: String): Flow<User>

    fun deleteUser(user: User): Flow<Boolean>

    fun getStoreLike(userId: UUID, storeId: UUID): Flow<Boolean>

    fun toggleStoreLike(userId: UUID, storeId: UUID): Flow<Boolean>
}
