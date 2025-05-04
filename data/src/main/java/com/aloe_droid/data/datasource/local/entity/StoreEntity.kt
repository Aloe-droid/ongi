package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.domain.entity.Store
import java.util.UUID

@Entity(tableName = "stores", primaryKeys = ["id", "queryId"])
data class StoreEntity(
    val id: String,
    val queryId: String, // TODO("추후에 쿼리 별로 DB 캐시 정책")
    val name: String,
    val address: String,
    val favoriteCount: Int,
    val latitude: Double,
    val longitude: Double,
    val distance: Double,
    val category: String,
    val city: String,
    val district: String,
    val phone: String?,
    val imageUrl: String?,

    val pageNumber: Int,
    val orderIndex: Int,
) {
    companion object {
        fun List<StoreDTO>.toStoreEntityList(
            pageNumber: Int,
            queryId: String,
        ) = mapIndexed { index, store ->
            store.toStoreEntity(pageNumber = pageNumber, orderIndex = index, queryId = queryId)
        }

        fun StoreDTO.toStoreEntity(
            pageNumber: Int,
            orderIndex: Int,
            queryId: String,
        ) = StoreEntity(
            id = id.toString(),
            name = name,
            address = address,
            favoriteCount = favoriteCount,
            latitude = latitude,
            longitude = longitude,
            distance = distance,
            category = category,
            city = city,
            district = district,
            phone = phone,
            imageUrl = imageUrl,

            pageNumber = pageNumber,
            orderIndex = orderIndex,
            queryId = queryId,
        )

        fun StoreEntity.toStore() = Store(
            id = UUID.fromString(id),
            name = name,
            address = address,
            favoriteCount = favoriteCount,
            latitude = latitude,
            longitude = longitude,
            distance = distance,
            category = category,
            city = city,
            district = district,
            phone = phone,
            imageUrl = imageUrl
        )
    }
}
