package com.aloe_droid.domain.entity

data class HomeEntity(
    val bannerList: List<Banner>,
    val location: Location,
    val favoriteStoreList: List<Store>,
    val nearbyStoreList: List<Store>
)
