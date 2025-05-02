package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.user.AddressDTO
import com.aloe_droid.data.datasource.dto.user.IdDTO
import com.aloe_droid.data.datasource.dto.user.LikeDTO
import com.aloe_droid.data.datasource.dto.user.UserDTO
import com.aloe_droid.data.datasource.network.api.UserAPI
import com.aloe_droid.data.datasource.network.util.ApiExt.Companion.safeApiCallToFlow
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val userAPI: UserAPI) : UserDataSource {

    override fun findOrCreateUser(address: AddressDTO): Flow<UserDTO> = safeApiCallToFlow {
        userAPI.findOrCreateUser(address)
    }

    override fun deleteUser(idDTO: IdDTO): Flow<Boolean> = safeApiCallToFlow {
        userAPI.deleteUser(idDTO)
    }

    override fun getStoreLike(
        userId: UUID,
        storeId: UUID
    ): Flow<LikeDTO> = safeApiCallToFlow {
        userAPI.getStoreLike(userId = userId, storeId = storeId)
    }

    override fun toggleStoreLike(
        userId: UUID,
        storeId: UUID
    ): Flow<LikeDTO> = safeApiCallToFlow {
        userAPI.toggleStoreLike(userId = userId, storeId = storeId)
    }
}
