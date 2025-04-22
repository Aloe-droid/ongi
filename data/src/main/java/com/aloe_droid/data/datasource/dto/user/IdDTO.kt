package com.aloe_droid.data.datasource.dto.user

import com.aloe_droid.data.datasource.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class IdDTO(@Serializable(with = UUIDSerializer::class) val id: UUID)
