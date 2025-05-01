package com.aloe_droid.data.datasource.dto.store

import kotlinx.serialization.Serializable

@Serializable
data class StorePage(
    val stores: List<StoreDTO>,
    val hasNext: Boolean,
    val hasPrev: Boolean
)
