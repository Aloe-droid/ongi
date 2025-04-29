package com.aloe_droid.presentation.home.data

import androidx.compose.runtime.Stable
import com.aloe_droid.domain.entity.Store
import java.util.UUID

@Stable
data class StoreData(
    val id: UUID,
    val name: String,
    val city: String,
    val district: String,
    val distance: Double,
    val favoriteCount: Int,
    val imageUrl: String?
) {
    companion object {
        fun List<Store>.toStoreData(): List<StoreData> = map { it.toStoreData() }
        fun Store.toStoreData() = StoreData(
            id = id,
            name = name,
            city = city,
            district = district,
            distance = distance,
            favoriteCount = favoriteCount,
            imageUrl = imageUrl
        )
    }
}
