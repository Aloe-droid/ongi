package com.aloe_droid.presentation.map.contract

import androidx.compose.runtime.Stable
import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.home.data.LocationData
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData
import com.google.android.gms.common.api.ResolvableApiException

@Stable
data class MapUiState(
    val isInitialState: Boolean = true,
    val locationData: LocationData = LocationData(),
    val isNeedPermission: Boolean = false,
    val gpsError: ResolvableApiException? = null,
    val mapData: MapData = MapData(),
    val searchedStoreList: List<StoreMapData> = emptyList(),
    val selectedMarkerStore: StoreMapData? = null,
) : UiContract.State
