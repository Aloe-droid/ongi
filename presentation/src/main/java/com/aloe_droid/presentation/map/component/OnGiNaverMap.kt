package com.aloe_droid.presentation.map.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.aloe_droid.presentation.base.theme.DefaultIconSize
import com.aloe_droid.presentation.base.theme.LargeIconSize
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.data.StoreMapData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.util.MarkerIcons

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun OnGiNaverMap(
    location: LatLng,
    maxZoom: Double = 20.0,
    minZoom: Double = 10.0,
    storeItems: List<StoreMapData>,
    onLocationCheck: () -> Unit,
    onChangeMapData: (MapData) -> Unit,
    onMarkerClick: (StoreMapData) -> Unit,
    onSearch: () -> Unit
) {
    val mapProperties = remember { MapProperties(maxZoom = maxZoom, minZoom = minZoom) }
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(location, (maxZoom + minZoom) / 2)
    }

    NaverMap(properties = mapProperties, cameraPositionState = cameraPositionState) {
        Marker(
            icon = MarkerIcons.BLACK,
            iconTintColor = Color.Red,
            state = MarkerState(position = location),
            width = DefaultIconSize,
            height = LargeIconSize
        )

        OnGiMapDataEffect(onChangeMapData = onChangeMapData)
        OnGiStoreMapDataEffect(storeItems = storeItems, onMarkerClick = onMarkerClick)
    }

    MapRowButtons(
        onLocationCheck = {
            onLocationCheck()
            cameraPositionState.move(CameraUpdate.scrollTo(location))
        },
        onSearch = {
            onSearch()
        }
    )
}
