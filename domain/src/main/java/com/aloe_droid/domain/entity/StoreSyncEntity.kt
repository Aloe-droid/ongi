package com.aloe_droid.domain.entity

import kotlinx.datetime.Instant

data class StoreSyncEntity(
    val storeCount: Long,
    val syncTime: Instant
)
