package com.aloe_droid.presentation.map.data

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.home.data.LocationData

@Stable
data class MapData(
    val mapCenter: LocationData = LocationData(),
    val distance: Float = 5f
)
