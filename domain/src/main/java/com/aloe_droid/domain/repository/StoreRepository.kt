package com.aloe_droid.domain.repository

import androidx.paging.PagingData
import com.aloe_droid.domain.entity.Menu
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreDetail
import com.aloe_droid.domain.entity.StoreQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import java.util.UUID

interface StoreRepository {
    fun getStoreList(storeQuery: StoreQuery): Flow<List<Store>>

    fun getStoreStream(storeQuery: StoreQuery): Flow<PagingData<Store>>

    fun getStore(id: UUID, latitude: Double, longitude: Double): Flow<Store>

    fun getStoreDetail(id: UUID): Flow<StoreDetail>

    fun getStoreMenus(id: UUID): Flow<List<Menu>>

    suspend fun updateStoreFavoriteCount(id: UUID, isLike: Boolean)

    fun getStoreCount(): Flow<Long>

    fun getStoreSyncTime(): Flow<Instant>
}
