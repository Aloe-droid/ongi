package com.aloe_droid.presentation.map.component

import androidx.compose.runtime.Composable
import com.aloe_droid.presentation.map.data.MapData
import com.aloe_droid.presentation.map.util.LocationUtil
import com.aloe_droid.presentation.map.util.LocationUtil.toLocationData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun OnGiMapDataEffect(onChangeMapData: (MapData) -> Unit) {
    DisposableMapEffect(key1 = Unit) { map: NaverMap ->
        val mapDataListener = NaverMap.OnCameraChangeListener { _: Int, _: Boolean ->
            val bounds = map.contentBounds
            val center: LatLng = bounds.center
            val northEast: LatLng = bounds.northEast
            val southWest: LatLng = bounds.southWest
            val radius: Float = LocationUtil.getRadiusKm(
                northEast = northEast,
                southWest = southWest
            )
            val mapData = MapData(mapCenter = center.toLocationData(), distance = radius)
            onChangeMapData(mapData)
        }

        map.addOnCameraChangeListener(mapDataListener)
        onDispose {
            map.removeOnCameraChangeListener(mapDataListener)
        }
    }
}
