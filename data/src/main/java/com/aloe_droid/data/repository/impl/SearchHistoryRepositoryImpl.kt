package com.aloe_droid.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aloe_droid.data.common.Dispatcher
import com.aloe_droid.data.common.DispatcherType
import com.aloe_droid.data.datasource.local.dao.SearchHistoryDao
import com.aloe_droid.data.datasource.local.entity.SearchHistoryEntity
import com.aloe_droid.data.repository.mapper.SearchHistoryMapper.toSearchHistory
import com.aloe_droid.data.repository.mapper.SearchHistoryMapper.toSearchHistoryEntity
import com.aloe_droid.domain.entity.SearchHistory
import com.aloe_droid.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    @Dispatcher(DispatcherType.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchHistoryRepository {

    override fun getAllHistoriesPaged(): Flow<PagingData<SearchHistory>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_SIZE),
            pagingSourceFactory = {  searchHistoryDao.getAllHistoriesPaged() }
        ).flow.map { pagingData: PagingData<SearchHistoryEntity> ->
            pagingData.map { searchHistoryEntity: SearchHistoryEntity ->
                searchHistoryEntity.toSearchHistory()
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun insert(history: SearchHistory) = withContext(ioDispatcher) {
        searchHistoryDao.insert(history.toSearchHistoryEntity())
    }

    override suspend fun deleteById(id: Long) = withContext(ioDispatcher) {
        searchHistoryDao.deleteById(id = id)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        searchHistoryDao.deleteAll()
    }

    companion object {
        private const val DEFAULT_SIZE: Int = 20
    }

}
