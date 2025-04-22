package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun findOrCreateUser(address: String): Flow<User>

    fun deleteUser(user: User): Flow<Boolean>

}
