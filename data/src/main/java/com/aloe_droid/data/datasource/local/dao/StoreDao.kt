package com.aloe_droid.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aloe_droid.data.datasource.local.entity.StoreEntity

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<StoreEntity>)

    @Query("SELECT * FROM stores WHERE queryId = :queryId ORDER BY pageNumber ASC, orderIndex ASC")
    fun getStores(queryId: String): PagingSource<Int, StoreEntity>

    @Query("SELECT * FROM stores WHERE id = :id")
    suspend fun getStoreById(id: String): StoreEntity

    @Update
    suspend fun updateStore(storeEntity: StoreEntity)

    @Query("DELETE FROM stores")
    suspend fun clearAll()

    @Query("DELETE FROM stores WHERE queryId = :queryId")
    suspend fun deleteStoresByQueryId(queryId: String)

}
