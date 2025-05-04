package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQueryCategory
import com.aloe_droid.domain.entity.StoreQueryDistance
import com.aloe_droid.domain.entity.StoreQuerySortType
import java.util.UUID


@Entity(tableName = "store_queries")
data class StoreQueryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val latitude: Double,
    val longitude: Double,
    val searchQuery: String,
    val category: StoreQueryCategory,
    val sortType: StoreQuerySortType,
    val distanceRange: StoreQueryDistance,
    val queryTime: Long,
) {
    companion object {
        fun StoreQuery.toStoreQueryEntity(): StoreQueryEntity {
            return StoreQueryEntity(
                latitude = location.latitude,
                longitude = location.longitude,
                searchQuery = searchQuery,
                category = category,
                sortType = sortType,
                distanceRange = distanceRange,
                queryTime = System.currentTimeMillis()
            )
        }

        fun StoreQueryEntity.isSameQuery(query: StoreQuery): Boolean {
            if (latitude != query.location.latitude) return false
            if (longitude != query.location.longitude) return false
            if (searchQuery != query.searchQuery) return false
            if (category != query.category) return false
            if (sortType != query.sortType) return false
            if (distanceRange != query.distanceRange) return false
            return true
        }
    }
}
