package com.aloe_droid.data.repository.page

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import androidx.room.withTransaction
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.data.datasource.dto.store.StorePage
import com.aloe_droid.data.datasource.local.dao.StoreDao
import com.aloe_droid.data.datasource.local.dao.StoreQueryCrossRefDao
import com.aloe_droid.data.datasource.local.database.OnGiDatabase
import com.aloe_droid.data.datasource.local.entity.StoreEntity
import com.aloe_droid.data.datasource.local.entity.StoreEntity.Companion.toStoreEntityList
import com.aloe_droid.data.datasource.local.entity.StoreQueryCrossRefEntity
import com.aloe_droid.data.datasource.network.source.StoreDataSource
import com.aloe_droid.domain.entity.StoreQuery
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class StoreRemoteMediator @AssistedInject constructor(
    @Assisted private val storeQuery: StoreQuery,
    @Assisted private val queryId: String,
    private val database: OnGiDatabase,
    private val storeDataSource: StoreDataSource,
) : RemoteMediator<Int, StoreEntity>() {

    private val storeDao: StoreDao = database.storeDao()
    private val refDao: StoreQueryCrossRefDao = database.storeRefDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoreEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> START_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                // 1. 쿼리 ID 기반 마지막 가게 참조 정보
                val ref: StoreQueryCrossRefEntity? = refDao.getLastRefForQuery(queryId = queryId)
                if (ref == null) return MediatorResult.Success(endOfPaginationReached = true)

                // 2. 참조된 가게 정보의 다음 키
                val nextKey: Int? = ref.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        // 3. 최종 변경된 쿼리
        val updatedQuery: StoreQuery = storeQuery.copy(page = page, size = state.config.pageSize)

        return runCatching {
            // 원격 데이터 수신
            val remotePage: StorePage = storeDataSource
                .getStoreList(storeQuery = updatedQuery)
                .first()
            val isEnd = !remotePage.hasNext || remotePage.stores.isEmpty()

            database.withTransaction {
                // 동일 쿼리에 대한 정보 삭제
                if (loadType == LoadType.REFRESH) {
                    deleteAllByQuery(queryId = queryId)
                }

                // 쿼리 기반 데이터 정의
                val prevKey: Int? = if (page == START_PAGE_INDEX) null else page - 1
                val nextKey: Int? = if (isEnd) null else page + 1
                val storeEntities: List<StoreEntity> = remotePage.stores.toStoreEntityList()
                val crossRefs: List<StoreQueryCrossRefEntity> = remotePage.stores
                    .mapIndexed { index: Int, store: StoreDTO ->
                        StoreQueryCrossRefEntity(
                            queryId = queryId,
                            storeId = store.id.toString(),
                            pageNumber = page,
                            orderIndex = index,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }

                // 데이터 저장
                storeDao.insertAll(stores = storeEntities)
                refDao.insertAll(crossRefs = crossRefs)
                MediatorResult.Success(endOfPaginationReached = isEnd)
            }
        }.getOrElse { throwable: Throwable ->
            MediatorResult.Error(throwable)
        }
    }

    @Transaction
    suspend fun deleteAllByQuery(queryId: String) {
        // 1. 쿼리에 대한 참조 삭제 2. 참조가 없어진 가게들 삭제
        refDao.deleteByQueryId(queryId = queryId)
        storeDao.deleteStoresWithoutReferences()
    }

    @AssistedFactory
    interface Factory {
        fun create(storeQuery: StoreQuery, queryId: String): StoreRemoteMediator
    }

    companion object {
        private const val START_PAGE_INDEX = 0
    }
}
