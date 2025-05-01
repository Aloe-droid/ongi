package com.aloe_droid.presentation.filtered_store.data

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class StoreFilter(
    val category: StoreCategory = StoreCategory.NONE,
    val sortType: StoreSortType = StoreSortType.FAVORITE,
    val distanceRange: StoreDistanceRange = StoreDistanceRange.K_5,
    val searchQuery: String = ""
)
