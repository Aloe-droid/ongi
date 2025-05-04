package com.aloe_droid.domain.repository

import androidx.paging.PagingData
import com.aloe_droid.domain.entity.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getAllHistoriesPaged(): Flow<PagingData<SearchHistory>>

    suspend fun insert(history: SearchHistory)

    suspend fun deleteById(id: Long)

    suspend fun deleteAll()
}
