package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.user.AddressDTO
import com.aloe_droid.data.datasource.dto.user.IdDTO
import com.aloe_droid.data.datasource.dto.user.LikeDTO
import com.aloe_droid.data.datasource.dto.user.UserDTO
import com.aloe_droid.data.datasource.network.api.UserAPI
import com.aloe_droid.data.datasource.network.util.ApiUtil.safeApiCallToFlow
import com.aloe_droid.data.datasource.network.util.FlowUtil.retryOnIOException
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val userAPI: UserAPI) : UserDataSource {

    override fun findOrCreateUser(addressDTO: AddressDTO): Flow<UserDTO> = safeApiCallToFlow {
        userAPI.findOrCreateUser(addressDTO)
    }.retryOnIOException()

    override fun deleteUser(idDTO: IdDTO): Flow<Boolean> = safeApiCallToFlow {
        userAPI.deleteUser(idDTO)
    }.retryOnIOException()

    override fun getStoreLike(
        userId: UUID,
        storeId: UUID
    ): Flow<LikeDTO> = safeApiCallToFlow {
        userAPI.getStoreLike(userId = userId, storeId = storeId)
    }.retryOnIOException()

    override fun toggleStoreLike(
        userId: UUID,
        storeId: UUID
    ): Flow<LikeDTO> = safeApiCallToFlow {
        userAPI.toggleStoreLike(userId = userId, storeId = storeId)
    }.retryOnIOException()
}
