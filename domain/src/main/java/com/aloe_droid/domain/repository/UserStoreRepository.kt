package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.User

interface UserStoreRepository {

    suspend fun saveUser(user: User)

    suspend fun getUser(): User?

    suspend fun clearUser()

}
