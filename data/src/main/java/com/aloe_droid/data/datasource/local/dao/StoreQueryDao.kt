package com.aloe_droid.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aloe_droid.data.datasource.local.entity.StoreQueryEntity
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
    LIMIT 1
"""
    )
    suspend fun findQueryByFields(
        latitude: Double,
        longitude: Double,
        searchQuery: String,
        category: StoreQueryCategory,
        sortType: StoreQuerySortType,
        distanceRange: StoreQueryDistance
    ): StoreQueryEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuery(query: StoreQueryEntity): Long

    @Query("DELETE FROM store_queries WHERE id = :id")
    suspend fun deleteQueryById(id: String)

    @Query("""
    SELECT * FROM store_queries
    ORDER BY queryTime DESC
    LIMIT 1
""")
    suspend fun getLastUsedQuery(): StoreQueryEntity?
}
