package com.aloe_droid.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aloe_droid.data.datasource.local.entity.StoreQueryEntity
import com.aloe_droid.data.datasource.local.entity.StoreQueryEntity.Companion.toStoreQueryEntity
import com.aloe_droid.data.datasource.local.util.Location.toScale
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQueryCategory
import com.aloe_droid.domain.entity.StoreQueryDistance
import com.aloe_droid.domain.entity.StoreQuerySortType

@Dao
interface StoreQueryDao {

    @Query(
        """
    SELECT * FROM store_queries
    WHERE latitude = :latitude
    AND longitude = :longitude
    AND searchQuery = :searchQuery
    AND category = :category
    AND sortType = :sortType
    AND distanceRange = :distanceRange
    AND onlyFavorites = :onlyFavorites
    LIMIT 1
"""
    )
    suspend fun findQueryByFields(
        latitude: Double,
        longitude: Double,
        searchQuery: String,
        category: StoreQueryCategory,
        sortType: StoreQuerySortType,
        distanceRange: StoreQueryDistance,
        onlyFavorites: Boolean,
    ): StoreQueryEntity?

    suspend fun findQueryByStoreQuery(
        storeQuery: StoreQuery,
    ): StoreQueryEntity? = with(storeQuery) {
        return findQueryByFields(
            latitude = location.latitude.toScale(),
            longitude = location.longitude.toScale(),
            searchQuery = searchQuery,
            category = category,
            sortType = sortType,
            distanceRange = distanceRange,
            onlyFavorites = onlyFavorites,
        )
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuery(query: StoreQueryEntity): Long

    @Update
    suspend fun updateQuery(query: StoreQueryEntity)

    @Query("DELETE FROM store_queries WHERE id = :id")
    suspend fun deleteQueryById(id: String)

    suspend fun upsert(storeQuery: StoreQuery): StoreQueryEntity {
        val existing: StoreQueryEntity? = findQueryByStoreQuery(storeQuery = storeQuery)

        return if (existing != null) {
            val entity = existing.copy(queryTime = System.currentTimeMillis())
            updateQuery(entity)
            entity
        } else {
            val entity = storeQuery.toStoreQueryEntity()
            insertQuery(entity)
            entity
        }
    }
}
