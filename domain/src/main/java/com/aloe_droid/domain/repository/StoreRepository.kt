package com.aloe_droid.domain.repository

import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreQuery
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getStoreList(storeQuery: StoreQuery): Flow<List<Store>>
}
