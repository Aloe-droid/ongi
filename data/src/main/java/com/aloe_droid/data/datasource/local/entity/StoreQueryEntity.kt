package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aloe_droid.data.datasource.local.util.Location.toScale
import com.aloe_droid.domain.entity.StoreQuery
import com.aloe_droid.domain.entity.StoreQueryCategory
import com.aloe_droid.domain.entity.StoreQueryDistance
import com.aloe_droid.domain.entity.StoreQuerySortType
import java.util.UUID


@Entity(tableName = "store_queries")
data class StoreQueryEntity(
    @PrimaryKey val id: String,
    val latitude: Double,
    val longitude: Double,
    val searchQuery: String,
    val category: StoreQueryCategory,
    val sortType: StoreQuerySortType,
    val distanceRange: StoreQueryDistance,
    val onlyFavorites: Boolean,
    val requestRoute: String,
    val queryTime: Long
) {
    companion object {
        fun StoreQuery.toStoreQueryEntity(requestRoute: String): StoreQueryEntity {
            return StoreQueryEntity(
                id = UUID.randomUUID().toString(),
                latitude = location.latitude.toScale(),
                longitude = location.longitude.toScale(),
                searchQuery = searchQuery,
                category = category,
                sortType = sortType,
                distanceRange = distanceRange,
                onlyFavorites = onlyFavorites,
                requestRoute = requestRoute,
                queryTime = System.currentTimeMillis()
            )
        }
    }
}
