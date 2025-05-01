package com.aloe_droid.presentation.home.data

import androidx.compose.runtime.Stable
import com.aloe_droid.domain.entity.Location
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class LocationData(
    val latitude: Double = 37.5642135,
    val longitude: Double = 127.0016985
) {
    companion object {
        fun Location.toLocationData() = LocationData(
            longitude = longitude,
            latitude = latitude
        )

        fun LocationData.toLocation() = Location(
            longitude = longitude,
            latitude = latitude
        )
    }
}
