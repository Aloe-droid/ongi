package com.aloe_droid.data.datasource.dto.store

import com.aloe_droid.data.datasource.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class StoreDTO(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
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
)
