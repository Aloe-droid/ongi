package com.aloe_droid.data.datasource.datastore.source

import com.aloe_droid.data.datasource.dto.user.UserDTO
import kotlinx.coroutines.flow.Flow


interface UserDatastore {

    suspend fun saveUser(userDTO: UserDTO)

    fun getUser(): Flow<UserDTO?>

    suspend fun clearUser()

}
