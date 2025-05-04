package com.aloe_droid.data.repository.page

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.data.datasource.dto.store.StorePage
import com.aloe_droid.data.datasource.local.dao.StoreDao
import com.aloe_droid.data.datasource.local.dao.StoreKeyDao
import com.aloe_droid.data.datasource.local.database.OnGiDatabase
import com.aloe_droid.data.datasource.local.entity.StoreEntity
import com.aloe_droid.data.datasource.local.entity.StoreEntity.Companion.toStoreEntityList
import com.aloe_droid.data.datasource.local.entity.StoreKeyEntity
import com.aloe_droid.data.datasource.network.source.StoreDataSource
import com.aloe_droid.domain.entity.StoreQuery
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class StoreRemoteMediator @AssistedInject constructor(
    @Assisted private val storeQuery: StoreQuery,
    private val database: OnGiDatabase,
    private val storeDataSource: StoreDataSource,
) : RemoteMediator<Int, StoreEntity>() {

    private val storeDao: StoreDao = database.storeDao()
    private val storeKeyDao: StoreKeyDao = database.storeKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoreEntity>
    ): MediatorResult {
        val query: StoreQuery? = makeNewQuery(loadType = loadType, state = state)
        if (query == null) {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        return runCatching {
            val remotePage: StorePage = storeDataSource.getStoreList(storeQuery = query).first()
            val isEndOfPagination: Boolean = remotePage.stores.isEmpty() || !remotePage.hasNext

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    storeDao.clearAll()
                    storeKeyDao.clearAll()
                }

                val prevKey = if (query.page == START_PAGE_INDEX) null else query.page - 1
                val nextKey = if (isEndOfPagination) null else query.page + 1
                val storeKeyEntities = remotePage.stores.map { store: StoreDTO ->
                    StoreKeyEntity(
                        storeId = store.id.toString(),
                        queryId = query.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }.toList()
                storeKeyDao.insertAll(storeKeys = storeKeyEntities)

                val storeEntities = remotePage.stores.toStoreEntityList(
                    pageNumber = query.page,
                    queryId = query.id
                )

                storeDao.insertAll(stores = storeEntities)
            }

            if (loadType == LoadType.REFRESH) {
                delay(REFRESH_DELAY)
            }

            MediatorResult.Success(endOfPaginationReached = isEndOfPagination)
        }.getOrElse { throwable ->
            println(throwable)
            MediatorResult.Error(throwable)
        }
    }

    private suspend fun makeNewQuery(
        loadType: LoadType,
        state: PagingState<Int, StoreEntity>
    ): StoreQuery? {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> START_PAGE_INDEX
            LoadType.PREPEND -> return null
            LoadType.APPEND -> {
                val lastItem: StoreEntity = state.lastItemOrNull() ?: return null
                val storeKey: StoreKeyEntity? = storeKeyDao.getStoreKey(lastItem.id)
                storeKey?.nextKey ?: return null
            }
        }
        return storeQuery.copy(page = page, size = state.config.pageSize)
    }

    @AssistedFactory
    interface Factory {
        fun create(storeQuery: StoreQuery): StoreRemoteMediator
    }

    companion object {
        private const val START_PAGE_INDEX = 0
        private const val REFRESH_DELAY = 100L
    }
}
