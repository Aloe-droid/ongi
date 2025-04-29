package com.aloe_droid.presentation.store.data

data class StoreFilter (
    val category: StoreCategory = StoreCategory.NONE,
    val sortType: StoreSortType = StoreSortType.FAVORITE,
    val distanceRange: StoreDistanceRange = StoreDistanceRange.NONE,
    val searchQuery: String = ""
)
