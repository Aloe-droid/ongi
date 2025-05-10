package com.aloe_droid.domain.entity

import java.util.UUID

data class StoreQuery(
    val userId: UUID,
    val location: Location,
    val category: StoreQueryCategory = StoreQueryCategory.NONE,
    val sortType: StoreQuerySortType = StoreQuerySortType.FAVORITE,
    val distanceRange: StoreQueryDistance = StoreQueryDistance.NONE,
    val searchQuery: String = "",
    val onlyFavorites: Boolean = false,
    val page: Int = 0,
    val size: Int = PAGE_SIZE
) {
    fun toQueryMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        map[USER_ID] = userId.toString()
        map[LATITUDE] = location.latitude
        map[LONGITUDE] = location.longitude
        map[PAGE] = page
        map[SIZE] = size
        map[SORT_TYPE] = sortType.name
        map[ONLY_FAVORITES] = onlyFavorites

        if (category != StoreQueryCategory.NONE) map[CATEGORY] = category
        if (distanceRange != StoreQueryDistance.NONE) map[DISTANCE] = distanceRange
        if (searchQuery.isNotBlank()) map[KEYWORD] = searchQuery
        return map
    }

    companion object {
        private const val PAGE = "page"
        private const val SIZE = "size"

        private const val USER_ID = "userId"
        private const val KEYWORD = "keyword"
        private const val CATEGORY = "category"
        private const val DISTANCE = "distanceRange"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
        private const val SORT_TYPE = "sortType"
        private const val ONLY_FAVORITES = "onlyFavorites"

        private const val PAGE_SIZE = 20
    }
}

enum class StoreQuerySortType {
    NAME,
    FAVORITE,
    DISTANCE
}

enum class StoreQueryCategory {
    KOREAN_FOOD,
    WESTERN_FOOD,
    JAPANESE_FOOD,
    CHINESE_FOOD,
    BAKERY,
    RESTAURANT,
    BATH_HOUSE,
    LAUNDRY,
    HOTEL,
    HAIR_SALON,
    ETC,
    NONE
}

enum class StoreQueryDistance(val maxKm: Float) {
    M_5(0.5f),
    K_1(1f),
    K_3(3f),
    K_5(5f),
    K_10(10f),
    K_15(15f),
    K_20(20f),
    K_30(30f),
    K_40(40f),
    K_50(50f),
    K_60(60f),
    NONE(-1f)
}
