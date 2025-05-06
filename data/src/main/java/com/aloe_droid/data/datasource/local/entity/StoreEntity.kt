package com.aloe_droid.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.domain.entity.Store
import java.util.UUID

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey val id: String,
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
    val imageUrl: String?
) {
    companion object {
        fun List<StoreDTO>.toStoreEntityList() = map { store ->
            store.toStoreEntity()
        }

        fun List<StoreEntity>.toStoreList() = map { storeEntity ->
            storeEntity.toStore()
        }

        fun StoreDTO.toStoreEntity() = StoreEntity(
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
