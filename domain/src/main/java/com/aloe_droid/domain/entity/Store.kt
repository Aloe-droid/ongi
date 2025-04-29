package com.aloe_droid.domain.entity

import java.util.UUID

data class Store(
    val id: UUID,
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
    val imageUrl: String?
)
