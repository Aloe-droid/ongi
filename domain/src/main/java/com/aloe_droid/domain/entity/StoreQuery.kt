package com.aloe_droid.domain.entity

import java.util.UUID

data class StoreQuery(
    val id: String = UUID.randomUUID().toString(),
    val location: Location,
    val category: StoreQueryCategory = StoreQueryCategory.NONE,
    val sortType: StoreQuerySortType = StoreQuerySortType.FAVORITE,
    val distanceRange: StoreQueryDistance = StoreQueryDistance.NONE,
    val searchQuery: String = "",
    val page: Int = 0,
    val size: Int = PAGE_SIZE
) {
    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()

        map[LATITUDE] = location.latitude.toString()
        map[LONGITUDE] = location.longitude.toString()
        map[PAGE] = page.toString()
        map[SIZE] = size.toString()
        map[SORT_TYPE] = sortType.name
        if (category != StoreQueryCategory.NONE) map[CATEGORY] = category.name
        if (distanceRange != StoreQueryDistance.NONE) map[DISTANCE] = distanceRange.name
        if (searchQuery.isNotBlank()) map[KEYWORD] = searchQuery
        return map
    }

    companion object {
        private const val PAGE = "page"
        private const val SIZE = "size"
        private const val KEYWORD = "keyword"
        private const val CATEGORY = "category"
        private const val DISTANCE = "distanceRange"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
        private const val SORT_TYPE = "sortType"

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

enum class StoreQueryDistance() {
    M_5,
    K_1,
    K_3,
    K_5,
    K_10,
    K_15,
    NONE
}
