package com.aloe_droid.presentation.map.contract

import com.aloe_droid.presentation.base.view.UiContract
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData

sealed class MapEvent : UiContract.Event {
    data object LoadEvent : MapEvent()
    data object LocationRetry : MapEvent()
    data class LocationSkip(val skipMessage: String) : MapEvent()
    data object CheckLocation : MapEvent()
    data class SelectStoreMarker(val storeData: StoreMapData) : MapEvent()
    data class ChangeMapInfo(val mapData: MapData) : MapEvent()
    data object SearchNearbyStores : MapEvent()
    data class SelectStore(val storeData: StoreMapData) : MapEvent()
}
