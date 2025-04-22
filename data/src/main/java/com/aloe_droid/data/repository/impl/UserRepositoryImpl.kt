package com.aloe_droid.data.repository.impl

import com.aloe_droid.data.common.Dispatcher
import com.aloe_droid.data.common.DispatcherType
import com.aloe_droid.data.datasource.dto.user.AddressDTO
import com.aloe_droid.data.datasource.dto.user.IdDTO
import com.aloe_droid.data.datasource.dto.user.UserDTO
import com.aloe_droid.data.datasource.network.source.UserDataSource
import com.aloe_droid.data.repository.mapper.UserMapper.toUser
import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override fun findOrCreateUser(address: String): Flow<User> = userDataSource
        .findOrCreateUser(addressDTO = AddressDTO(address))
        .map { userDTO: UserDTO -> userDTO.toUser() }
        .flowOn(ioDispatcher)

    override fun deleteUser(user: User): Flow<Boolean> = userDataSource
        .deleteUser(idDTO = IdDTO(user.id))
        .flowOn(ioDispatcher)

}
