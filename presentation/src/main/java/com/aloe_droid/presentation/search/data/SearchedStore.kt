package com.aloe_droid.presentation.search.data

import androidx.paging.PagingData
import androidx.paging.map
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.filtered_store.data.StoreCategory
import java.util.UUID

data class SearchedStore(
    val id: UUID,
    val name: String,
    val category: StoreCategory,
    val distance: Double,
) {
    companion object {
        private const val KOREAN_FOOD = "한식"
        private const val WESTERN_FOOD = "양식"
        private const val JAPANESE_FOOD = "일식"
        private const val CHINESE_FOOD = "중식"
        private const val BAKERY = "베이커리"
        private const val RESTAURANT = "기타요식업"
        private const val BATH_HOUSE = "목욕업"
        private const val LAUNDRY = "세탁업"
        private const val HOTEL = "숙박업"
        private const val HAIR_SALON = "미용업"
        private const val USE = "이용업"
        private const val ETC = "기타비요식업"

        fun PagingData<Store>.toPagingSearchStore(): PagingData<SearchedStore> = map {
            it.toSearchedStore()
        }

        fun Store.toSearchedStore() = SearchedStore(
            id = id,
            name = name,
            category = category.toStoreCategory(),
            distance = distance
        )

        private fun String.toStoreCategory(): StoreCategory = when (this) {
            KOREAN_FOOD -> StoreCategory.KOREAN_FOOD
            WESTERN_FOOD -> StoreCategory.WESTERN_FOOD
            JAPANESE_FOOD -> StoreCategory.JAPANESE_FOOD
            CHINESE_FOOD -> StoreCategory.CHINESE_FOOD
            BAKERY -> StoreCategory.BAKERY
            RESTAURANT -> StoreCategory.RESTAURANT
            BATH_HOUSE -> StoreCategory.BATH_HOUSE
            LAUNDRY -> StoreCategory.LAUNDRY
            HOTEL -> StoreCategory.HOTEL
            HAIR_SALON -> StoreCategory.HAIR_SALON
            USE -> StoreCategory.HAIR_SALON
            ETC -> StoreCategory.ETC
            else -> StoreCategory.ETC
        }

        fun StoreCategory.toIconRes(): Int = when (this) {
            StoreCategory.KOREAN_FOOD -> R.drawable.korean_food
            StoreCategory.WESTERN_FOOD -> R.drawable.western_food
            StoreCategory.JAPANESE_FOOD -> R.drawable.japanese_food
            StoreCategory.CHINESE_FOOD -> R.drawable.chinese_food
            StoreCategory.BAKERY -> R.drawable.bakery
            StoreCategory.RESTAURANT -> R.drawable.restaurant
            StoreCategory.BATH_HOUSE -> R.drawable.bathhouse
            StoreCategory.LAUNDRY -> R.drawable.laundry
            StoreCategory.HOTEL -> R.drawable.hotel
            StoreCategory.HAIR_SALON -> R.drawable.hair_salon
            StoreCategory.ETC -> R.drawable.etc
            StoreCategory.NONE -> -1
        }
    }
}
