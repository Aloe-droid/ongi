package com.aloe_droid.presentation.map.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.home.data.LocationData
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData
import com.google.android.gms.common.api.ResolvableApiException
import java.util.UUID

@Stable
data class MapUiState(
    val isInitialState: Boolean = true,
    val checkLocation: Boolean = false,
    val findStores: Boolean = true,
    val locationData: LocationData = LocationData(),
    val selectedMarkerId: UUID? = null,
    val isNeedPermission: Boolean = false,
    val gpsError: ResolvableApiException? = null,
    val mapData: MapData = MapData(),
) : UiContract.State {
    companion object {
        val changeComparator: (MapUiState, MapUiState) -> Boolean = { prev, now ->
            if (now.isInitialState || now.checkLocation || now.findStores) false
            else if (prev.locationData != now.locationData) false
            else true
        }
    }
}

@Stable
data class MapUiData(
    val searchedStoreList: List<StoreMapData> = emptyList(),
    val selectedMarkerStore: StoreMapData? = null
)
