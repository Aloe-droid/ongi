package com.aloe_droid.domain.entity

data class StoreEntity(
    val isLike: Boolean,
    val store: Store,
    val storeDetail: StoreDetail,
    val menuList: List<Menu>
)
