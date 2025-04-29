package com.aloe_droid.presentation.home.data

import androidx.compose.runtime.Immutable
import com.aloe_droid.presentation.R
import com.aloe_droid.presentation.store.data.StoreCategory
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class CategoryData(
    val imageRes: Int,
    val nameRes: Int,
    val storeCategory: StoreCategory
) {

    override fun toString(): String {
        return "CategoryData(storeCategory=$storeCategory)"
    }

    companion object {
        val CategoryList: List<CategoryData> = listOf(
            CategoryData(
                imageRes = R.drawable.korean_food,
                nameRes = R.string.category_korean,
                storeCategory = StoreCategory.KOREAN_FOOD
            ),
            CategoryData(
                imageRes = R.drawable.western_food,
                nameRes = R.string.category_western,
                storeCategory = StoreCategory.WESTERN_FOOD
            ),
            CategoryData(
                imageRes = R.drawable.japanese_food,
                nameRes = R.string.category_japanese,
                storeCategory = StoreCategory.JAPANESE_FOOD
            ),
            CategoryData(
                imageRes = R.drawable.chinese_food,
                nameRes = R.string.category_chinese,
                storeCategory = StoreCategory.CHINESE_FOOD
            ),
            CategoryData(
                imageRes = R.drawable.bakery,
                nameRes = R.string.category_bakery,
                storeCategory = StoreCategory.BAKERY
            ),
            CategoryData(
                imageRes = R.drawable.restaurant,
                nameRes = R.string.category_restaurant,
                storeCategory = StoreCategory.RESTAURANT
            ),
            CategoryData(
                imageRes = R.drawable.bathhouse,
                nameRes = R.string.category_bath,
                storeCategory = StoreCategory.BATH_HOUSE
            ),
            CategoryData(
                imageRes = R.drawable.laundry,
                nameRes = R.string.category_laundry,
                storeCategory = StoreCategory.LAUNDRY
            ),
            CategoryData(
                imageRes = R.drawable.hotel,
                nameRes = R.string.category_hotel,
                storeCategory = StoreCategory.HOTEL
            ),
            CategoryData(
                imageRes = R.drawable.hair_salon,
                nameRes = R.string.category_hair,
                storeCategory = StoreCategory.HAIR_SALON
            ),
            CategoryData(
                imageRes = R.drawable.etc,
                nameRes = R.string.category_etc,
                storeCategory = StoreCategory.ETC
            ),
        )
    }
}
