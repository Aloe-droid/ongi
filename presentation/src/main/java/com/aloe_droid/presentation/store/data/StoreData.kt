package com.aloe_droid.presentation.store.data

import androidx.compose.runtime.Stable
import com.aloe_droid.domain.entity.StoreEntity
import com.aloe_droid.presentation.store.data.MenuData.Companion.toMenuDataList
import com.aloe_droid.presentation.store.data.StoreDetailData.Companion.toStoreDetailData

@Stable
data class StoreData(
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

    val detail: StoreDetailData,
    val menuList: List<MenuData>,
    val isLikeStore: Boolean,
) {
    companion object {
        fun StoreEntity.toStoreData() = StoreData(
            name = store.name,
            address = store.address,
            favoriteCount = store.favoriteCount,
            latitude = store.latitude,
            longitude = store.longitude,
            distance = store.distance,
            category = store.category,
            city = store.city,
            district = store.district,
            phone = store.phone,
            imageUrl = store.imageUrl,
            detail = storeDetail.toStoreDetailData(),
            menuList = menuList.toMenuDataList(),
            isLikeStore = isLike
        )
    }
}
