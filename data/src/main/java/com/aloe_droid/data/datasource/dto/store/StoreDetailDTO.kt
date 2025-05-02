package com.aloe_droid.data.datasource.dto.store

import com.aloe_droid.data.datasource.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class StoreDetailDTO(
    @Serializable(with = UUIDSerializer::class) val storeId: UUID,
    val hasParking: Boolean,
    val hasTakeout: Boolean,
    val hasDelivery: Boolean,
    val hasReservation: Boolean,
    val hasDividedRestroom: Boolean,
    val allowsPets: Boolean,
    val hasWifi: Boolean,
    val hasKidsFacility: Boolean,
    val allowsGroup: Boolean
)
