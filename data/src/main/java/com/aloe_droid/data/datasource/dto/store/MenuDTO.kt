package com.aloe_droid.data.datasource.dto.store

import com.aloe_droid.data.datasource.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MenuDTO(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    @Serializable(with = UUIDSerializer::class) val storeId: UUID,
    val name: String,
    val price: String
)
