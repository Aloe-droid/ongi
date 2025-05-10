package com.aloe_droid.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aloe_droid.data.datasource.local.data.StoresWithQuery
import com.aloe_droid.data.datasource.local.entity.StoreEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<StoreEntity>)

    @Transaction
    @Query(
        """
        SELECT *
        FROM stores
        INNER JOIN store_query_cross_ref
        ON stores.id = store_query_cross_ref.storeId
        WHERE store_query_cross_ref.queryId = :queryId
        ORDER BY store_query_cross_ref.pageNumber ASC, store_query_cross_ref.orderIndex ASC
    """
    )
    fun getStoresByQueryId(queryId: String): PagingSource<Int, StoreEntity>

    @Query(
        """
        SELECT *
        FROM stores
        INNER JOIN store_query_cross_ref
        ON stores.id = store_query_cross_ref.storeId
        WHERE store_query_cross_ref.queryId = :queryId
        ORDER BY store_query_cross_ref.pageNumber ASC, store_query_cross_ref.orderIndex ASC
        LIMIT :count
    """
    )
    fun getTopStores(queryId: String, count: Int): Flow<List<StoreEntity>>

    fun getTopStoresWithQueryId(
        queryId: String,
        count: Int
    ): Flow<StoresWithQuery> = getTopStores(queryId = queryId, count = count)
        .map { storeEntities: List<StoreEntity> ->
            StoresWithQuery(storeList = storeEntities, queryId = queryId)
        }.distinctUntilChanged()

    @Query("SELECT * FROM stores WHERE id = :id")
    suspend fun getStoreById(id: String): StoreEntity

    @Query("SELECT * FROM stores WHERE id = :id")
    fun getStoreFlowById(id: String): Flow<StoreEntity>

    @Update
    suspend fun updateStore(storeEntity: StoreEntity)

    @Query("DELETE FROM stores")
    suspend fun clearAll()

    @Query(
        """
    DELETE FROM stores 
    WHERE id NOT IN (
        SELECT storeId 
        FROM store_query_cross_ref
    )
"""
    )
    suspend fun deleteStoresWithoutReferences(): Int
}
