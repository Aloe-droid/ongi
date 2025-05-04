package com.aloe_droid.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aloe_droid.data.datasource.local.dao.SearchHistoryDao
import com.aloe_droid.data.datasource.local.dao.StoreDao
import com.aloe_droid.data.datasource.local.dao.StoreKeyDao
import com.aloe_droid.data.datasource.local.dao.StoreQueryDao
import com.aloe_droid.data.datasource.local.entity.SearchHistoryEntity
import com.aloe_droid.data.datasource.local.entity.StoreEntity
import com.aloe_droid.data.datasource.local.entity.StoreKeyEntity
import com.aloe_droid.data.datasource.local.entity.StoreQueryEntity

@Database(
    entities = [
        StoreEntity::class,
        StoreKeyEntity::class,
        StoreQueryEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1
)
abstract class OnGiDatabase : RoomDatabase() {
    abstract fun storeDao(): StoreDao
    abstract fun storeKeyDao(): StoreKeyDao
    abstract fun storeQueryDao(): StoreQueryDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}
