package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserStoreRepository {

    suspend fun saveUser(user: User)

    fun getUser(): Flow<User?>

    suspend fun clearUser()

}
