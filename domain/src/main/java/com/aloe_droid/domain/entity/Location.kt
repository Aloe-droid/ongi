package com.aloe_droid.domain.entity

data class Location(
    val latitude: Double = 37.5642135,
    val longitude: Double = 127.0016985,
    val isDefault: Boolean = false,
    val error: Throwable? = null
)
