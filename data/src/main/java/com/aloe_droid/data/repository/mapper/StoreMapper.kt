package com.aloe_droid.data.repository.mapper

import com.aloe_droid.data.datasource.dto.store.MenuDTO
import com.aloe_droid.data.datasource.dto.store.StoreDTO
import com.aloe_droid.data.datasource.dto.store.StoreDetailDTO
import com.aloe_droid.domain.entity.Menu
import com.aloe_droid.domain.entity.Store
import com.aloe_droid.domain.entity.StoreDetail

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

    fun StoreDetailDTO.toStoreDetail() = StoreDetail(
        hasParking = hasParking,
        hasTakeout = hasTakeout,
        hasDelivery = hasDelivery,
        hasReservation = hasReservation,
        hasDividedRestroom = hasDividedRestroom,
        allowsPets = allowsPets,
        hasWifi = hasWifi,
        hasKidsFacility = hasKidsFacility,
        allowsGroup = allowsGroup
    )

    fun List<MenuDTO>.toMenuList(): List<Menu> = map { it.toMenu() }

    fun MenuDTO.toMenu() = Menu(
        id = id,
        name = name,
        price = price
    )
}
