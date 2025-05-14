package com.aloe_droid.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aloe_droid.data.datasource.local.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Upsert
    suspend fun upsert(history: SearchHistoryEntity)

    @Query("SELECT * FROM search_histories ORDER BY timestamp DESC")
    fun getAllHistoriesPaged(): PagingSource<Int, SearchHistoryEntity>

    @Query("DELETE FROM search_histories WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM search_histories")
    suspend fun deleteAll()
}
