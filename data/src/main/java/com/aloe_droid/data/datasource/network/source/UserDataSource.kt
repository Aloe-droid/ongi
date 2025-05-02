package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.user.AddressDTO
import com.aloe_droid.data.datasource.dto.user.IdDTO
import com.aloe_droid.data.datasource.dto.user.LikeDTO
import com.aloe_droid.data.datasource.dto.user.UserDTO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserDataSource {

    fun findOrCreateUser(addressDTO: AddressDTO): Flow<UserDTO>

    fun deleteUser(idDTO: IdDTO): Flow<Boolean>

    fun getStoreLike(userId: UUID, storeId: UUID): Flow<LikeDTO>

    fun toggleStoreLike(userId: UUID, storeId: UUID): Flow<LikeDTO>

}
