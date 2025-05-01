package com.aloe_droid.data.datasource.network.source

import com.aloe_droid.data.datasource.dto.store.StorePage
import com.aloe_droid.domain.entity.StoreQuery
import kotlinx.coroutines.flow.Flow

interface StoreDataSource {

    fun getStoreList(storeQuery: StoreQuery): Flow<StorePage>

}
