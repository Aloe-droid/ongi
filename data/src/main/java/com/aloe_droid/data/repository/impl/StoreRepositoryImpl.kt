package com.aloe_droid.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aloe_droid.data.common.Dispatcher
import com.aloe_droid.data.common.DispatcherType
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.data.datasource.dto.store.StorePage
import com.aloe_droid.data.datasource.network.source.StoreDataSource
import com.aloe_droid.data.datasource.network.source.StorePagingSource
import com.aloe_droid.data.repository.mapper.StoreMapper.toStore
import com.aloe_droid.data.repository.mapper.StoreMapper.toStoreList
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.repository.StoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeDataSource: StoreDataSource,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : StoreRepository {

    override fun getStoreList(storeQuery: StoreQuery): Flow<List<Store>> = storeDataSource
        .getStoreList(storeQuery)
        .map { storePage: StorePage -> storePage.stores.toStoreList() }
        .flowOn(ioDispatcher)

    override fun getStoreStream(storeQuery: StoreQuery): Flow<PagingData<Store>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                StorePagingSource(storeDataSource = storeDataSource, storeQuery = storeQuery)
            }
        ).flow.map { pagingData: PagingData<StoreDTO> ->
            pagingData.map { storeDTO: StoreDTO ->
                storeDTO.toStore()
            }
        }.flowOn(ioDispatcher)
    }
}
