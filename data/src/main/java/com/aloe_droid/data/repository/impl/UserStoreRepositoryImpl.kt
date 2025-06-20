package com.aloe_droid.data.repository.impl

import com.aloe_droid.data.common.Dispatcher
import com.aloe_droid.data.common.DispatcherType
import com.aloe_droid.data.datasource.datastore.source.UserDatastore
import com.aloe_droid.data.repository.mapper.UserMapper.toUser
import com.aloe_droid.data.repository.mapper.UserMapper.toUserDTO
import com.aloe_droid.domain.entity.User
import com.aloe_droid.domain.repository.UserStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserStoreRepositoryImpl @Inject constructor(
    private val userDatastore: UserDatastore,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : UserStoreRepository {

    override suspend fun saveUser(user: User) = withContext(ioDispatcher) {
        userDatastore.saveUser(userDTO = user.toUserDTO())
    }

    override fun getUser(): Flow<User?> = userDatastore.getUser()
        .map { it?.toUser() }
        .flowOn(ioDispatcher)

    override suspend fun clearUser() = withContext(ioDispatcher) {
        userDatastore.clearUser()
    }

}
