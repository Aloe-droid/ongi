package com.aloe_droid.data.repository.mapper

import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.domain.entity.Store

object StoreMapper {
    fun List<StoreDTO>.toStoreList(): List<Store> = map { it.toStore() }

    fun StoreDTO.toStore(): Store = Store(
        id = id,
        name = name,
        address = address,
        favoriteCount = favoriteCount,
        latitude = latitude,
        longitude = longitude,
        distance = distance,
        category = category,
        phone = phone,
        city = city,
        district = district,
        imageUrl = imageUrl
    )
}
