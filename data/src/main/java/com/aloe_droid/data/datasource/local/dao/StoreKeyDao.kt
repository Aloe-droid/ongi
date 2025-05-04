package com.aloe_droid.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aloe_droid.data.datasource.local.entity.StoreKeyEntity

@Dao
interface StoreKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(storeKeys: List<StoreKeyEntity>)

    @Query("SELECT * FROM store_keys WHERE storeId = :storeId")
    suspend fun getStoreKey(storeId: String): StoreKeyEntity?

    @Query("DELETE FROM store_keys")
    suspend fun clearAll()

    @Query("DELETE FROM store_keys WHERE queryId = :queryId")
    suspend fun deleteStoreKeysByQueryId(queryId: String)

}
