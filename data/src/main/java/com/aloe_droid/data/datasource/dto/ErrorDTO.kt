package com.aloe_droid.data.datasource.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
    val timeStamp: Instant
)
