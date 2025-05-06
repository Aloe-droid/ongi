package com.aloe_droid.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aloe_droid.data.datasource.local.entity.StoreQueryCrossRefEntity

@Dao
interface StoreQueryCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<StoreQueryCrossRefEntity>)

    @Query("SELECT * FROM store_query_cross_ref WHERE storeId = :storeId AND queryId = :queryId")
    suspend fun getCrossRef(storeId: String, queryId: String): StoreQueryCrossRefEntity?

    @Query("DELETE FROM store_query_cross_ref")
    suspend fun clearAll()

    @Query("DELETE FROM store_query_cross_ref WHERE queryId = :queryId")
    suspend fun deleteByQueryId(queryId: String) : Int

    @Query(
        """
    SELECT * 
    FROM store_query_cross_ref
    WHERE queryId = :queryId
    ORDER BY pageNumber DESC, orderIndex DESC
    LIMIT 1
"""
    )
    suspend fun getLastRefForQuery(queryId: String): StoreQueryCrossRefEntity?

    @Query("""
    SELECT storeId 
    FROM store_query_cross_ref 
    WHERE queryId = :queryId
    GROUP BY storeId 
    HAVING COUNT(*) = 1
""")
    suspend fun findDeletableStoreIdsForQuery(queryId: String): List<String>
}
