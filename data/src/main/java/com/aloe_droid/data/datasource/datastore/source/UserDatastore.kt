package com.aloe_droid.data.datasource.datastore.source

import com.aloe_droid.data.datasource.dto.user.UserDTO


interface UserDatastore {

    suspend fun saveUser(userDTO: UserDTO)

    suspend fun getUser(): UserDTO?

    suspend fun clearUser()

}
