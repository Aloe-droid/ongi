package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.data.datasource.network.api.StoreAPI
import com.aloe_droid.data.datasource.network.util.ApiExt.Companion.safeApiCallToFlow
import com.aloe_droid.domain.entity.StoreQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreDataSourceImpl @Inject constructor(private val storeAPI: StoreAPI) : StoreDataSource {

    override fun getStoreList(storeQuery: StoreQuery): Flow<List<StoreDTO>> = safeApiCallToFlow {
        storeAPI.getStores(storeQuery.toQueryMap())
    }

}
